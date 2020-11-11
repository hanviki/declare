package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBPersonalsummary;
import cc.mrbird.febs.dca.dao.DcaBPersonalsummaryMapper;
import cc.mrbird.febs.dca.service.IDcaBPersonalsummaryService;
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
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBPersonalsummaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBPersonalsummaryServiceImpl extends ServiceImpl<DcaBPersonalsummaryMapper, DcaBPersonalsummary> implements IDcaBPersonalsummaryService {


@Override
public IPage<DcaBPersonalsummary> findDcaBPersonalsummarys(QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary){
        try{
        LambdaQueryWrapper<DcaBPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBPersonalsummary::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBPersonalsummary.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBPersonalsummary::getUserAccount, dcaBPersonalsummary.getUserAccount()).or()
                        .like(DcaBPersonalsummary::getUserAccountName, dcaBPersonalsummary.getUserAccount()));

            }
                                if (dcaBPersonalsummary.getState()!=null) {
                                queryWrapper.eq(DcaBPersonalsummary::getState, dcaBPersonalsummary.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBPersonalsummary.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBPersonalsummary.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBPersonalsummary::getCreateTime, dcaBPersonalsummary.getCreateTimeFrom())
                                .le(DcaBPersonalsummary::getCreateTime, dcaBPersonalsummary.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBPersonalsummary.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBPersonalsummary.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBPersonalsummary::getModifyTime, dcaBPersonalsummary.getModifyTimeFrom())
                                .le(DcaBPersonalsummary::getModifyTime, dcaBPersonalsummary.getModifyTimeTo());
                                }

        Page<DcaBPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBPersonalsummary> findDcaBPersonalsummaryList (QueryRequest request, DcaBPersonalsummary dcaBPersonalsummary){
        try{
        Page<DcaBPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBPersonalsummary(page,dcaBPersonalsummary);
        }catch(Exception e){
        log.error("获取个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBPersonalsummary(DcaBPersonalsummary dcaBPersonalsummary){
                dcaBPersonalsummary.setId(UUID.randomUUID().toString());
        dcaBPersonalsummary.setCreateTime(new Date());
        dcaBPersonalsummary.setIsDeletemark(1);
        this.save(dcaBPersonalsummary);
        }

@Override
@Transactional
public void updateDcaBPersonalsummary(DcaBPersonalsummary dcaBPersonalsummary){
        dcaBPersonalsummary.setModifyTime(new Date());
        this.baseMapper.updateDcaBPersonalsummary(dcaBPersonalsummary);
        }

@Override
@Transactional
public void deleteDcaBPersonalsummarys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }