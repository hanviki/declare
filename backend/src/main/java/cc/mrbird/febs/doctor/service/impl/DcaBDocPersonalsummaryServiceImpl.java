package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocPersonalsummary;
import cc.mrbird.febs.doctor.dao.DcaBDocPersonalsummaryMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocPersonalsummaryService;
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
 * 个人总结 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocPersonalsummaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocPersonalsummaryServiceImpl extends ServiceImpl<DcaBDocPersonalsummaryMapper, DcaBDocPersonalsummary> implements IDcaBDocPersonalsummaryService {


@Override
@DS("slave")
public IPage<DcaBDocPersonalsummary> findDcaBDocPersonalsummarys(QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary){
        try{
        LambdaQueryWrapper<DcaBDocPersonalsummary> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocPersonalsummary::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocPersonalsummary.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocPersonalsummary::getUserAccount, dcaBDocPersonalsummary.getUserAccount()).or()
        .like(DcaBDocPersonalsummary::getUserAccountName, dcaBDocPersonalsummary.getUserAccount()));

        }
        if (dcaBDocPersonalsummary.getState()!=null) {
        queryWrapper.eq(DcaBDocPersonalsummary::getState, dcaBDocPersonalsummary.getState());
        }
                if (StringUtils.isNotBlank(dcaBDocPersonalsummary.getDcaYear())) {
                        queryWrapper.eq(DcaBDocPersonalsummary::getDcaYear,dcaBDocPersonalsummary.getDcaYear());
                }
       /** if (dcaBDocPersonalsummary.getAuditState()!=null && (dcaBDocPersonalsummary.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocPersonalsummary::getAuditState, dcaBDocPersonalsummary.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocPersonalsummary.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocPersonalsummary.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocPersonalsummary::getCreateTime, dcaBDocPersonalsummary.getCreateTimeFrom())
                                .le(DcaBDocPersonalsummary::getCreateTime, dcaBDocPersonalsummary.getCreateTimeTo());
                                }

        Page<DcaBDocPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocPersonalsummary> findDcaBDocPersonalsummaryList (QueryRequest request, DcaBDocPersonalsummary dcaBDocPersonalsummary){
        try{
        Page<DcaBDocPersonalsummary> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocPersonalsummary(page,dcaBDocPersonalsummary);
        }catch(Exception e){
        log.error("获取个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocPersonalsummary(DcaBDocPersonalsummary dcaBDocPersonalsummary){
                dcaBDocPersonalsummary.setId(UUID.randomUUID().toString());
        dcaBDocPersonalsummary.setCreateTime(new Date());
        dcaBDocPersonalsummary.setIsDeletemark(1);
        this.save(dcaBDocPersonalsummary);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocPersonalsummary(DcaBDocPersonalsummary dcaBDocPersonalsummary){
        dcaBDocPersonalsummary.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocPersonalsummary(dcaBDocPersonalsummary);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocPersonalsummarys(String[]Ids){
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