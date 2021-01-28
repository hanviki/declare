package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocPrizeorpunish;
import cc.mrbird.febs.doctor.dao.DcaBDocPrizeorpunishMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocPrizeorpunishService;
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
 * 何时受奖励处分 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocPrizeorpunishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocPrizeorpunishServiceImpl extends ServiceImpl<DcaBDocPrizeorpunishMapper, DcaBDocPrizeorpunish> implements IDcaBDocPrizeorpunishService {


@Override
@DS("slave")
public IPage<DcaBDocPrizeorpunish> findDcaBDocPrizeorpunishs(QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
        try{
        LambdaQueryWrapper<DcaBDocPrizeorpunish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocPrizeorpunish::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocPrizeorpunish.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocPrizeorpunish::getUserAccount, dcaBDocPrizeorpunish.getUserAccount()).or()
        .like(DcaBDocPrizeorpunish::getUserAccountName, dcaBDocPrizeorpunish.getUserAccount()));

        }
        if (dcaBDocPrizeorpunish.getState()!=null) {
        queryWrapper.eq(DcaBDocPrizeorpunish::getState, dcaBDocPrizeorpunish.getState());
        }
       /** if (dcaBDocPrizeorpunish.getAuditState()!=null && (dcaBDocPrizeorpunish.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocPrizeorpunish::getAuditState, dcaBDocPrizeorpunish.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocPrizeorpunish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocPrizeorpunish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocPrizeorpunish::getCreateTime, dcaBDocPrizeorpunish.getCreateTimeFrom())
                                .le(DcaBDocPrizeorpunish::getCreateTime, dcaBDocPrizeorpunish.getCreateTimeTo());
                                }

        Page<DcaBDocPrizeorpunish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocPrizeorpunish> findDcaBDocPrizeorpunishList (QueryRequest request, DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
        try{
        Page<DcaBDocPrizeorpunish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocPrizeorpunish(page,dcaBDocPrizeorpunish);
        }catch(Exception e){
        log.error("获取何时受奖励处分失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocPrizeorpunish(DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
                dcaBDocPrizeorpunish.setId(UUID.randomUUID().toString());
        dcaBDocPrizeorpunish.setCreateTime(new Date());
        dcaBDocPrizeorpunish.setIsDeletemark(1);
        this.save(dcaBDocPrizeorpunish);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocPrizeorpunish(DcaBDocPrizeorpunish dcaBDocPrizeorpunish){
        dcaBDocPrizeorpunish.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocPrizeorpunish(dcaBDocPrizeorpunish);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocPrizeorpunishs(String[]Ids){
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