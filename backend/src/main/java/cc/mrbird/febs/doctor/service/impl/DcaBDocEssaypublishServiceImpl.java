package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocEssaypublish;
import cc.mrbird.febs.doctor.dao.DcaBDocEssaypublishMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocEssaypublishService;
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
 * 论文出版 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocEssaypublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocEssaypublishServiceImpl extends ServiceImpl<DcaBDocEssaypublishMapper, DcaBDocEssaypublish> implements IDcaBDocEssaypublishService {


@Override
@DS("slave")
public IPage<DcaBDocEssaypublish> findDcaBDocEssaypublishs(QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish){
        try{
        LambdaQueryWrapper<DcaBDocEssaypublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocEssaypublish::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocEssaypublish.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocEssaypublish::getUserAccount, dcaBDocEssaypublish.getUserAccount()).or()
        .like(DcaBDocEssaypublish::getUserAccountName, dcaBDocEssaypublish.getUserAccount()));

        }
        if (dcaBDocEssaypublish.getState()!=null) {
        queryWrapper.eq(DcaBDocEssaypublish::getState, dcaBDocEssaypublish.getState());
        }
       /** if (dcaBDocEssaypublish.getAuditState()!=null && (dcaBDocEssaypublish.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocEssaypublish::getAuditState, dcaBDocEssaypublish.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocEssaypublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocEssaypublish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocEssaypublish::getCreateTime, dcaBDocEssaypublish.getCreateTimeFrom())
                                .le(DcaBDocEssaypublish::getCreateTime, dcaBDocEssaypublish.getCreateTimeTo());
                                }

        Page<DcaBDocEssaypublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocEssaypublish> findDcaBDocEssaypublishList (QueryRequest request, DcaBDocEssaypublish dcaBDocEssaypublish){
        try{
        Page<DcaBDocEssaypublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocEssaypublish(page,dcaBDocEssaypublish);
        }catch(Exception e){
        log.error("获取论文出版失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocEssaypublish(DcaBDocEssaypublish dcaBDocEssaypublish){
                dcaBDocEssaypublish.setId(UUID.randomUUID().toString());
        dcaBDocEssaypublish.setCreateTime(new Date());
        dcaBDocEssaypublish.setIsDeletemark(1);
        this.save(dcaBDocEssaypublish);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocEssaypublish(DcaBDocEssaypublish dcaBDocEssaypublish){
        dcaBDocEssaypublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocEssaypublish(dcaBDocEssaypublish);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocEssaypublishs(String[]Ids){
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