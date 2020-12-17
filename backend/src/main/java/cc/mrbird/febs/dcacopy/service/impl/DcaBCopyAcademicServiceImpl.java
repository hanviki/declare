package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAcademic;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAcademicMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAcademicService;
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
 * 担任辅导员教师班主任情况(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBCopyAcademicService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyAcademicServiceImpl extends ServiceImpl<DcaBCopyAcademicMapper, DcaBCopyAcademic> implements IDcaBCopyAcademicService {


@Override
public IPage<DcaBCopyAcademic> findDcaBCopyAcademics(QueryRequest request, DcaBCopyAcademic dcaBCopyAcademic){
        try{
        LambdaQueryWrapper<DcaBCopyAcademic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyAcademic::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyAcademic.getDcaYear())) {
                                queryWrapper.like(DcaBCopyAcademic::getDcaYear, dcaBCopyAcademic.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAcademic.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyAcademic::getUserAccountName, dcaBCopyAcademic.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAcademic.getUserAccount())) {
                                queryWrapper.like(DcaBCopyAcademic::getUserAccount, dcaBCopyAcademic.getUserAccount());
                                }

        Page<DcaBCopyAcademic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyAcademic> findDcaBCopyAcademicList (QueryRequest request, DcaBCopyAcademic dcaBCopyAcademic){
        try{
        Page<DcaBCopyAcademic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyAcademic(page,dcaBCopyAcademic);
        }catch(Exception e){
        log.error("获取担任辅导员教师班主任情况(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyAcademic(DcaBCopyAcademic dcaBCopyAcademic){
                dcaBCopyAcademic.setId(UUID.randomUUID().toString());
        dcaBCopyAcademic.setCreateTime(new Date());
        dcaBCopyAcademic.setIsDeletemark(1);
        this.save(dcaBCopyAcademic);
        }

@Override
@Transactional
public void updateDcaBCopyAcademic(DcaBCopyAcademic dcaBCopyAcademic){
        dcaBCopyAcademic.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyAcademic(dcaBCopyAcademic);
        }

@Override
@Transactional
public void deleteDcaBCopyAcademics(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyAcademic> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyAcademic> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyAcademic::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyAcademic::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }