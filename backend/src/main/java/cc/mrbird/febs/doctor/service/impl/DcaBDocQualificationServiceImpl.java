package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocQualification;
import cc.mrbird.febs.doctor.dao.DcaBDocQualificationMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocQualificationService;
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
 * 资质情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */
@Slf4j
@Service("IDcaBDocQualificationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocQualificationServiceImpl extends ServiceImpl<DcaBDocQualificationMapper, DcaBDocQualification> implements IDcaBDocQualificationService {


@Override
@DS("slave")
public IPage<DcaBDocQualification> findDcaBDocQualifications(QueryRequest request, DcaBDocQualification dcaBDocQualification){
        try{
        LambdaQueryWrapper<DcaBDocQualification> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocQualification::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocQualification.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocQualification::getUserAccount, dcaBDocQualification.getUserAccount()).or()
        .like(DcaBDocQualification::getUserAccountName, dcaBDocQualification.getUserAccount()));

        }
        if (dcaBDocQualification.getState()!=null) {
        queryWrapper.eq(DcaBDocQualification::getState, dcaBDocQualification.getState());
        }
       /** if (dcaBDocQualification.getAuditState()!=null && (dcaBDocQualification.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocQualification::getAuditState, dcaBDocQualification.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocQualification.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocQualification.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocQualification::getCreateTime, dcaBDocQualification.getCreateTimeFrom())
                                .le(DcaBDocQualification::getCreateTime, dcaBDocQualification.getCreateTimeTo());
                                }

        Page<DcaBDocQualification> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocQualification> findDcaBDocQualificationList (QueryRequest request, DcaBDocQualification dcaBDocQualification){
        try{
        Page<DcaBDocQualification> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocQualification(page,dcaBDocQualification);
        }catch(Exception e){
        log.error("获取资质情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocQualification(DcaBDocQualification dcaBDocQualification){
                dcaBDocQualification.setId(UUID.randomUUID().toString());
        dcaBDocQualification.setCreateTime(new Date());
        dcaBDocQualification.setIsDeletemark(1);
        this.save(dcaBDocQualification);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocQualification(DcaBDocQualification dcaBDocQualification){
        dcaBDocQualification.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocQualification(dcaBDocQualification);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocQualifications(String[]Ids){
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