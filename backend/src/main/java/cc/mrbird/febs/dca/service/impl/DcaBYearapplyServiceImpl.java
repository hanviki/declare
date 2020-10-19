package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBYearapply;
import cc.mrbird.febs.dca.dao.DcaBYearapplyMapper;
import cc.mrbird.febs.dca.service.IDcaBYearapplyService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-24
 */
@Slf4j
@Service("IDcaBYearapplyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBYearapplyServiceImpl extends ServiceImpl<DcaBYearapplyMapper, DcaBYearapply> implements IDcaBYearapplyService {


@Override
public IPage<DcaBYearapply> findDcaBYearapplys(QueryRequest request, DcaBYearapply dcaBYearapply){
        try{
        LambdaQueryWrapper<DcaBYearapply> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBYearapply::getIsDeletemark, 1);//1是未删 0是已删

                                if (dcaBYearapply.getState()!=null) {
                                queryWrapper.eq(DcaBYearapply::getState, dcaBYearapply.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBYearapply.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBYearapply.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBYearapply::getCreateTime, dcaBYearapply.getCreateTimeFrom())
                                .le(DcaBYearapply::getCreateTime, dcaBYearapply.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBYearapply.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBYearapply.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBYearapply::getModifyTime, dcaBYearapply.getModifyTimeFrom())
                                .le(DcaBYearapply::getModifyTime, dcaBYearapply.getModifyTimeTo());
                                }

        Page<DcaBYearapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBYearapply> findDcaBYearapplyList (QueryRequest request, DcaBYearapply dcaBYearapply){
        try{
        Page<DcaBYearapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBYearapply(page,dcaBYearapply);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBYearapply(DcaBYearapply dcaBYearapply){
                dcaBYearapply.setId(UUID.randomUUID().toString());
        dcaBYearapply.setCreateTime(new Date());
        dcaBYearapply.setIsDeletemark(1);
        this.save(dcaBYearapply);
        }

@Override
@Transactional
public void updateDcaBYearapply(DcaBYearapply dcaBYearapply){
        dcaBYearapply.setModifyTime(new Date());
        this.baseMapper.updateDcaBYearapply(dcaBYearapply);
        }

@Override
@Transactional
public void deleteDcaBYearapplys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }