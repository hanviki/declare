package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBApplyjob;
import cc.mrbird.febs.dca.dao.DcaBApplyjobMapper;
import cc.mrbird.febs.dca.service.IDcaBApplyjobService;
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
 * 拟聘岗位 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBApplyjobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBApplyjobServiceImpl extends ServiceImpl<DcaBApplyjobMapper, DcaBApplyjob> implements IDcaBApplyjobService {


@Override
public IPage<DcaBApplyjob> findDcaBApplyjobs(QueryRequest request, DcaBApplyjob dcaBApplyjob){
        try{
        LambdaQueryWrapper<DcaBApplyjob> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBApplyjob::getIsDeletemark, 1);//1是未删 0是已删


            if (StringUtils.isNotBlank(dcaBApplyjob.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBApplyjob::getUserAccount, dcaBApplyjob.getUserAccount()).or().like(DcaBApplyjob::getUserAccountName, dcaBApplyjob.getUserAccount()));
            }
                                if (dcaBApplyjob.getState()!=null) {
                                queryWrapper.eq(DcaBApplyjob::getState, dcaBApplyjob.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBApplyjob.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBApplyjob.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBApplyjob::getCreateTime, dcaBApplyjob.getCreateTimeFrom())
                                .le(DcaBApplyjob::getCreateTime, dcaBApplyjob.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBApplyjob.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBApplyjob.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBApplyjob::getModifyTime, dcaBApplyjob.getModifyTimeFrom())
                                .le(DcaBApplyjob::getModifyTime, dcaBApplyjob.getModifyTimeTo());
                                }

        Page<DcaBApplyjob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBApplyjob> findDcaBApplyjobList (QueryRequest request, DcaBApplyjob dcaBApplyjob){
        try{
        Page<DcaBApplyjob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBApplyjob(page,dcaBApplyjob);
        }catch(Exception e){
        log.error("获取拟聘岗位失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBApplyjob(DcaBApplyjob dcaBApplyjob){
                dcaBApplyjob.setId(UUID.randomUUID().toString());
        dcaBApplyjob.setCreateTime(new Date());
        dcaBApplyjob.setIsDeletemark(1);
        this.save(dcaBApplyjob);
        }

@Override
@Transactional
public void updateDcaBApplyjob(DcaBApplyjob dcaBApplyjob){
        dcaBApplyjob.setModifyTime(new Date());
        this.baseMapper.updateDcaBApplyjob(dcaBApplyjob);
        }

@Override
@Transactional
public void deleteDcaBApplyjobs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }