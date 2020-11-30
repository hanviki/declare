package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyGraduate;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyGraduateMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyGraduateService;
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
 * 任现职以来独立指导研究生情况(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyGraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyGraduateServiceImpl extends ServiceImpl<DcaBCopyGraduateMapper, DcaBCopyGraduate> implements IDcaBCopyGraduateService {


@Override
public IPage<DcaBCopyGraduate> findDcaBCopyGraduates(QueryRequest request, DcaBCopyGraduate dcaBCopyGraduate){
        try{
        LambdaQueryWrapper<DcaBCopyGraduate> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyGraduate::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyGraduate.getDcaYear())) {
                                queryWrapper.like(DcaBCopyGraduate::getDcaYear, dcaBCopyGraduate.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyGraduate.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyGraduate::getUserAccountName, dcaBCopyGraduate.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyGraduate.getUserAccount())) {
                                queryWrapper.like(DcaBCopyGraduate::getUserAccount, dcaBCopyGraduate.getUserAccount());
                                }

        Page<DcaBCopyGraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyGraduate> findDcaBCopyGraduateList (QueryRequest request, DcaBCopyGraduate dcaBCopyGraduate){
        try{
        Page<DcaBCopyGraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyGraduate(page,dcaBCopyGraduate);
        }catch(Exception e){
        log.error("获取任现职以来独立指导研究生情况(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyGraduate(DcaBCopyGraduate dcaBCopyGraduate){
                dcaBCopyGraduate.setId(UUID.randomUUID().toString());
        dcaBCopyGraduate.setCreateTime(new Date());
        dcaBCopyGraduate.setIsDeletemark(1);
        this.save(dcaBCopyGraduate);
        }

@Override
@Transactional
public void updateDcaBCopyGraduate(DcaBCopyGraduate dcaBCopyGraduate){
        dcaBCopyGraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyGraduate(dcaBCopyGraduate);
        }

@Override
@Transactional
public void deleteDcaBCopyGraduates(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyGraduate> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyGraduate> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyGraduate::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyGraduate::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }