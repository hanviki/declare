package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.VScmBGyspicUser;
import cc.mrbird.febs.scm.dao.VScmBGyspicUserMapper;
import cc.mrbird.febs.scm.service.IVScmBGyspicUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-07-13
 */
@Slf4j
@Service("IVScmBGyspicUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VScmBGyspicUserServiceImpl extends ServiceImpl<VScmBGyspicUserMapper, VScmBGyspicUser> implements IVScmBGyspicUserService {


@Override
public IPage<VScmBGyspicUser> findVScmBGyspicUsers(QueryRequest request, VScmBGyspicUser vScmBGyspicUser, String keyword_mater, String keyword_gys,String userId){
        try{
        LambdaQueryWrapper<VScmBGyspicUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(VScmBGyspicUser::getIsDeletemark, 1);//1是未删 0是已删
                queryWrapper.eq(VScmBGyspicUser::getUserid,userId);

                if (StringUtils.isNotBlank(vScmBGyspicUser.getId())) {
                        queryWrapper.eq(VScmBGyspicUser::getId, vScmBGyspicUser.getId());
                }
                if (StringUtils.isNotBlank(vScmBGyspicUser.getMaterId())) {
                        queryWrapper.eq(VScmBGyspicUser::getMaterId, vScmBGyspicUser.getMaterId());
                }
                if (StringUtils.isNotBlank(vScmBGyspicUser.getMatnr())) {
                        queryWrapper.eq(VScmBGyspicUser::getMatnr, vScmBGyspicUser.getMatnr());
                }
                if (vScmBGyspicUser.getState()!=null &&vScmBGyspicUser.getState()!=-1) {
                        queryWrapper.eq(VScmBGyspicUser::getState, vScmBGyspicUser.getState());
                }
                if (StringUtils.isNotBlank(vScmBGyspicUser.getTxz01())) {
                        queryWrapper.like(VScmBGyspicUser::getTxz01, vScmBGyspicUser.getTxz01());
                }
                if (StringUtils.isNotBlank(vScmBGyspicUser.getGysaccount())) {
                        queryWrapper.eq(VScmBGyspicUser::getGysaccount, vScmBGyspicUser.getGysaccount());
                }
                if (StringUtils.isNotBlank(vScmBGyspicUser.getName())) {
                        queryWrapper.like(VScmBGyspicUser::getName, vScmBGyspicUser.getName());
                }
                if (StringUtils.isNotBlank(vScmBGyspicUser.getCharge())) {
                        queryWrapper.like(VScmBGyspicUser::getCharge, vScmBGyspicUser.getCharge());
                }
                if(StringUtils.isNotBlank(keyword_mater))
                {
                        queryWrapper.and(qw->qw.eq(VScmBGyspicUser::getMatnr,keyword_mater).or().like(VScmBGyspicUser::getTxz01,keyword_mater));
                }
                if(StringUtils.isNotBlank(keyword_gys))
                {
                        queryWrapper.and(qw->qw.eq(VScmBGyspicUser::getGysaccount,keyword_gys).or().like(VScmBGyspicUser::getName,keyword_gys));
                }

        Page<VScmBGyspicUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createVScmBGyspicUser(VScmBGyspicUser vScmBGyspicUser){
        vScmBGyspicUser.setId(UUID.randomUUID().toString());
        vScmBGyspicUser.setCreateTime(new Date());
        vScmBGyspicUser.setIsDeletemark(1);
        this.save(vScmBGyspicUser);
        }

@Override
@Transactional
public void updateVScmBGyspicUser(VScmBGyspicUser vScmBGyspicUser){
        vScmBGyspicUser.setModifyTime(new Date());
        this.baseMapper.updateVScmBGyspicUser(vScmBGyspicUser);
        }

@Override
@Transactional
public void deleteVScmBGyspicUsers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }