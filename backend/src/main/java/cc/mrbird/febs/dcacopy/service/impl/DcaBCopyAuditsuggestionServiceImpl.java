package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditsuggestion;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAuditsuggestionMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditsuggestionService;
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
 * 审核意见表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyAuditsuggestionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyAuditsuggestionServiceImpl extends ServiceImpl<DcaBCopyAuditsuggestionMapper, DcaBCopyAuditsuggestion> implements IDcaBCopyAuditsuggestionService {


@Override
public IPage<DcaBCopyAuditsuggestion> findDcaBCopyAuditsuggestions(QueryRequest request, DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion){
        try{
        LambdaQueryWrapper<DcaBCopyAuditsuggestion> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyAuditsuggestion::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyAuditsuggestion.getDcaYear())) {
                                queryWrapper.like(DcaBCopyAuditsuggestion::getDcaYear, dcaBCopyAuditsuggestion.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAuditsuggestion.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyAuditsuggestion::getUserAccountName, dcaBCopyAuditsuggestion.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAuditsuggestion.getUserAccount())) {
                                queryWrapper.like(DcaBCopyAuditsuggestion::getUserAccount, dcaBCopyAuditsuggestion.getUserAccount());
                                }

        Page<DcaBCopyAuditsuggestion> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyAuditsuggestion> findDcaBCopyAuditsuggestionList (QueryRequest request, DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion){
        try{
        Page<DcaBCopyAuditsuggestion> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyAuditsuggestion(page,dcaBCopyAuditsuggestion);
        }catch(Exception e){
        log.error("获取审核意见表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyAuditsuggestion(DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion){
                dcaBCopyAuditsuggestion.setId(UUID.randomUUID().toString());
        dcaBCopyAuditsuggestion.setCreateTime(new Date());
        dcaBCopyAuditsuggestion.setIsDeletemark(1);
        this.save(dcaBCopyAuditsuggestion);
        }

@Override
@Transactional
public void updateDcaBCopyAuditsuggestion(DcaBCopyAuditsuggestion dcaBCopyAuditsuggestion){
        dcaBCopyAuditsuggestion.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyAuditsuggestion(dcaBCopyAuditsuggestion);
        }

@Override
@Transactional
public void deleteDcaBCopyAuditsuggestions(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyAuditsuggestion> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyAuditsuggestion> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyAuditsuggestion::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyAuditsuggestion::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }