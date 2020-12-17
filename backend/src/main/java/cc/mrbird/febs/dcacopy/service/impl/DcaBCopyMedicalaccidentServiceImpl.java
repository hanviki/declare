package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyMedicalaccident;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyMedicalaccidentMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyMedicalaccidentService;
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
 * 担任博导硕导 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBCopyMedicalaccidentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyMedicalaccidentServiceImpl extends ServiceImpl<DcaBCopyMedicalaccidentMapper, DcaBCopyMedicalaccident> implements IDcaBCopyMedicalaccidentService {


@Override
public IPage<DcaBCopyMedicalaccident> findDcaBCopyMedicalaccidents(QueryRequest request, DcaBCopyMedicalaccident dcaBCopyMedicalaccident){
        try{
        LambdaQueryWrapper<DcaBCopyMedicalaccident> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyMedicalaccident::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyMedicalaccident.getDcaYear())) {
                                queryWrapper.like(DcaBCopyMedicalaccident::getDcaYear, dcaBCopyMedicalaccident.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyMedicalaccident.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyMedicalaccident::getUserAccountName, dcaBCopyMedicalaccident.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyMedicalaccident.getUserAccount())) {
                                queryWrapper.like(DcaBCopyMedicalaccident::getUserAccount, dcaBCopyMedicalaccident.getUserAccount());
                                }

        Page<DcaBCopyMedicalaccident> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyMedicalaccident> findDcaBCopyMedicalaccidentList (QueryRequest request, DcaBCopyMedicalaccident dcaBCopyMedicalaccident){
        try{
        Page<DcaBCopyMedicalaccident> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyMedicalaccident(page,dcaBCopyMedicalaccident);
        }catch(Exception e){
        log.error("获取担任博导硕导失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyMedicalaccident(DcaBCopyMedicalaccident dcaBCopyMedicalaccident){
                dcaBCopyMedicalaccident.setId(UUID.randomUUID().toString());
        dcaBCopyMedicalaccident.setCreateTime(new Date());
        dcaBCopyMedicalaccident.setIsDeletemark(1);
        this.save(dcaBCopyMedicalaccident);
        }

@Override
@Transactional
public void updateDcaBCopyMedicalaccident(DcaBCopyMedicalaccident dcaBCopyMedicalaccident){
        dcaBCopyMedicalaccident.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyMedicalaccident(dcaBCopyMedicalaccident);
        }

@Override
@Transactional
public void deleteDcaBCopyMedicalaccidents(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyMedicalaccident> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyMedicalaccident> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyMedicalaccident::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyMedicalaccident::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }