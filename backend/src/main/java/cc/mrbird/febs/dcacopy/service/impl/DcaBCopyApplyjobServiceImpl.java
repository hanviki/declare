package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyApplyjob;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyApplyjobMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyApplyjobService;
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
 * 拟聘岗位 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyApplyjobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyApplyjobServiceImpl extends ServiceImpl<DcaBCopyApplyjobMapper, DcaBCopyApplyjob> implements IDcaBCopyApplyjobService {


@Override
public IPage<DcaBCopyApplyjob> findDcaBCopyApplyjobs(QueryRequest request, DcaBCopyApplyjob dcaBCopyApplyjob){
        try{
        LambdaQueryWrapper<DcaBCopyApplyjob> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyApplyjob::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyApplyjob.getDcaYear())) {
                                queryWrapper.like(DcaBCopyApplyjob::getDcaYear, dcaBCopyApplyjob.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyApplyjob.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyApplyjob::getUserAccountName, dcaBCopyApplyjob.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyApplyjob.getUserAccount())) {
                                queryWrapper.like(DcaBCopyApplyjob::getUserAccount, dcaBCopyApplyjob.getUserAccount());
                                }

        Page<DcaBCopyApplyjob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyApplyjob> findDcaBCopyApplyjobList (QueryRequest request, DcaBCopyApplyjob dcaBCopyApplyjob){
        try{
        Page<DcaBCopyApplyjob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyApplyjob(page,dcaBCopyApplyjob);
        }catch(Exception e){
        log.error("获取拟聘岗位失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyApplyjob(DcaBCopyApplyjob dcaBCopyApplyjob){
                dcaBCopyApplyjob.setId(UUID.randomUUID().toString());
        dcaBCopyApplyjob.setCreateTime(new Date());
        dcaBCopyApplyjob.setIsDeletemark(1);
        this.save(dcaBCopyApplyjob);
        }

@Override
@Transactional
public void updateDcaBCopyApplyjob(DcaBCopyApplyjob dcaBCopyApplyjob){
        dcaBCopyApplyjob.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyApplyjob(dcaBCopyApplyjob);
        }

@Override
@Transactional
public void deleteDcaBCopyApplyjobs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyApplyjob> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyApplyjob> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyApplyjob::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyApplyjob::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }