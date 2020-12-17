package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAchievement;
import cc.mrbird.febs.dca.dao.DcaBAchievementMapper;
import cc.mrbird.febs.dca.service.IDcaBAchievementService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("IDcaBAchievementService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAchievementServiceImpl extends ServiceImpl<DcaBAchievementMapper, DcaBAchievement> implements IDcaBAchievementService {


        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBAchievement> findDcaBAchievements(QueryRequest request, DcaBAchievement dcaBAchievement){
        try{
        LambdaQueryWrapper<DcaBAchievement> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAchievement::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBAchievement.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBAchievement::getUserAccount, dcaBAchievement.getUserAccount()).or()
        .like(DcaBAchievement::getUserAccountName, dcaBAchievement.getUserAccount()));

        }
        if (dcaBAchievement.getState()!=null) {
        queryWrapper.eq(DcaBAchievement::getState, dcaBAchievement.getState());
        }
       /** if (dcaBAchievement.getAuditState()!=null && (dcaBAchievement.getAuditState()>=0)) {
        queryWrapper.eq(DcaBAchievement::getAuditState, dcaBAchievement.getAuditState());
        }*/
                if(StringUtils.isNotBlank(dcaBAchievement.getAuditManName())){// 年度 和高级、中级、初级
                        List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBAchievement.getAuditMan(),dcaBAchievement.getAuditManName());
                        if(userAccountsList.size()==0){
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBAchievement::getUserAccount,userAccountsList);
                }
                                if (StringUtils.isNotBlank(dcaBAchievement.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAchievement.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBAchievement::getCreateTime, dcaBAchievement.getCreateTimeFrom())
                                .le(DcaBAchievement::getCreateTime, dcaBAchievement.getCreateTimeTo());
                                }

        Page<DcaBAchievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBAchievement> findDcaBAchievementList (QueryRequest request, DcaBAchievement dcaBAchievement){
        try{
        Page<DcaBAchievement> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBAchievement(page,dcaBAchievement);
        }catch(Exception e){
        log.error("获取主要医疗业绩失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBAchievement(DcaBAchievement dcaBAchievement){
                dcaBAchievement.setId(UUID.randomUUID().toString());
        dcaBAchievement.setCreateTime(new Date());
        dcaBAchievement.setIsDeletemark(1);
        this.save(dcaBAchievement);
        }

@Override
@Transactional
public void updateDcaBAchievement(DcaBAchievement dcaBAchievement){
        dcaBAchievement.setModifyTime(new Date());
        this.baseMapper.updateDcaBAchievement(dcaBAchievement);
        }

@Override
@Transactional
public void deleteDcaBAchievements(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }