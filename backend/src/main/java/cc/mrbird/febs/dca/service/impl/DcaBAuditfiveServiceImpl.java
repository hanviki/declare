package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAuditfive;
import cc.mrbird.febs.dca.dao.DcaBAuditfiveMapper;
import cc.mrbird.febs.dca.service.IDcaBAuditfiveService;
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
 * 近五年总体评价情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-12
 */
@Slf4j
@Service("IDcaBAuditfiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAuditfiveServiceImpl extends ServiceImpl<DcaBAuditfiveMapper, DcaBAuditfive> implements IDcaBAuditfiveService {


@Override
public IPage<DcaBAuditfive> findDcaBAuditfives(QueryRequest request, DcaBAuditfive dcaBAuditfive){
        try{
        LambdaQueryWrapper<DcaBAuditfive> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAuditfive::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBAuditfive.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAuditfive.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBAuditfive::getCreateTime, dcaBAuditfive.getCreateTimeFrom())
                                .le(DcaBAuditfive::getCreateTime, dcaBAuditfive.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBAuditfive.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBAuditfive.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBAuditfive::getModifyTime, dcaBAuditfive.getModifyTimeFrom())
                                .le(DcaBAuditfive::getModifyTime, dcaBAuditfive.getModifyTimeTo());
                                }

        Page<DcaBAuditfive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBAuditfive> findDcaBAuditfiveList (QueryRequest request, DcaBAuditfive dcaBAuditfive){
        try{
        Page<DcaBAuditfive> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBAuditfive(page,dcaBAuditfive);
        }catch(Exception e){
        log.error("获取近五年总体评价情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBAuditfive(DcaBAuditfive dcaBAuditfive){
                dcaBAuditfive.setId(UUID.randomUUID().toString());
        dcaBAuditfive.setCreateTime(new Date());
        dcaBAuditfive.setIsDeletemark(1);
        this.save(dcaBAuditfive);
        }

@Override
@Transactional
public void updateDcaBAuditfive(DcaBAuditfive dcaBAuditfive){
        dcaBAuditfive.setModifyTime(new Date());
        this.baseMapper.updateDcaBAuditfive(dcaBAuditfive);
        }

@Override
@Transactional
public void deleteDcaBAuditfives(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }