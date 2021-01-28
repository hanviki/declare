package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocEmploy;
import cc.mrbird.febs.doctor.dao.DcaBDocEmployMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocEmployService;
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
 * 任职培养 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocEmployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocEmployServiceImpl extends ServiceImpl<DcaBDocEmployMapper, DcaBDocEmploy> implements IDcaBDocEmployService {


@Override
@DS("slave")
public IPage<DcaBDocEmploy> findDcaBDocEmploys(QueryRequest request, DcaBDocEmploy dcaBDocEmploy){
        try{
        LambdaQueryWrapper<DcaBDocEmploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocEmploy::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocEmploy.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocEmploy::getUserAccount, dcaBDocEmploy.getUserAccount()).or()
        .like(DcaBDocEmploy::getUserAccountName, dcaBDocEmploy.getUserAccount()));

        }
        if (dcaBDocEmploy.getState()!=null) {
        queryWrapper.eq(DcaBDocEmploy::getState, dcaBDocEmploy.getState());
        }
       /** if (dcaBDocEmploy.getAuditState()!=null && (dcaBDocEmploy.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocEmploy::getAuditState, dcaBDocEmploy.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocEmploy.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocEmploy.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocEmploy::getCreateTime, dcaBDocEmploy.getCreateTimeFrom())
                                .le(DcaBDocEmploy::getCreateTime, dcaBDocEmploy.getCreateTimeTo());
                                }

        Page<DcaBDocEmploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocEmploy> findDcaBDocEmployList (QueryRequest request, DcaBDocEmploy dcaBDocEmploy){
        try{
        Page<DcaBDocEmploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocEmploy(page,dcaBDocEmploy);
        }catch(Exception e){
        log.error("获取任职培养失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocEmploy(DcaBDocEmploy dcaBDocEmploy){
                dcaBDocEmploy.setId(UUID.randomUUID().toString());
        dcaBDocEmploy.setCreateTime(new Date());
        dcaBDocEmploy.setIsDeletemark(1);
        this.save(dcaBDocEmploy);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocEmploy(DcaBDocEmploy dcaBDocEmploy){
        dcaBDocEmploy.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocEmploy(dcaBDocEmploy);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocEmploys(String[]Ids){
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