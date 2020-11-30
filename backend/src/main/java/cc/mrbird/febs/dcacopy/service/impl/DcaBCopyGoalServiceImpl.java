package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyGoal;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyGoalMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyGoalService;
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
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyGoalService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyGoalServiceImpl extends ServiceImpl<DcaBCopyGoalMapper, DcaBCopyGoal> implements IDcaBCopyGoalService {


@Override
public IPage<DcaBCopyGoal> findDcaBCopyGoals(QueryRequest request, DcaBCopyGoal dcaBCopyGoal){
        try{
        LambdaQueryWrapper<DcaBCopyGoal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyGoal::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyGoal.getDcaYear())) {
                                queryWrapper.like(DcaBCopyGoal::getDcaYear, dcaBCopyGoal.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyGoal.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyGoal::getUserAccountName, dcaBCopyGoal.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyGoal.getUserAccount())) {
                                queryWrapper.like(DcaBCopyGoal::getUserAccount, dcaBCopyGoal.getUserAccount());
                                }

        Page<DcaBCopyGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyGoal> findDcaBCopyGoalList (QueryRequest request, DcaBCopyGoal dcaBCopyGoal){
        try{
        Page<DcaBCopyGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyGoal(page,dcaBCopyGoal);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyGoal(DcaBCopyGoal dcaBCopyGoal){
                dcaBCopyGoal.setId(UUID.randomUUID().toString());
        dcaBCopyGoal.setCreateTime(new Date());
        dcaBCopyGoal.setIsDeletemark(1);
        this.save(dcaBCopyGoal);
        }

@Override
@Transactional
public void updateDcaBCopyGoal(DcaBCopyGoal dcaBCopyGoal){
        dcaBCopyGoal.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyGoal(dcaBCopyGoal);
        }

@Override
@Transactional
public void deleteDcaBCopyGoals(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyGoal> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyGoal> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyGoal::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyGoal::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }