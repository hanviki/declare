package cc.mrbird.febs.check.service.impl;

import cc.mrbird.febs.check.entity.CheckDTitle;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.check.entity.CheckBSetting;
import cc.mrbird.febs.check.dao.CheckBSettingMapper;
import cc.mrbird.febs.check.service.ICheckBSettingService;
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
 * 指标配置表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
@Slf4j
@Service("ICheckBSettingService")
@DS("slave")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckBSettingServiceImpl extends ServiceImpl<CheckBSettingMapper, CheckBSetting> implements ICheckBSettingService {


@Override
@DS("slave")
public IPage<CheckBSetting> findCheckBSettings(QueryRequest request, CheckBSetting checkBSetting){
        try{
        LambdaQueryWrapper<CheckBSetting> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CheckBSetting::getIsDeletemark, 1);//1是未删 0是已删


        Page<CheckBSetting> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<CheckBSetting> findCheckBSettingList (QueryRequest request, CheckBSetting checkBSetting){
        try{
        Page<CheckBSetting> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findCheckBSetting(page,checkBSetting);
        }catch(Exception e){
        log.error("获取指标配置表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createCheckBSetting(CheckBSetting checkBSetting){
                checkBSetting.setCreateTime(new Date());
        checkBSetting.setIsDeletemark(1);
        this.save(checkBSetting);
        }

@Override
@Transactional
@DS("slave")
public void updateCheckBSetting(CheckBSetting checkBSetting){
        checkBSetting.setModifyTime(new Date());
        this.baseMapper.updateCheckBSetting(checkBSetting);
        }

@Override
@Transactional
@DS("slave")
public void deleteCheckBSettings(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public List<CheckBSetting> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<CheckBSetting> queryWrapper=new LambdaQueryWrapper<>();

      return  this.baseMapper.selectList(queryWrapper);
        }
        @Override
        @Transactional
        @DS("slave")
        public  List<CheckDTitle> getTitleByUserAccount(String userAccount){
        return  this.baseMapper.findTitleByUserAccount(userAccount);
        }
        }