package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyQualification;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyQualificationMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyQualificationService;
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
 * 资质情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-25
 */
@Slf4j
@Service("IDcaBCopyQualificationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyQualificationServiceImpl extends ServiceImpl<DcaBCopyQualificationMapper, DcaBCopyQualification> implements IDcaBCopyQualificationService {


@Override
public IPage<DcaBCopyQualification> findDcaBCopyQualifications(QueryRequest request, DcaBCopyQualification dcaBCopyQualification){
        try{
        LambdaQueryWrapper<DcaBCopyQualification> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyQualification::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyQualification.getDcaYear())) {
                                queryWrapper.like(DcaBCopyQualification::getDcaYear, dcaBCopyQualification.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyQualification.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyQualification::getUserAccountName, dcaBCopyQualification.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyQualification.getUserAccount())) {
                                queryWrapper.like(DcaBCopyQualification::getUserAccount, dcaBCopyQualification.getUserAccount());
                                }

        Page<DcaBCopyQualification> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyQualification> findDcaBCopyQualificationList (QueryRequest request, DcaBCopyQualification dcaBCopyQualification){
        try{
        Page<DcaBCopyQualification> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyQualification(page,dcaBCopyQualification);
        }catch(Exception e){
        log.error("获取资质情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyQualification(DcaBCopyQualification dcaBCopyQualification){
                dcaBCopyQualification.setId(UUID.randomUUID().toString());
        dcaBCopyQualification.setCreateTime(new Date());
        dcaBCopyQualification.setIsDeletemark(1);
        this.save(dcaBCopyQualification);
        }

@Override
@Transactional
public void updateDcaBCopyQualification(DcaBCopyQualification dcaBCopyQualification){
        dcaBCopyQualification.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyQualification(dcaBCopyQualification);
        }

@Override
@Transactional
public void deleteDcaBCopyQualifications(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyQualification> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyQualification> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyQualification::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyQualification::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }