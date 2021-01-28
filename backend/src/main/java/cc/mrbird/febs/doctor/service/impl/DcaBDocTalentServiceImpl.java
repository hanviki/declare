package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocTalent;
import cc.mrbird.febs.doctor.dao.DcaBDocTalentMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocTalentService;
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
 * 任现职以来完成研究生教学人才培养情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocTalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocTalentServiceImpl extends ServiceImpl<DcaBDocTalentMapper, DcaBDocTalent> implements IDcaBDocTalentService {


@Override
@DS("slave")
public IPage<DcaBDocTalent> findDcaBDocTalents(QueryRequest request, DcaBDocTalent dcaBDocTalent){
        try{
        LambdaQueryWrapper<DcaBDocTalent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocTalent::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocTalent.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocTalent::getUserAccount, dcaBDocTalent.getUserAccount()).or()
        .like(DcaBDocTalent::getUserAccountName, dcaBDocTalent.getUserAccount()));

        }
        if (dcaBDocTalent.getState()!=null) {
        queryWrapper.eq(DcaBDocTalent::getState, dcaBDocTalent.getState());
        }
       /** if (dcaBDocTalent.getAuditState()!=null && (dcaBDocTalent.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocTalent::getAuditState, dcaBDocTalent.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocTalent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocTalent.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocTalent::getCreateTime, dcaBDocTalent.getCreateTimeFrom())
                                .le(DcaBDocTalent::getCreateTime, dcaBDocTalent.getCreateTimeTo());
                                }

        Page<DcaBDocTalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocTalent> findDcaBDocTalentList (QueryRequest request, DcaBDocTalent dcaBDocTalent){
        try{
        Page<DcaBDocTalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocTalent(page,dcaBDocTalent);
        }catch(Exception e){
        log.error("获取任现职以来完成研究生教学人才培养情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocTalent(DcaBDocTalent dcaBDocTalent){
                dcaBDocTalent.setId(UUID.randomUUID().toString());
        dcaBDocTalent.setCreateTime(new Date());
        dcaBDocTalent.setIsDeletemark(1);
        this.save(dcaBDocTalent);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocTalent(DcaBDocTalent dcaBDocTalent){
        dcaBDocTalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocTalent(dcaBDocTalent);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocTalents(String[]Ids){
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