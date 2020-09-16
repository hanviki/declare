package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBInnovatebuild;
import cc.mrbird.febs.dca.dao.DcaBInnovatebuildMapper;
import cc.mrbird.febs.dca.service.IDcaBInnovatebuildService;
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
 * 改革及建设项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-15
 */
@Slf4j
@Service("IDcaBInnovatebuildService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBInnovatebuildServiceImpl extends ServiceImpl<DcaBInnovatebuildMapper, DcaBInnovatebuild> implements IDcaBInnovatebuildService {


@Override
public IPage<DcaBInnovatebuild> findDcaBInnovatebuilds(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild){
        try{
        LambdaQueryWrapper<DcaBInnovatebuild> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBInnovatebuild::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBInnovatebuild.getUserAccount())) {
                                queryWrapper.like(DcaBInnovatebuild::getUserAccount, dcaBInnovatebuild.getUserAccount());
                                }
                                if (dcaBInnovatebuild.getState()!=null) {
                                queryWrapper.eq(DcaBInnovatebuild::getState, dcaBInnovatebuild.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBInnovatebuild.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBInnovatebuild.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBInnovatebuild::getCreateTime, dcaBInnovatebuild.getCreateTimeFrom())
                                .le(DcaBInnovatebuild::getCreateTime, dcaBInnovatebuild.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBInnovatebuild.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBInnovatebuild.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBInnovatebuild::getModifyTime, dcaBInnovatebuild.getModifyTimeFrom())
                                .le(DcaBInnovatebuild::getModifyTime, dcaBInnovatebuild.getModifyTimeTo());
                                }

        Page<DcaBInnovatebuild> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBInnovatebuild> findDcaBInnovatebuildList (QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild){
        try{
        Page<DcaBInnovatebuild> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBInnovatebuild(page,dcaBInnovatebuild);
        }catch(Exception e){
        log.error("获取改革及建设项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild){
                dcaBInnovatebuild.setId(UUID.randomUUID().toString());
        dcaBInnovatebuild.setCreateTime(new Date());
        dcaBInnovatebuild.setIsDeletemark(1);
        this.save(dcaBInnovatebuild);
        }

@Override
@Transactional
public void updateDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild){
        dcaBInnovatebuild.setModifyTime(new Date());
        this.baseMapper.updateDcaBInnovatebuild(dcaBInnovatebuild);
        }

@Override
@Transactional
public void deleteDcaBInnovatebuilds(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }