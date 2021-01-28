package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocMedicalaccident;
import cc.mrbird.febs.doctor.dao.DcaBDocMedicalaccidentMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocMedicalaccidentService;
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
 * 担任博导硕导 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocMedicalaccidentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocMedicalaccidentServiceImpl extends ServiceImpl<DcaBDocMedicalaccidentMapper, DcaBDocMedicalaccident> implements IDcaBDocMedicalaccidentService {


@Override
@DS("slave")
public IPage<DcaBDocMedicalaccident> findDcaBDocMedicalaccidents(QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident){
        try{
        LambdaQueryWrapper<DcaBDocMedicalaccident> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocMedicalaccident::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocMedicalaccident.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocMedicalaccident::getUserAccount, dcaBDocMedicalaccident.getUserAccount()).or()
        .like(DcaBDocMedicalaccident::getUserAccountName, dcaBDocMedicalaccident.getUserAccount()));

        }
        if (dcaBDocMedicalaccident.getState()!=null) {
        queryWrapper.eq(DcaBDocMedicalaccident::getState, dcaBDocMedicalaccident.getState());
        }
       /** if (dcaBDocMedicalaccident.getAuditState()!=null && (dcaBDocMedicalaccident.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocMedicalaccident::getAuditState, dcaBDocMedicalaccident.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocMedicalaccident.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocMedicalaccident.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocMedicalaccident::getCreateTime, dcaBDocMedicalaccident.getCreateTimeFrom())
                                .le(DcaBDocMedicalaccident::getCreateTime, dcaBDocMedicalaccident.getCreateTimeTo());
                                }

        Page<DcaBDocMedicalaccident> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocMedicalaccident> findDcaBDocMedicalaccidentList (QueryRequest request, DcaBDocMedicalaccident dcaBDocMedicalaccident){
        try{
        Page<DcaBDocMedicalaccident> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocMedicalaccident(page,dcaBDocMedicalaccident);
        }catch(Exception e){
        log.error("获取担任博导硕导失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocMedicalaccident(DcaBDocMedicalaccident dcaBDocMedicalaccident){
                dcaBDocMedicalaccident.setId(UUID.randomUUID().toString());
        dcaBDocMedicalaccident.setCreateTime(new Date());
        dcaBDocMedicalaccident.setIsDeletemark(1);
        this.save(dcaBDocMedicalaccident);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocMedicalaccident(DcaBDocMedicalaccident dcaBDocMedicalaccident){
        dcaBDocMedicalaccident.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocMedicalaccident(dcaBDocMedicalaccident);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocMedicalaccidents(String[]Ids){
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