package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAuditfiveother;
import cc.mrbird.febs.doctor.dao.DcaBDocAuditfiveotherMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAuditfiveotherService;
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
 * 中期考核和出站考核 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-13
 */
@Slf4j
@Service("IDcaBDocAuditfiveotherService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAuditfiveotherServiceImpl extends ServiceImpl<DcaBDocAuditfiveotherMapper, DcaBDocAuditfiveother> implements IDcaBDocAuditfiveotherService {


@Override
@DS("slave")
public IPage<DcaBDocAuditfiveother> findDcaBDocAuditfiveothers(QueryRequest request, DcaBDocAuditfiveother dcaBDocAuditfiveother){
        try{
        LambdaQueryWrapper<DcaBDocAuditfiveother> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAuditfiveother::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocAuditfiveother.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocAuditfiveother::getUserAccount, dcaBDocAuditfiveother.getUserAccount()).or()
        .like(DcaBDocAuditfiveother::getUserAccountName, dcaBDocAuditfiveother.getUserAccount()));

        }
        if (dcaBDocAuditfiveother.getState()!=null) {
        queryWrapper.eq(DcaBDocAuditfiveother::getState, dcaBDocAuditfiveother.getState());
        }
       /** if (dcaBDocAuditfiveother.getAuditState()!=null && (dcaBDocAuditfiveother.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocAuditfiveother::getAuditState, dcaBDocAuditfiveother.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocAuditfiveother.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocAuditfiveother.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocAuditfiveother::getCreateTime, dcaBDocAuditfiveother.getCreateTimeFrom())
                                .le(DcaBDocAuditfiveother::getCreateTime, dcaBDocAuditfiveother.getCreateTimeTo());
                                }

        Page<DcaBDocAuditfiveother> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAuditfiveother> findDcaBDocAuditfiveotherList (QueryRequest request, DcaBDocAuditfiveother dcaBDocAuditfiveother){
        try{
        Page<DcaBDocAuditfiveother> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAuditfiveother(page,dcaBDocAuditfiveother);
        }catch(Exception e){
        log.error("获取中期考核和出站考核失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAuditfiveother(DcaBDocAuditfiveother dcaBDocAuditfiveother){
                dcaBDocAuditfiveother.setId(UUID.randomUUID().toString());
        dcaBDocAuditfiveother.setCreateTime(new Date());
        dcaBDocAuditfiveother.setIsDeletemark(1);
        this.save(dcaBDocAuditfiveother);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAuditfiveother(DcaBDocAuditfiveother dcaBDocAuditfiveother){
        dcaBDocAuditfiveother.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAuditfiveother(dcaBDocAuditfiveother);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAuditfiveothers(String[]Ids){
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