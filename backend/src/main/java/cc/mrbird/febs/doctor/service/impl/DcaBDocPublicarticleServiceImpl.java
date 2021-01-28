package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocPublicarticle;
import cc.mrbird.febs.doctor.dao.DcaBDocPublicarticleMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocPublicarticleService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-12
 */
@Slf4j
@Service("IDcaBDocPublicarticleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocPublicarticleServiceImpl extends ServiceImpl<DcaBDocPublicarticleMapper, DcaBDocPublicarticle> implements IDcaBDocPublicarticleService {


@Override
@DS("slave")
public IPage<DcaBDocPublicarticle> findDcaBDocPublicarticles(QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle){
        try{
        LambdaQueryWrapper<DcaBDocPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocPublicarticle::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocPublicarticle.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocPublicarticle::getUserAccount, dcaBDocPublicarticle.getUserAccount()).or()
        .like(DcaBDocPublicarticle::getUserAccountName, dcaBDocPublicarticle.getUserAccount()));

        }
        if (dcaBDocPublicarticle.getState()!=null) {
        queryWrapper.eq(DcaBDocPublicarticle::getState, dcaBDocPublicarticle.getState());
        }
       /** if (dcaBDocPublicarticle.getAuditState()!=null && (dcaBDocPublicarticle.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocPublicarticle::getAuditState, dcaBDocPublicarticle.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocPublicarticle.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocPublicarticle.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocPublicarticle::getCreateTime, dcaBDocPublicarticle.getCreateTimeFrom())
                                .le(DcaBDocPublicarticle::getCreateTime, dcaBDocPublicarticle.getCreateTimeTo());
                                }

        Page<DcaBDocPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocPublicarticle> findDcaBDocPublicarticleList (QueryRequest request, DcaBDocPublicarticle dcaBDocPublicarticle){
        try{
        Page<DcaBDocPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocPublicarticle(page,dcaBDocPublicarticle);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocPublicarticle(DcaBDocPublicarticle dcaBDocPublicarticle){
                dcaBDocPublicarticle.setId(UUID.randomUUID().toString());
        dcaBDocPublicarticle.setCreateTime(new Date());
        dcaBDocPublicarticle.setIsDeletemark(1);
        this.save(dcaBDocPublicarticle);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocPublicarticle(DcaBDocPublicarticle dcaBDocPublicarticle){
        dcaBDocPublicarticle.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocPublicarticle(dcaBDocPublicarticle);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocPublicarticles(String[]Ids){
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