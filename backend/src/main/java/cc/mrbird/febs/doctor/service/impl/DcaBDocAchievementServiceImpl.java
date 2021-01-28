package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAchievement;
import cc.mrbird.febs.doctor.dao.DcaBDocAchievementMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAchievementService;
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
 * 主要医疗业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocAchievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAchievementServiceImpl extends ServiceImpl<DcaBDocAchievementMapper, DcaBDocAchievement> implements IDcaBDocAchievementService {


@Override
@DS("slave")
public IPage<DcaBDocAchievement> findDcaBDocAchievements(QueryRequest request, DcaBDocAchievement dcaBDocAchievement){
        try{
        LambdaQueryWrapper<DcaBDocAchievement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAchievement::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocAchievement.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocAchievement::getUserAccount, dcaBDocAchievement.getUserAccount()).or()
        .like(DcaBDocAchievement::getUserAccountName, dcaBDocAchievement.getUserAccount()));

        }
        if (dcaBDocAchievement.getState()!=null) {
        queryWrapper.eq(DcaBDocAchievement::getState, dcaBDocAchievement.getState());
        }
       /** if (dcaBDocAchievement.getAuditState()!=null && (dcaBDocAchievement.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocAchievement::getAuditState, dcaBDocAchievement.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocAchievement.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocAchievement.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocAchievement::getCreateTime, dcaBDocAchievement.getCreateTimeFrom())
                                .le(DcaBDocAchievement::getCreateTime, dcaBDocAchievement.getCreateTimeTo());
                                }

        Page<DcaBDocAchievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAchievement> findDcaBDocAchievementList (QueryRequest request, DcaBDocAchievement dcaBDocAchievement){
        try{
        Page<DcaBDocAchievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAchievement(page,dcaBDocAchievement);
        }catch(Exception e){
        log.error("获取主要医疗业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAchievement(DcaBDocAchievement dcaBDocAchievement){
                dcaBDocAchievement.setId(UUID.randomUUID().toString());
        dcaBDocAchievement.setCreateTime(new Date());
        dcaBDocAchievement.setIsDeletemark(1);
        this.save(dcaBDocAchievement);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAchievement(DcaBDocAchievement dcaBDocAchievement){
        dcaBDocAchievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAchievement(dcaBDocAchievement);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAchievements(String[]Ids){
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
        }