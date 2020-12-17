package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAchievement;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAchievementMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAchievementService;
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
 * 主要医疗业绩 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBCopyAchievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyAchievementServiceImpl extends ServiceImpl<DcaBCopyAchievementMapper, DcaBCopyAchievement> implements IDcaBCopyAchievementService {


@Override
public IPage<DcaBCopyAchievement> findDcaBCopyAchievements(QueryRequest request, DcaBCopyAchievement dcaBCopyAchievement){
        try{
        LambdaQueryWrapper<DcaBCopyAchievement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyAchievement::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyAchievement.getDcaYear())) {
                                queryWrapper.like(DcaBCopyAchievement::getDcaYear, dcaBCopyAchievement.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAchievement.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyAchievement::getUserAccountName, dcaBCopyAchievement.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAchievement.getUserAccount())) {
                                queryWrapper.like(DcaBCopyAchievement::getUserAccount, dcaBCopyAchievement.getUserAccount());
                                }

        Page<DcaBCopyAchievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyAchievement> findDcaBCopyAchievementList (QueryRequest request, DcaBCopyAchievement dcaBCopyAchievement){
        try{
        Page<DcaBCopyAchievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyAchievement(page,dcaBCopyAchievement);
        }catch(Exception e){
        log.error("获取主要医疗业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyAchievement(DcaBCopyAchievement dcaBCopyAchievement){
                dcaBCopyAchievement.setId(UUID.randomUUID().toString());
        dcaBCopyAchievement.setCreateTime(new Date());
        dcaBCopyAchievement.setIsDeletemark(1);
        this.save(dcaBCopyAchievement);
        }

@Override
@Transactional
public void updateDcaBCopyAchievement(DcaBCopyAchievement dcaBCopyAchievement){
        dcaBCopyAchievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyAchievement(dcaBCopyAchievement);
        }

@Override
@Transactional
public void deleteDcaBCopyAchievements(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyAchievement> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyAchievement> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyAchievement::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyAchievement::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }