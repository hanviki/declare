package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocPatent;
import cc.mrbird.febs.doctor.dao.DcaBDocPatentMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocPatentService;
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
 * 申请专利情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocPatentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocPatentServiceImpl extends ServiceImpl<DcaBDocPatentMapper, DcaBDocPatent> implements IDcaBDocPatentService {


@Override
@DS("slave")
public IPage<DcaBDocPatent> findDcaBDocPatents(QueryRequest request, DcaBDocPatent dcaBDocPatent){
        try{
        LambdaQueryWrapper<DcaBDocPatent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocPatent::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocPatent.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocPatent::getUserAccount, dcaBDocPatent.getUserAccount()).or()
        .like(DcaBDocPatent::getUserAccountName, dcaBDocPatent.getUserAccount()));

        }
        if (dcaBDocPatent.getState()!=null) {
        queryWrapper.eq(DcaBDocPatent::getState, dcaBDocPatent.getState());
        }
       /** if (dcaBDocPatent.getAuditState()!=null && (dcaBDocPatent.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocPatent::getAuditState, dcaBDocPatent.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocPatent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocPatent.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocPatent::getCreateTime, dcaBDocPatent.getCreateTimeFrom())
                                .le(DcaBDocPatent::getCreateTime, dcaBDocPatent.getCreateTimeTo());
                                }

        Page<DcaBDocPatent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocPatent> findDcaBDocPatentList (QueryRequest request, DcaBDocPatent dcaBDocPatent){
        try{
        Page<DcaBDocPatent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocPatent(page,dcaBDocPatent);
        }catch(Exception e){
        log.error("获取申请专利情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocPatent(DcaBDocPatent dcaBDocPatent){
                dcaBDocPatent.setId(UUID.randomUUID().toString());
        dcaBDocPatent.setCreateTime(new Date());
        dcaBDocPatent.setIsDeletemark(1);
        this.save(dcaBDocPatent);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocPatent(DcaBDocPatent dcaBDocPatent){
        dcaBDocPatent.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocPatent(dcaBDocPatent);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocPatents(String[]Ids){
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