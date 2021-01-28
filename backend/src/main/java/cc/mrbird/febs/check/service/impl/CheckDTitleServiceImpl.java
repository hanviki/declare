package cc.mrbird.febs.check.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.check.dao.CheckDTitleMapper;
import cc.mrbird.febs.check.service.ICheckDTitleService;
import com.baomidou.dynamic.datasource.annotation.DS;
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
 * 指标表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
@Slf4j
@Service("ICheckDTitleService")
@DS("slave")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckDTitleServiceImpl extends ServiceImpl<CheckDTitleMapper, CheckDTitle> implements ICheckDTitleService {


@Override
@DS("slave")
public IPage<CheckDTitle> findCheckDTitles(QueryRequest request, CheckDTitle checkDTitle){
        try{
        LambdaQueryWrapper<CheckDTitle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CheckDTitle::getIsDeletemark, 1);//1是未删 0是已删


        Page<CheckDTitle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<CheckDTitle> findCheckDTitleList (QueryRequest request, CheckDTitle checkDTitle){
        try{
        Page<CheckDTitle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findCheckDTitle(page,checkDTitle);
        }catch(Exception e){
        log.error("获取指标表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createCheckDTitle(CheckDTitle checkDTitle){
                checkDTitle.setCreateTime(new Date());
        checkDTitle.setIsDeletemark(1);
        this.save(checkDTitle);
        }

@Override
@Transactional
@DS("slave")
public void updateCheckDTitle(CheckDTitle checkDTitle){
        checkDTitle.setModifyTime(new Date());
        this.baseMapper.updateCheckDTitle(checkDTitle);
        }

@Override
@Transactional
@DS("slave")
public void deleteCheckDTitles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public List<CheckDTitle> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<CheckDTitle> queryWrapper=new LambdaQueryWrapper<>();

      return  this.baseMapper.selectList(queryWrapper);
        }

        }