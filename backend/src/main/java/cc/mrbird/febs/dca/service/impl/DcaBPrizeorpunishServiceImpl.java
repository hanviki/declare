package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBPrizeorpunish;
import cc.mrbird.febs.dca.dao.DcaBPrizeorpunishMapper;
import cc.mrbird.febs.dca.service.IDcaBPrizeorpunishService;
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
 * 何时受奖励处分 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBPrizeorpunishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBPrizeorpunishServiceImpl extends ServiceImpl<DcaBPrizeorpunishMapper, DcaBPrizeorpunish> implements IDcaBPrizeorpunishService {


@Override
public IPage<DcaBPrizeorpunish> findDcaBPrizeorpunishs(QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish){
        try{
        LambdaQueryWrapper<DcaBPrizeorpunish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBPrizeorpunish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBPrizeorpunish.getUserAccount())) {
                                queryWrapper.like(DcaBPrizeorpunish::getUserAccount, dcaBPrizeorpunish.getUserAccount());
                                }
                                if (dcaBPrizeorpunish.getState()!=null) {
                                queryWrapper.eq(DcaBPrizeorpunish::getState, dcaBPrizeorpunish.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBPrizeorpunish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBPrizeorpunish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBPrizeorpunish::getCreateTime, dcaBPrizeorpunish.getCreateTimeFrom())
                                .le(DcaBPrizeorpunish::getCreateTime, dcaBPrizeorpunish.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBPrizeorpunish.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBPrizeorpunish.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBPrizeorpunish::getModifyTime, dcaBPrizeorpunish.getModifyTimeFrom())
                                .le(DcaBPrizeorpunish::getModifyTime, dcaBPrizeorpunish.getModifyTimeTo());
                                }

        Page<DcaBPrizeorpunish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBPrizeorpunish> findDcaBPrizeorpunishList (QueryRequest request, DcaBPrizeorpunish dcaBPrizeorpunish){
        try{
        Page<DcaBPrizeorpunish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBPrizeorpunish(page,dcaBPrizeorpunish);
        }catch(Exception e){
        log.error("获取何时受奖励处分失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBPrizeorpunish(DcaBPrizeorpunish dcaBPrizeorpunish){
                dcaBPrizeorpunish.setId(UUID.randomUUID().toString());
        dcaBPrizeorpunish.setCreateTime(new Date());
        dcaBPrizeorpunish.setIsDeletemark(1);
        this.save(dcaBPrizeorpunish);
        }

@Override
@Transactional
public void updateDcaBPrizeorpunish(DcaBPrizeorpunish dcaBPrizeorpunish){
        dcaBPrizeorpunish.setModifyTime(new Date());
        this.baseMapper.updateDcaBPrizeorpunish(dcaBPrizeorpunish);
        }

@Override
@Transactional
public void deleteDcaBPrizeorpunishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }