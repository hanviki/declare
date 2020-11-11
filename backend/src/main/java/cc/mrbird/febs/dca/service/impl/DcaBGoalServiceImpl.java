package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBGoal;
import cc.mrbird.febs.dca.dao.DcaBGoalMapper;
import cc.mrbird.febs.dca.service.IDcaBGoalService;
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
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBGoalService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBGoalServiceImpl extends ServiceImpl<DcaBGoalMapper, DcaBGoal> implements IDcaBGoalService {


@Override
public IPage<DcaBGoal> findDcaBGoals(QueryRequest request, DcaBGoal dcaBGoal){
        try{
        LambdaQueryWrapper<DcaBGoal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBGoal::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBGoal.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBGoal::getUserAccount, dcaBGoal.getUserAccount()).or()
                        .like(DcaBGoal::getUserAccountName, dcaBGoal.getUserAccount()));

            }
                                if (dcaBGoal.getState()!=null) {
                                queryWrapper.eq(DcaBGoal::getState, dcaBGoal.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBGoal.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBGoal.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBGoal::getCreateTime, dcaBGoal.getCreateTimeFrom())
                                .le(DcaBGoal::getCreateTime, dcaBGoal.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBGoal.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBGoal.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBGoal::getModifyTime, dcaBGoal.getModifyTimeFrom())
                                .le(DcaBGoal::getModifyTime, dcaBGoal.getModifyTimeTo());
                                }

        Page<DcaBGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBGoal> findDcaBGoalList (QueryRequest request, DcaBGoal dcaBGoal){
        try{
        Page<DcaBGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBGoal(page,dcaBGoal);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBGoal(DcaBGoal dcaBGoal){
                dcaBGoal.setId(UUID.randomUUID().toString());
        dcaBGoal.setCreateTime(new Date());
        dcaBGoal.setIsDeletemark(1);
        this.save(dcaBGoal);
        }

@Override
@Transactional
public void updateDcaBGoal(DcaBGoal dcaBGoal){
        dcaBGoal.setModifyTime(new Date());
        this.baseMapper.updateDcaBGoal(dcaBGoal);
        }

@Override
@Transactional
public void deleteDcaBGoals(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }