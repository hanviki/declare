package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocUser;
import cc.mrbird.febs.doctor.dao.DcaBDocUserMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.dynamic.datasource.annotation.DS;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */
@Slf4j
@Service("IDcaBDocUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocUserServiceImpl extends ServiceImpl<DcaBDocUserMapper, DcaBDocUser> implements IDcaBDocUserService {


@Override
@DS("slave")
public IPage<DcaBDocUser> findDcaBDocUsers(QueryRequest request, DcaBDocUser dcaBDocUser){
        try{
        LambdaQueryWrapper<DcaBDocUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocUser::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocUser.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocUser::getUserAccount, dcaBDocUser.getUserAccount()).or()
        .like(DcaBDocUser::getUserAccountName, dcaBDocUser.getUserAccount()));

        }
        if (dcaBDocUser.getState()!=null) {
        queryWrapper.eq(DcaBDocUser::getState, dcaBDocUser.getState());
        }
       /** if (dcaBDocUser.getAuditState()!=null && (dcaBDocUser.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocUser::getAuditState, dcaBDocUser.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocUser.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocUser.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocUser::getCreateTime, dcaBDocUser.getCreateTimeFrom())
                                .le(DcaBDocUser::getCreateTime, dcaBDocUser.getCreateTimeTo());
                                }

        Page<DcaBDocUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocUser> findDcaBDocUserList (QueryRequest request, DcaBDocUser dcaBDocUser){
        try{
        Page<DcaBDocUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocUser(page,dcaBDocUser);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocUser(DcaBDocUser dcaBDocUser){
                dcaBDocUser.setId(UUID.randomUUID().toString());
        dcaBDocUser.setCreateTime(new Date());
        dcaBDocUser.setIsDeletemark(1);
        this.save(dcaBDocUser);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocUser(DcaBDocUser dcaBDocUser){
        dcaBDocUser.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocUser(dcaBDocUser);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocUsers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
@DS("slave")
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }

        @Override
        @Transactional
        @DS("slave")
        public List<DcaBDocUser> findPerson(String userAccount){
                LambdaQueryWrapper<DcaBDocUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(DcaBDocUser::getIsDeletemark, 1);//1是未删 0是已删

                if (StringUtils.isNotBlank(userAccount)) {
                        queryWrapper.eq(DcaBDocUser::getUserAccount,userAccount);

                }
                queryWrapper.eq(DcaBDocUser::getIsDeletemark,1);
                return  this.baseMapper.selectList(queryWrapper);

        }
        }