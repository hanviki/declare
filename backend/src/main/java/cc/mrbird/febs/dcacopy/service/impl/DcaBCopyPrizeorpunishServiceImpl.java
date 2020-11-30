package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPrizeorpunish;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyPrizeorpunishMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyPrizeorpunishService;
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
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyPrizeorpunishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyPrizeorpunishServiceImpl extends ServiceImpl<DcaBCopyPrizeorpunishMapper, DcaBCopyPrizeorpunish> implements IDcaBCopyPrizeorpunishService {


@Override
public IPage<DcaBCopyPrizeorpunish> findDcaBCopyPrizeorpunishs(QueryRequest request, DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish){
        try{
        LambdaQueryWrapper<DcaBCopyPrizeorpunish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyPrizeorpunish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyPrizeorpunish.getDcaYear())) {
                                queryWrapper.like(DcaBCopyPrizeorpunish::getDcaYear, dcaBCopyPrizeorpunish.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPrizeorpunish.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyPrizeorpunish::getUserAccountName, dcaBCopyPrizeorpunish.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPrizeorpunish.getUserAccount())) {
                                queryWrapper.like(DcaBCopyPrizeorpunish::getUserAccount, dcaBCopyPrizeorpunish.getUserAccount());
                                }

        Page<DcaBCopyPrizeorpunish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyPrizeorpunish> findDcaBCopyPrizeorpunishList (QueryRequest request, DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish){
        try{
        Page<DcaBCopyPrizeorpunish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyPrizeorpunish(page,dcaBCopyPrizeorpunish);
        }catch(Exception e){
        log.error("获取何时受奖励处分失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyPrizeorpunish(DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish){
                dcaBCopyPrizeorpunish.setId(UUID.randomUUID().toString());
        dcaBCopyPrizeorpunish.setCreateTime(new Date());
        dcaBCopyPrizeorpunish.setIsDeletemark(1);
        this.save(dcaBCopyPrizeorpunish);
        }

@Override
@Transactional
public void updateDcaBCopyPrizeorpunish(DcaBCopyPrizeorpunish dcaBCopyPrizeorpunish){
        dcaBCopyPrizeorpunish.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyPrizeorpunish(dcaBCopyPrizeorpunish);
        }

@Override
@Transactional
public void deleteDcaBCopyPrizeorpunishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyPrizeorpunish> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyPrizeorpunish> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyPrizeorpunish::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyPrizeorpunish::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }