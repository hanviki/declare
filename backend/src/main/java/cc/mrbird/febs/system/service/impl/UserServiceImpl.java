package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.scm.dao.ScmBUserandareaMapper;
import cc.mrbird.febs.scm.entity.ScmBUserandarea;
import cc.mrbird.febs.system.dao.UserMapper;
import cc.mrbird.febs.system.dao.UserRoleMapper;
import cc.mrbird.febs.system.domain.User;
import cc.mrbird.febs.system.domain.UserRole;
import cc.mrbird.febs.system.manager.UserManager;
import cc.mrbird.febs.system.service.UserConfigService;
import cc.mrbird.febs.system.service.UserRoleService;
import cc.mrbird.febs.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserConfigService userConfigService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ScmBUserandareaMapper areaUserMapper;

    @Override
    public User findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }


    @Override
    public IPage<User> findUserDetail(User user, QueryRequest request) {
        try {
            Page<User> page = new Page<>();
            SortUtil.handlePageSort(request, page, "userId", FebsConstant.ORDER_ASC, false);
            return this.baseMapper.findUserDetail(page, user);
        } catch (Exception e) {
            log.error("查询用户异常", e);
            return null;
        }
    }
    @Override
    public  void updateUserByName( User user) throws Exception
    {
        this.baseMapper.updateUserByName(user);
        // 重新将用户信息加载到 redis中
        cacheService.saveUser(user.getUsername());
    }
    @Override
    @Transactional
    public void updateLoginTime(String username) throws Exception {
        User user = new User();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));

        // 重新将用户信息加载到 redis中
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void createUser(User user) throws Exception {
        // 创建用户
        user.setCreateTime(new Date());
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword(MD5Util.encrypt(user.getUsername(), User.DEFAULT_PASSWORD));
        save(user);

        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        //保存用户的部门区域
        if(user.getAreaId()!=null) {
            String[] areaIds = user.getAreaId().split(StringPool.COMMA);
            setUserArea(user, areaIds);
        }
        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));

        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);
    }
    private void setUserArea(User user, String[] areaIds) {
        Arrays.stream(areaIds).forEach(menuId -> {
            ScmBUserandarea rm = new ScmBUserandarea();
            rm.setId(UUID.randomUUID().toString());
            rm.setAreaID(menuId);
            rm.setUserID(user.getUserId());
            rm.setCreateTime(new Date());
             this.areaUserMapper.insert(rm);
        });
    }
    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        // 更新用户
        user.setPassword(null);
        user.setModifyTime(new Date());
        updateById(user);

        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));

        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

        this.areaUserMapper.delete(new LambdaQueryWrapper<ScmBUserandarea>().eq(ScmBUserandarea::getUserID, user.getUserId()));

        //保存用户的部门区域
        String[] areaIds = user.getAreaId().split(StringPool.COMMA);
        setUserArea(user,areaIds);

        // 重新将用户信息，用户角色信息，用户权限信息 加载到 redis中
        cacheService.saveUser(user.getUsername());
        cacheService.saveRoles(user.getUsername());
        cacheService.savePermissions(user.getUsername());
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) throws Exception {
        // 先删除相应的缓存
        this.userManager.deleteUserRedisCache(userIds);

        List<String> list = Arrays.asList(userIds);

        removeByIds(list);

        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
        // 删除用户个性化配置
        this.userConfigService.deleteByUserId(userIds);
    }

    @Override
    @Transactional
    public void updateProfile(User user) throws Exception {
        updateById(user);
        // 重新缓存用户信息
        cacheService.saveUser(user.getUsername());
    }

    @Override
    @Transactional
    public void updateAvatar(String username, String avatar) throws Exception {
        User user = new User();
        user.setAvatar(avatar);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }
    @Override
    @Transactional
    public void updateOpenid(String username, String openid) throws Exception {
        User user = new User();
        user.setCode("2020"+openid);//为了找到第一次订阅时的openid

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
       // cacheService.saveUser(username);
    }
    @Override
    @Transactional
    public void updateOpenid2(String username, String openid) throws Exception {
        User user = new User();
        user.setCode(openid);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    @Override
    @Transactional
    public void regist(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSsex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setDescription("注册用户");
        this.save(user);

        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(2L); // 注册用户角色 ID
        this.userRoleMapper.insert(ur);

        // 创建用户默认的个性化配置
        userConfigService.initDefaultUserConfig(String.valueOf(user.getUserId()));
        // 将用户相关信息保存到 Redis中
        userManager.loadUserRedisCache(user);

    }

    @Override
    @Transactional
    public void resetPassword(String[] usernames) throws Exception {
        for (String username : usernames) {

            User user = new User();
            user.setPassword(MD5Util.encrypt(username, User.DEFAULT_PASSWORD));

            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            // 重新将用户信息加载到 redis中
            cacheService.saveUser(username);
        }

    }

    private void setUserRoles(User user, String[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            this.userRoleMapper.insert(ur);
        });
    }
    public void updateRealname(String username, String realname) throws Exception {
        User user = new User();
        user.setRealname(realname);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }

    public  void  UpdateUserOnly(User user,String username) throws Exception
    {
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // 重新缓存用户信息
        cacheService.saveUser(username);
    }
    public List<User> findUserWithoutOpenid(){
       return this.baseMapper.findUserWithoutOpenid();
    }

}
