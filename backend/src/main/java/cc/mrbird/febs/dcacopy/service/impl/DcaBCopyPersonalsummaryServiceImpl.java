package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPersonalsummary;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyPersonalsummaryMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyPersonalsummaryService;
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
 * 个人总结 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyPersonalsummaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyPersonalsummaryServiceImpl extends ServiceImpl<DcaBCopyPersonalsummaryMapper, DcaBCopyPersonalsummary> implements IDcaBCopyPersonalsummaryService {


@Override
public IPage<DcaBCopyPersonalsummary> findDcaBCopyPersonalsummarys(QueryRequest request, DcaBCopyPersonalsummary dcaBCopyPersonalsummary){
        try{
        LambdaQueryWrapper<DcaBCopyPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyPersonalsummary::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyPersonalsummary.getDcaYear())) {
                                queryWrapper.like(DcaBCopyPersonalsummary::getDcaYear, dcaBCopyPersonalsummary.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPersonalsummary.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyPersonalsummary::getUserAccountName, dcaBCopyPersonalsummary.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPersonalsummary.getUserAccount())) {
                                queryWrapper.like(DcaBCopyPersonalsummary::getUserAccount, dcaBCopyPersonalsummary.getUserAccount());
                                }

        Page<DcaBCopyPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyPersonalsummary> findDcaBCopyPersonalsummaryList (QueryRequest request, DcaBCopyPersonalsummary dcaBCopyPersonalsummary){
        try{
        Page<DcaBCopyPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyPersonalsummary(page,dcaBCopyPersonalsummary);
        }catch(Exception e){
        log.error("获取个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyPersonalsummary(DcaBCopyPersonalsummary dcaBCopyPersonalsummary){
                dcaBCopyPersonalsummary.setId(UUID.randomUUID().toString());
        dcaBCopyPersonalsummary.setCreateTime(new Date());
        dcaBCopyPersonalsummary.setIsDeletemark(1);
        this.save(dcaBCopyPersonalsummary);
        }

@Override
@Transactional
public void updateDcaBCopyPersonalsummary(DcaBCopyPersonalsummary dcaBCopyPersonalsummary){
        dcaBCopyPersonalsummary.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyPersonalsummary(dcaBCopyPersonalsummary);
        }

@Override
@Transactional
public void deleteDcaBCopyPersonalsummarys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyPersonalsummary> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyPersonalsummary::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyPersonalsummary::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }