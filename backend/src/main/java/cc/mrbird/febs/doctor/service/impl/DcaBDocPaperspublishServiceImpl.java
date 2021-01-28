package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocPaperspublish;
import cc.mrbird.febs.doctor.dao.DcaBDocPaperspublishMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocPaperspublishService;
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
 * 教学论文出版教材 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocPaperspublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocPaperspublishServiceImpl extends ServiceImpl<DcaBDocPaperspublishMapper, DcaBDocPaperspublish> implements IDcaBDocPaperspublishService {


@Override
@DS("slave")
public IPage<DcaBDocPaperspublish> findDcaBDocPaperspublishs(QueryRequest request, DcaBDocPaperspublish dcaBDocPaperspublish){
        try{
        LambdaQueryWrapper<DcaBDocPaperspublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocPaperspublish::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocPaperspublish.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocPaperspublish::getUserAccount, dcaBDocPaperspublish.getUserAccount()).or()
        .like(DcaBDocPaperspublish::getUserAccountName, dcaBDocPaperspublish.getUserAccount()));

        }
        if (dcaBDocPaperspublish.getState()!=null) {
        queryWrapper.eq(DcaBDocPaperspublish::getState, dcaBDocPaperspublish.getState());
        }
       /** if (dcaBDocPaperspublish.getAuditState()!=null && (dcaBDocPaperspublish.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocPaperspublish::getAuditState, dcaBDocPaperspublish.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocPaperspublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocPaperspublish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocPaperspublish::getCreateTime, dcaBDocPaperspublish.getCreateTimeFrom())
                                .le(DcaBDocPaperspublish::getCreateTime, dcaBDocPaperspublish.getCreateTimeTo());
                                }

        Page<DcaBDocPaperspublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocPaperspublish> findDcaBDocPaperspublishList (QueryRequest request, DcaBDocPaperspublish dcaBDocPaperspublish){
        try{
        Page<DcaBDocPaperspublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocPaperspublish(page,dcaBDocPaperspublish);
        }catch(Exception e){
        log.error("获取教学论文出版教材失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocPaperspublish(DcaBDocPaperspublish dcaBDocPaperspublish){
                dcaBDocPaperspublish.setId(UUID.randomUUID().toString());
        dcaBDocPaperspublish.setCreateTime(new Date());
        dcaBDocPaperspublish.setIsDeletemark(1);
        this.save(dcaBDocPaperspublish);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocPaperspublish(DcaBDocPaperspublish dcaBDocPaperspublish){
        dcaBDocPaperspublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocPaperspublish(dcaBDocPaperspublish);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocPaperspublishs(String[]Ids){
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