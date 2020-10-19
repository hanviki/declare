package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaDDeclaremodules;
import cc.mrbird.febs.dca.dao.DcaDDeclaremodulesMapper;
import cc.mrbird.febs.dca.service.IDcaDDeclaremodulesService;
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
 * 申请项目表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-22
 */
@Slf4j
@Service("IDcaDDeclaremodulesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDDeclaremodulesServiceImpl extends ServiceImpl<DcaDDeclaremodulesMapper, DcaDDeclaremodules> implements IDcaDDeclaremodulesService {


@Override
public IPage<DcaDDeclaremodules> findDcaDDeclaremoduless(QueryRequest request, DcaDDeclaremodules dcaDDeclaremodules){
        try{
        LambdaQueryWrapper<DcaDDeclaremodules> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDDeclaremodules::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaDDeclaremodules.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaDDeclaremodules.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaDDeclaremodules::getCreateTime, dcaDDeclaremodules.getCreateTimeFrom())
                                .le(DcaDDeclaremodules::getCreateTime, dcaDDeclaremodules.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaDDeclaremodules.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaDDeclaremodules.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaDDeclaremodules::getModifyTime, dcaDDeclaremodules.getModifyTimeFrom())
                                .le(DcaDDeclaremodules::getModifyTime, dcaDDeclaremodules.getModifyTimeTo());
                                }

        Page<DcaDDeclaremodules> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDDeclaremodules> findDcaDDeclaremodulesList (QueryRequest request, DcaDDeclaremodules dcaDDeclaremodules){
        try{
        Page<DcaDDeclaremodules> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDDeclaremodules(page,dcaDDeclaremodules);
        }catch(Exception e){
        log.error("获取申请项目表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaDDeclaremodules(DcaDDeclaremodules dcaDDeclaremodules){
                dcaDDeclaremodules.setId(UUID.randomUUID().toString());
        dcaDDeclaremodules.setCreateTime(new Date());
        dcaDDeclaremodules.setIsDeletemark(1);
        this.save(dcaDDeclaremodules);
        }

@Override
@Transactional
public void updateDcaDDeclaremodules(DcaDDeclaremodules dcaDDeclaremodules){
        dcaDDeclaremodules.setModifyTime(new Date());
        this.baseMapper.updateDcaDDeclaremodules(dcaDDeclaremodules);
        }

@Override
@Transactional
public void deleteDcaDDeclaremoduless(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }