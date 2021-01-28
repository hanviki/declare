package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocInnovatebuild;
import cc.mrbird.febs.doctor.dao.DcaBDocInnovatebuildMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocInnovatebuildService;
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
 * 改革及建设项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocInnovatebuildService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocInnovatebuildServiceImpl extends ServiceImpl<DcaBDocInnovatebuildMapper, DcaBDocInnovatebuild> implements IDcaBDocInnovatebuildService {


@Override
@DS("slave")
public IPage<DcaBDocInnovatebuild> findDcaBDocInnovatebuilds(QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild){
        try{
        LambdaQueryWrapper<DcaBDocInnovatebuild> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocInnovatebuild::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocInnovatebuild.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocInnovatebuild::getUserAccount, dcaBDocInnovatebuild.getUserAccount()).or()
        .like(DcaBDocInnovatebuild::getUserAccountName, dcaBDocInnovatebuild.getUserAccount()));

        }
        if (dcaBDocInnovatebuild.getState()!=null) {
        queryWrapper.eq(DcaBDocInnovatebuild::getState, dcaBDocInnovatebuild.getState());
        }
       /** if (dcaBDocInnovatebuild.getAuditState()!=null && (dcaBDocInnovatebuild.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocInnovatebuild::getAuditState, dcaBDocInnovatebuild.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocInnovatebuild.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocInnovatebuild.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocInnovatebuild::getCreateTime, dcaBDocInnovatebuild.getCreateTimeFrom())
                                .le(DcaBDocInnovatebuild::getCreateTime, dcaBDocInnovatebuild.getCreateTimeTo());
                                }

        Page<DcaBDocInnovatebuild> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocInnovatebuild> findDcaBDocInnovatebuildList (QueryRequest request, DcaBDocInnovatebuild dcaBDocInnovatebuild){
        try{
        Page<DcaBDocInnovatebuild> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocInnovatebuild(page,dcaBDocInnovatebuild);
        }catch(Exception e){
        log.error("获取改革及建设项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocInnovatebuild(DcaBDocInnovatebuild dcaBDocInnovatebuild){
                dcaBDocInnovatebuild.setId(UUID.randomUUID().toString());
        dcaBDocInnovatebuild.setCreateTime(new Date());
        dcaBDocInnovatebuild.setIsDeletemark(1);
        this.save(dcaBDocInnovatebuild);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocInnovatebuild(DcaBDocInnovatebuild dcaBDocInnovatebuild){
        dcaBDocInnovatebuild.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocInnovatebuild(dcaBDocInnovatebuild);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocInnovatebuilds(String[]Ids){
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