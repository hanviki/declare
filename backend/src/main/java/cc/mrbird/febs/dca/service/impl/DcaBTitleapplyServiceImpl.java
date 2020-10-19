package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTitleapply;
import cc.mrbird.febs.dca.dao.DcaBTitleapplyMapper;
import cc.mrbird.febs.dca.service.IDcaBTitleapplyService;
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
 * 职称申请表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
@Slf4j
@Service("IDcaBTitleapplyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTitleapplyServiceImpl extends ServiceImpl<DcaBTitleapplyMapper, DcaBTitleapply> implements IDcaBTitleapplyService {


@Override
public IPage<DcaBTitleapply> findDcaBTitleapplys(QueryRequest request, DcaBTitleapply dcaBTitleapply){
        try{
        LambdaQueryWrapper<DcaBTitleapply> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTitleapply::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBTitleapply.getUserAccount())) {
                                queryWrapper.like(DcaBTitleapply::getUserAccount, dcaBTitleapply.getUserAccount());
                                }
                                if (StringUtils.isNotBlank(dcaBTitleapply.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTitleapply.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTitleapply::getCreateTime, dcaBTitleapply.getCreateTimeFrom())
                                .le(DcaBTitleapply::getCreateTime, dcaBTitleapply.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTitleapply.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTitleapply.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTitleapply::getModifyTime, dcaBTitleapply.getModifyTimeFrom())
                                .le(DcaBTitleapply::getModifyTime, dcaBTitleapply.getModifyTimeTo());
                                }

        Page<DcaBTitleapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTitleapply> findDcaBTitleapplyList (QueryRequest request, DcaBTitleapply dcaBTitleapply){
        try{
        Page<DcaBTitleapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTitleapply(page,dcaBTitleapply);
        }catch(Exception e){
        log.error("获取职称申请表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTitleapply(DcaBTitleapply dcaBTitleapply){
                dcaBTitleapply.setId(UUID.randomUUID().toString());
        dcaBTitleapply.setCreateTime(new Date());
        dcaBTitleapply.setIsDeletemark(1);
        this.save(dcaBTitleapply);
        }

@Override
@Transactional
public void updateDcaBTitleapply(DcaBTitleapply dcaBTitleapply){
        dcaBTitleapply.setModifyTime(new Date());
        this.baseMapper.updateDcaBTitleapply(dcaBTitleapply);
        }

@Override
@Transactional
public void deleteDcaBTitleapplys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }