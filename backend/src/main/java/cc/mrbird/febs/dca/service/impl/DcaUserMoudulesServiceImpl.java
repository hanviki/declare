package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaUserMoudules;
import cc.mrbird.febs.dca.dao.DcaUserMoudulesMapper;
import cc.mrbird.febs.dca.service.IDcaUserMoudulesService;
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
 * 申报模块和用户表关联表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */
@Slf4j
@Service("IDcaUserMoudulesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaUserMoudulesServiceImpl extends ServiceImpl<DcaUserMoudulesMapper, DcaUserMoudules> implements IDcaUserMoudulesService {


@Override
public IPage<DcaUserMoudules> findDcaUserMouduless(QueryRequest request, DcaUserMoudules dcaUserMoudules){
        try{
        LambdaQueryWrapper<DcaUserMoudules> queryWrapper=new LambdaQueryWrapper<>();
        //queryWrapper.eq(DcaUserMoudules::getIsDeletemark, 1);//1是未删 0是已删


        Page<DcaUserMoudules> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaUserMoudules> findDcaUserMoudulesList (QueryRequest request, DcaUserMoudules dcaUserMoudules){
        try{
        Page<DcaUserMoudules> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaUserMoudules(page,dcaUserMoudules);
        }catch(Exception e){
        log.error("获取申报模块和用户表关联表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaUserMoudules(DcaUserMoudules dcaUserMoudules){
                //dcaUserMoudules.setCreateTime(new Date());
        //dcaUserMoudules.setIsDeletemark(1);
        this.save(dcaUserMoudules);
        }

@Override
@Transactional
public void updateDcaUserMoudules(DcaUserMoudules dcaUserMoudules){
       // dcaUserMoudules.setModifyTime(new Date());
        this.baseMapper.updateDcaUserMoudules(dcaUserMoudules);
        }

@Override
@Transactional
public void deleteDcaUserMouduless(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
       public List<DcaUserMoudules> getMudulesByUserId(Integer userId){
                LambdaQueryWrapper<DcaUserMoudules> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(DcaUserMoudules::getUserId,userId);
               return this.baseMapper.selectList(queryWrapper);
        }
        }