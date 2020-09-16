package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBGraduate;
import cc.mrbird.febs.dca.dao.DcaBGraduateMapper;
import cc.mrbird.febs.dca.service.IDcaBGraduateService;
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
 * 研究生情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-15
 */
@Slf4j
@Service("IDcaBGraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBGraduateServiceImpl extends ServiceImpl<DcaBGraduateMapper, DcaBGraduate> implements IDcaBGraduateService {


@Override
public IPage<DcaBGraduate> findDcaBGraduates(QueryRequest request, DcaBGraduate dcaBGraduate){
        try{
        LambdaQueryWrapper<DcaBGraduate> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBGraduate::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBGraduate.getUserAccount())) {
                                queryWrapper.like(DcaBGraduate::getUserAccount, dcaBGraduate.getUserAccount());
                                }
                                if (dcaBGraduate.getState()!=null) {
                                queryWrapper.eq(DcaBGraduate::getState, dcaBGraduate.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBGraduate.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBGraduate.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBGraduate::getCreateTime, dcaBGraduate.getCreateTimeFrom())
                                .le(DcaBGraduate::getCreateTime, dcaBGraduate.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBGraduate.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBGraduate.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBGraduate::getModifyTime, dcaBGraduate.getModifyTimeFrom())
                                .le(DcaBGraduate::getModifyTime, dcaBGraduate.getModifyTimeTo());
                                }

        Page<DcaBGraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBGraduate> findDcaBGraduateList (QueryRequest request, DcaBGraduate dcaBGraduate){
        try{
        Page<DcaBGraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBGraduate(page,dcaBGraduate);
        }catch(Exception e){
        log.error("获取研究生情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBGraduate(DcaBGraduate dcaBGraduate){
                dcaBGraduate.setId(UUID.randomUUID().toString());
        dcaBGraduate.setCreateTime(new Date());
        dcaBGraduate.setIsDeletemark(1);
        this.save(dcaBGraduate);
        }

@Override
@Transactional
public void updateDcaBGraduate(DcaBGraduate dcaBGraduate){
        dcaBGraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBGraduate(dcaBGraduate);
        }

@Override
@Transactional
public void deleteDcaBGraduates(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }