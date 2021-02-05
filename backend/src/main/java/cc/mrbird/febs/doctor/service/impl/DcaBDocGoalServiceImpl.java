package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocGoal;
import cc.mrbird.febs.doctor.dao.DcaBDocGoalMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocGoalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.dynamic.datasource.annotation.DS;
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
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocGoalService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocGoalServiceImpl extends ServiceImpl<DcaBDocGoalMapper, DcaBDocGoal> implements IDcaBDocGoalService {


@Override
@DS("slave")
public IPage<DcaBDocGoal> findDcaBDocGoals(QueryRequest request, DcaBDocGoal dcaBDocGoal){
        try{
        LambdaQueryWrapper<DcaBDocGoal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocGoal::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocGoal.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocGoal::getUserAccount, dcaBDocGoal.getUserAccount()).or()
        .like(DcaBDocGoal::getUserAccountName, dcaBDocGoal.getUserAccount()));

        }
                if (StringUtils.isNotBlank(dcaBDocGoal.getDcaYear())) {
                        queryWrapper.eq(DcaBDocGoal::getDcaYear,dcaBDocGoal.getDcaYear());
                }
        if (dcaBDocGoal.getState()!=null) {
        queryWrapper.eq(DcaBDocGoal::getState, dcaBDocGoal.getState());
        }
       /** if (dcaBDocGoal.getAuditState()!=null && (dcaBDocGoal.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocGoal::getAuditState, dcaBDocGoal.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocGoal.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocGoal.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocGoal::getCreateTime, dcaBDocGoal.getCreateTimeFrom())
                                .le(DcaBDocGoal::getCreateTime, dcaBDocGoal.getCreateTimeTo());
                                }

        Page<DcaBDocGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocGoal> findDcaBDocGoalList (QueryRequest request, DcaBDocGoal dcaBDocGoal){
        try{
        Page<DcaBDocGoal> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocGoal(page,dcaBDocGoal);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocGoal(DcaBDocGoal dcaBDocGoal){
                dcaBDocGoal.setId(UUID.randomUUID().toString());
        dcaBDocGoal.setCreateTime(new Date());
        dcaBDocGoal.setIsDeletemark(1);
        this.save(dcaBDocGoal);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocGoal(DcaBDocGoal dcaBDocGoal){
        dcaBDocGoal.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocGoal(dcaBDocGoal);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocGoals(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
@DS("slave")
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }