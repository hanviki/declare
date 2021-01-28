package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocSciencepublish;
import cc.mrbird.febs.doctor.dao.DcaBDocSciencepublishMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocSciencepublishService;
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
 * 科研论文 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocSciencepublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocSciencepublishServiceImpl extends ServiceImpl<DcaBDocSciencepublishMapper, DcaBDocSciencepublish> implements IDcaBDocSciencepublishService {


@Override
@DS("slave")
public IPage<DcaBDocSciencepublish> findDcaBDocSciencepublishs(QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish){
        try{
        LambdaQueryWrapper<DcaBDocSciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocSciencepublish::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocSciencepublish.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocSciencepublish::getUserAccount, dcaBDocSciencepublish.getUserAccount()).or()
        .like(DcaBDocSciencepublish::getUserAccountName, dcaBDocSciencepublish.getUserAccount()));

        }
        if (dcaBDocSciencepublish.getState()!=null) {
        queryWrapper.eq(DcaBDocSciencepublish::getState, dcaBDocSciencepublish.getState());
        }
       /** if (dcaBDocSciencepublish.getAuditState()!=null && (dcaBDocSciencepublish.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocSciencepublish::getAuditState, dcaBDocSciencepublish.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocSciencepublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocSciencepublish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocSciencepublish::getCreateTime, dcaBDocSciencepublish.getCreateTimeFrom())
                                .le(DcaBDocSciencepublish::getCreateTime, dcaBDocSciencepublish.getCreateTimeTo());
                                }

        Page<DcaBDocSciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocSciencepublish> findDcaBDocSciencepublishList (QueryRequest request, DcaBDocSciencepublish dcaBDocSciencepublish){
        try{
        Page<DcaBDocSciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocSciencepublish(page,dcaBDocSciencepublish);
        }catch(Exception e){
        log.error("获取科研论文失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocSciencepublish(DcaBDocSciencepublish dcaBDocSciencepublish){
                dcaBDocSciencepublish.setId(UUID.randomUUID().toString());
        dcaBDocSciencepublish.setCreateTime(new Date());
        dcaBDocSciencepublish.setIsDeletemark(1);
        this.save(dcaBDocSciencepublish);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocSciencepublish(DcaBDocSciencepublish dcaBDocSciencepublish){
        dcaBDocSciencepublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocSciencepublish(dcaBDocSciencepublish);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocSciencepublishs(String[]Ids){
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