package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAcademic;
import cc.mrbird.febs.doctor.dao.DcaBDocAcademicMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAcademicService;
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
 * 担任辅导员教师班主任情况(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocAcademicService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAcademicServiceImpl extends ServiceImpl<DcaBDocAcademicMapper, DcaBDocAcademic> implements IDcaBDocAcademicService {


@Override
@DS("slave")
public IPage<DcaBDocAcademic> findDcaBDocAcademics(QueryRequest request, DcaBDocAcademic dcaBDocAcademic){
        try{
        LambdaQueryWrapper<DcaBDocAcademic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAcademic::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocAcademic.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocAcademic::getUserAccount, dcaBDocAcademic.getUserAccount()).or()
        .like(DcaBDocAcademic::getUserAccountName, dcaBDocAcademic.getUserAccount()));

        }
        if (dcaBDocAcademic.getState()!=null) {
        queryWrapper.eq(DcaBDocAcademic::getState, dcaBDocAcademic.getState());
        }
       /** if (dcaBDocAcademic.getAuditState()!=null && (dcaBDocAcademic.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocAcademic::getAuditState, dcaBDocAcademic.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocAcademic.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocAcademic.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocAcademic::getCreateTime, dcaBDocAcademic.getCreateTimeFrom())
                                .le(DcaBDocAcademic::getCreateTime, dcaBDocAcademic.getCreateTimeTo());
                                }

        Page<DcaBDocAcademic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAcademic> findDcaBDocAcademicList (QueryRequest request, DcaBDocAcademic dcaBDocAcademic){
        try{
        Page<DcaBDocAcademic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAcademic(page,dcaBDocAcademic);
        }catch(Exception e){
        log.error("获取担任辅导员教师班主任情况(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAcademic(DcaBDocAcademic dcaBDocAcademic){
                dcaBDocAcademic.setId(UUID.randomUUID().toString());
        dcaBDocAcademic.setCreateTime(new Date());
        dcaBDocAcademic.setIsDeletemark(1);
        this.save(dcaBDocAcademic);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAcademic(DcaBDocAcademic dcaBDocAcademic){
        dcaBDocAcademic.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAcademic(dcaBDocAcademic);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAcademics(String[]Ids){
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