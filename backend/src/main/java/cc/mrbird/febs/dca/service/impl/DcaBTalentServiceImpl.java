package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTalent;
import cc.mrbird.febs.dca.dao.DcaBTalentMapper;
import cc.mrbird.febs.dca.service.IDcaBTalentService;
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
 * 任现职以来完成研究生教学人才培养情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-15
 */
@Slf4j
@Service("IDcaBTalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTalentServiceImpl extends ServiceImpl<DcaBTalentMapper, DcaBTalent> implements IDcaBTalentService {


@Override
public IPage<DcaBTalent> findDcaBTalents(QueryRequest request, DcaBTalent dcaBTalent){
        try{
        LambdaQueryWrapper<DcaBTalent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTalent::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBTalent.getUserAccount())) {
                                queryWrapper.like(DcaBTalent::getUserAccount, dcaBTalent.getUserAccount());
                                }
                                if (dcaBTalent.getState()!=null) {
                                queryWrapper.eq(DcaBTalent::getState, dcaBTalent.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBTalent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTalent.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTalent::getCreateTime, dcaBTalent.getCreateTimeFrom())
                                .le(DcaBTalent::getCreateTime, dcaBTalent.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTalent.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTalent.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTalent::getModifyTime, dcaBTalent.getModifyTimeFrom())
                                .le(DcaBTalent::getModifyTime, dcaBTalent.getModifyTimeTo());
                                }

        Page<DcaBTalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTalent> findDcaBTalentList (QueryRequest request, DcaBTalent dcaBTalent){
        try{
        Page<DcaBTalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTalent(page,dcaBTalent);
        }catch(Exception e){
        log.error("获取任现职以来完成研究生教学人才培养情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTalent(DcaBTalent dcaBTalent){
                dcaBTalent.setId(UUID.randomUUID().toString());
        dcaBTalent.setCreateTime(new Date());
        dcaBTalent.setIsDeletemark(1);
        this.save(dcaBTalent);
        }

@Override
@Transactional
public void updateDcaBTalent(DcaBTalent dcaBTalent){
        dcaBTalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBTalent(dcaBTalent);
        }

@Override
@Transactional
public void deleteDcaBTalents(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }