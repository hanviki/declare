package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyInnovatebuild;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyInnovatebuildMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyInnovatebuildService;
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
 * 任现职以来承担的本科教学改革及建设项目(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyInnovatebuildService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyInnovatebuildServiceImpl extends ServiceImpl<DcaBCopyInnovatebuildMapper, DcaBCopyInnovatebuild> implements IDcaBCopyInnovatebuildService {


@Override
public IPage<DcaBCopyInnovatebuild> findDcaBCopyInnovatebuilds(QueryRequest request, DcaBCopyInnovatebuild dcaBCopyInnovatebuild){
        try{
        LambdaQueryWrapper<DcaBCopyInnovatebuild> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyInnovatebuild::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyInnovatebuild.getDcaYear())) {
                                queryWrapper.like(DcaBCopyInnovatebuild::getDcaYear, dcaBCopyInnovatebuild.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyInnovatebuild.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyInnovatebuild::getUserAccountName, dcaBCopyInnovatebuild.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyInnovatebuild.getUserAccount())) {
                                queryWrapper.like(DcaBCopyInnovatebuild::getUserAccount, dcaBCopyInnovatebuild.getUserAccount());
                                }

        Page<DcaBCopyInnovatebuild> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyInnovatebuild> findDcaBCopyInnovatebuildList (QueryRequest request, DcaBCopyInnovatebuild dcaBCopyInnovatebuild){
        try{
        Page<DcaBCopyInnovatebuild> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyInnovatebuild(page,dcaBCopyInnovatebuild);
        }catch(Exception e){
        log.error("获取任现职以来承担的本科教学改革及建设项目(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyInnovatebuild(DcaBCopyInnovatebuild dcaBCopyInnovatebuild){
                dcaBCopyInnovatebuild.setId(UUID.randomUUID().toString());
        dcaBCopyInnovatebuild.setCreateTime(new Date());
        dcaBCopyInnovatebuild.setIsDeletemark(1);
        this.save(dcaBCopyInnovatebuild);
        }

@Override
@Transactional
public void updateDcaBCopyInnovatebuild(DcaBCopyInnovatebuild dcaBCopyInnovatebuild){
        dcaBCopyInnovatebuild.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyInnovatebuild(dcaBCopyInnovatebuild);
        }

@Override
@Transactional
public void deleteDcaBCopyInnovatebuilds(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyInnovatebuild> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyInnovatebuild> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyInnovatebuild::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyInnovatebuild::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }