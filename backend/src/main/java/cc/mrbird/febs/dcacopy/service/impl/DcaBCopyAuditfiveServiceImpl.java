package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditfive;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAuditfiveMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditfiveService;
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
 * 近五年总体评价情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyAuditfiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyAuditfiveServiceImpl extends ServiceImpl<DcaBCopyAuditfiveMapper, DcaBCopyAuditfive> implements IDcaBCopyAuditfiveService {


@Override
public IPage<DcaBCopyAuditfive> findDcaBCopyAuditfives(QueryRequest request, DcaBCopyAuditfive dcaBCopyAuditfive){
        try{
        LambdaQueryWrapper<DcaBCopyAuditfive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyAuditfive::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyAuditfive.getDcaYear())) {
                                queryWrapper.like(DcaBCopyAuditfive::getDcaYear, dcaBCopyAuditfive.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAuditfive.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyAuditfive::getUserAccountName, dcaBCopyAuditfive.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAuditfive.getUserAccount())) {
                                queryWrapper.like(DcaBCopyAuditfive::getUserAccount, dcaBCopyAuditfive.getUserAccount());
                                }

        Page<DcaBCopyAuditfive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyAuditfive> findDcaBCopyAuditfiveList (QueryRequest request, DcaBCopyAuditfive dcaBCopyAuditfive){
        try{
        Page<DcaBCopyAuditfive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyAuditfive(page,dcaBCopyAuditfive);
        }catch(Exception e){
        log.error("获取近五年总体评价情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyAuditfive(DcaBCopyAuditfive dcaBCopyAuditfive){
                dcaBCopyAuditfive.setId(UUID.randomUUID().toString());
        dcaBCopyAuditfive.setCreateTime(new Date());
        dcaBCopyAuditfive.setIsDeletemark(1);
        this.save(dcaBCopyAuditfive);
        }

@Override
@Transactional
public void updateDcaBCopyAuditfive(DcaBCopyAuditfive dcaBCopyAuditfive){
        dcaBCopyAuditfive.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyAuditfive(dcaBCopyAuditfive);
        }

@Override
@Transactional
public void deleteDcaBCopyAuditfives(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyAuditfive> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyAuditfive> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyAuditfive::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyAuditfive::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }