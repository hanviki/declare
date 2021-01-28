package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocApplyjob;
import cc.mrbird.febs.doctor.dao.DcaBDocApplyjobMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocApplyjobService;
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
 * 拟聘岗位 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */
@Slf4j
@Service("IDcaBDocApplyjobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocApplyjobServiceImpl extends ServiceImpl<DcaBDocApplyjobMapper, DcaBDocApplyjob> implements IDcaBDocApplyjobService {


@Override
@DS("slave")
public IPage<DcaBDocApplyjob> findDcaBDocApplyjobs(QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob){
        try{
        LambdaQueryWrapper<DcaBDocApplyjob> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocApplyjob::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocApplyjob.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocApplyjob::getUserAccount, dcaBDocApplyjob.getUserAccount()).or()
        .like(DcaBDocApplyjob::getUserAccountName, dcaBDocApplyjob.getUserAccount()));

        }
        if (dcaBDocApplyjob.getState()!=null) {
        queryWrapper.eq(DcaBDocApplyjob::getState, dcaBDocApplyjob.getState());
        }
       /** if (dcaBDocApplyjob.getAuditState()!=null && (dcaBDocApplyjob.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocApplyjob::getAuditState, dcaBDocApplyjob.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocApplyjob.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocApplyjob.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocApplyjob::getCreateTime, dcaBDocApplyjob.getCreateTimeFrom())
                                .le(DcaBDocApplyjob::getCreateTime, dcaBDocApplyjob.getCreateTimeTo());
                                }

        Page<DcaBDocApplyjob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocApplyjob> findDcaBDocApplyjobList (QueryRequest request, DcaBDocApplyjob dcaBDocApplyjob){
        try{
        Page<DcaBDocApplyjob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocApplyjob(page,dcaBDocApplyjob);
        }catch(Exception e){
        log.error("获取拟聘岗位失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocApplyjob(DcaBDocApplyjob dcaBDocApplyjob){
                dcaBDocApplyjob.setId(UUID.randomUUID().toString());
        dcaBDocApplyjob.setCreateTime(new Date());
        dcaBDocApplyjob.setIsDeletemark(1);
        this.save(dcaBDocApplyjob);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocApplyjob(DcaBDocApplyjob dcaBDocApplyjob){
        dcaBDocApplyjob.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocApplyjob(dcaBDocApplyjob);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocApplyjobs(String[]Ids){
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