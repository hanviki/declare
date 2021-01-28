package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfive;
import cc.mrbird.febs.doctor.dao.DcaBDocAuditfiveMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAuditfiveService;
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
 * 近五年总体评价情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */
@Slf4j
@Service("IDcaBDocAuditfiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAuditfiveServiceImpl extends ServiceImpl<DcaBDocAuditfiveMapper, DcaBDocAuditfive> implements IDcaBDocAuditfiveService {


@Override
@DS("slave")
public IPage<DcaBDocAuditfive> findDcaBDocAuditfives(QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive){
        try{
        LambdaQueryWrapper<DcaBDocAuditfive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAuditfive::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocAuditfive.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocAuditfive::getUserAccount, dcaBDocAuditfive.getUserAccount()).or()
        .like(DcaBDocAuditfive::getUserAccountName, dcaBDocAuditfive.getUserAccount()));

        }
        if (dcaBDocAuditfive.getState()!=null) {
        queryWrapper.eq(DcaBDocAuditfive::getState, dcaBDocAuditfive.getState());
        }
       /** if (dcaBDocAuditfive.getAuditState()!=null && (dcaBDocAuditfive.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocAuditfive::getAuditState, dcaBDocAuditfive.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocAuditfive.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocAuditfive.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocAuditfive::getCreateTime, dcaBDocAuditfive.getCreateTimeFrom())
                                .le(DcaBDocAuditfive::getCreateTime, dcaBDocAuditfive.getCreateTimeTo());
                                }

        Page<DcaBDocAuditfive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAuditfive> findDcaBDocAuditfiveList (QueryRequest request, DcaBDocAuditfive dcaBDocAuditfive){
        try{
        Page<DcaBDocAuditfive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAuditfive(page,dcaBDocAuditfive);
        }catch(Exception e){
        log.error("获取近五年总体评价情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAuditfive(DcaBDocAuditfive dcaBDocAuditfive){
                dcaBDocAuditfive.setId(UUID.randomUUID().toString());
        dcaBDocAuditfive.setCreateTime(new Date());
        dcaBDocAuditfive.setIsDeletemark(1);
        this.save(dcaBDocAuditfive);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAuditfive(DcaBDocAuditfive dcaBDocAuditfive){
        dcaBDocAuditfive.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAuditfive(dcaBDocAuditfive);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAuditfives(String[]Ids){
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