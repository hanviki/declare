package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocTeacherqualify;
import cc.mrbird.febs.doctor.dao.DcaBDocTeacherqualifyMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocTeacherqualifyService;
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
 * 教师资格 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocTeacherqualifyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocTeacherqualifyServiceImpl extends ServiceImpl<DcaBDocTeacherqualifyMapper, DcaBDocTeacherqualify> implements IDcaBDocTeacherqualifyService {


@Override
@DS("slave")
public IPage<DcaBDocTeacherqualify> findDcaBDocTeacherqualifys(QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify){
        try{
        LambdaQueryWrapper<DcaBDocTeacherqualify> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocTeacherqualify::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocTeacherqualify.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocTeacherqualify::getUserAccount, dcaBDocTeacherqualify.getUserAccount()).or()
        .like(DcaBDocTeacherqualify::getUserAccountName, dcaBDocTeacherqualify.getUserAccount()));

        }
        if (dcaBDocTeacherqualify.getState()!=null) {
        queryWrapper.eq(DcaBDocTeacherqualify::getState, dcaBDocTeacherqualify.getState());
        }
       /** if (dcaBDocTeacherqualify.getAuditState()!=null && (dcaBDocTeacherqualify.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocTeacherqualify::getAuditState, dcaBDocTeacherqualify.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocTeacherqualify.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocTeacherqualify.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocTeacherqualify::getCreateTime, dcaBDocTeacherqualify.getCreateTimeFrom())
                                .le(DcaBDocTeacherqualify::getCreateTime, dcaBDocTeacherqualify.getCreateTimeTo());
                                }

        Page<DcaBDocTeacherqualify> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocTeacherqualify> findDcaBDocTeacherqualifyList (QueryRequest request, DcaBDocTeacherqualify dcaBDocTeacherqualify){
        try{
        Page<DcaBDocTeacherqualify> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocTeacherqualify(page,dcaBDocTeacherqualify);
        }catch(Exception e){
        log.error("获取教师资格失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocTeacherqualify(DcaBDocTeacherqualify dcaBDocTeacherqualify){
                dcaBDocTeacherqualify.setId(UUID.randomUUID().toString());
        dcaBDocTeacherqualify.setCreateTime(new Date());
        dcaBDocTeacherqualify.setIsDeletemark(1);
        this.save(dcaBDocTeacherqualify);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocTeacherqualify(DcaBDocTeacherqualify dcaBDocTeacherqualify){
        dcaBDocTeacherqualify.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocTeacherqualify(dcaBDocTeacherqualify);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocTeacherqualifys(String[]Ids){
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