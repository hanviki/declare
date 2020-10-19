package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAuditsuggestion;
import cc.mrbird.febs.dca.dao.DcaBAuditsuggestionMapper;
import cc.mrbird.febs.dca.service.IDcaBAuditsuggestionService;
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
 * @since 2020-09-22
 */
@Slf4j
@Service("IDcaBAuditsuggestionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAuditsuggestionServiceImpl extends ServiceImpl<DcaBAuditsuggestionMapper, DcaBAuditsuggestion> implements IDcaBAuditsuggestionService {


@Override
public IPage<DcaBAuditsuggestion> findDcaBAuditsuggestions(QueryRequest request, DcaBAuditsuggestion dcaBAuditsuggestion){
        try{
        LambdaQueryWrapper<DcaBAuditsuggestion> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAuditsuggestion::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBAuditsuggestion.getUserAccount())) {
                                queryWrapper.like(DcaBAuditsuggestion::getUserAccount, dcaBAuditsuggestion.getUserAccount());
                                }
                                if (dcaBAuditsuggestion.getState()!=null) {
                                queryWrapper.eq(DcaBAuditsuggestion::getState, dcaBAuditsuggestion.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBAuditsuggestion.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAuditsuggestion.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBAuditsuggestion::getCreateTime, dcaBAuditsuggestion.getCreateTimeFrom())
                                .le(DcaBAuditsuggestion::getCreateTime, dcaBAuditsuggestion.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBAuditsuggestion.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBAuditsuggestion.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBAuditsuggestion::getModifyTime, dcaBAuditsuggestion.getModifyTimeFrom())
                                .le(DcaBAuditsuggestion::getModifyTime, dcaBAuditsuggestion.getModifyTimeTo());
                                }

        Page<DcaBAuditsuggestion> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBAuditsuggestion> findDcaBAuditsuggestionList (QueryRequest request, DcaBAuditsuggestion dcaBAuditsuggestion){
        try{
        Page<DcaBAuditsuggestion> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBAuditsuggestion(page,dcaBAuditsuggestion);
        }catch(Exception e){
        log.error("获取审核意见表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBAuditsuggestion(DcaBAuditsuggestion dcaBAuditsuggestion){
                dcaBAuditsuggestion.setId(UUID.randomUUID().toString());
        dcaBAuditsuggestion.setCreateTime(new Date());
        dcaBAuditsuggestion.setIsDeletemark(1);
        this.save(dcaBAuditsuggestion);
        }

@Override
@Transactional
public void updateDcaBAuditsuggestion(DcaBAuditsuggestion dcaBAuditsuggestion){
        dcaBAuditsuggestion.setModifyTime(new Date());
        this.baseMapper.updateDcaBAuditsuggestion(dcaBAuditsuggestion);
        }

@Override
@Transactional
public void deleteDcaBAuditsuggestions(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }