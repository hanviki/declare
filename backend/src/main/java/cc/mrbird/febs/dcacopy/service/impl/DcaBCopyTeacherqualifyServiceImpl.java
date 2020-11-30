package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherqualify;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyTeacherqualifyMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacherqualifyService;
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
 * 教师资格证书(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyTeacherqualifyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyTeacherqualifyServiceImpl extends ServiceImpl<DcaBCopyTeacherqualifyMapper, DcaBCopyTeacherqualify> implements IDcaBCopyTeacherqualifyService {


@Override
public IPage<DcaBCopyTeacherqualify> findDcaBCopyTeacherqualifys(QueryRequest request, DcaBCopyTeacherqualify dcaBCopyTeacherqualify){
        try{
        LambdaQueryWrapper<DcaBCopyTeacherqualify> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyTeacherqualify::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyTeacherqualify.getDcaYear())) {
                                queryWrapper.like(DcaBCopyTeacherqualify::getDcaYear, dcaBCopyTeacherqualify.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTeacherqualify.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyTeacherqualify::getUserAccountName, dcaBCopyTeacherqualify.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTeacherqualify.getUserAccount())) {
                                queryWrapper.like(DcaBCopyTeacherqualify::getUserAccount, dcaBCopyTeacherqualify.getUserAccount());
                                }

        Page<DcaBCopyTeacherqualify> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyTeacherqualify> findDcaBCopyTeacherqualifyList (QueryRequest request, DcaBCopyTeacherqualify dcaBCopyTeacherqualify){
        try{
        Page<DcaBCopyTeacherqualify> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyTeacherqualify(page,dcaBCopyTeacherqualify);
        }catch(Exception e){
        log.error("获取教师资格证书(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyTeacherqualify(DcaBCopyTeacherqualify dcaBCopyTeacherqualify){
                dcaBCopyTeacherqualify.setId(UUID.randomUUID().toString());
        dcaBCopyTeacherqualify.setCreateTime(new Date());
        dcaBCopyTeacherqualify.setIsDeletemark(1);
        this.save(dcaBCopyTeacherqualify);
        }

@Override
@Transactional
public void updateDcaBCopyTeacherqualify(DcaBCopyTeacherqualify dcaBCopyTeacherqualify){
        dcaBCopyTeacherqualify.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyTeacherqualify(dcaBCopyTeacherqualify);
        }

@Override
@Transactional
public void deleteDcaBCopyTeacherqualifys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyTeacherqualify> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyTeacherqualify> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyTeacherqualify::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyTeacherqualify::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }