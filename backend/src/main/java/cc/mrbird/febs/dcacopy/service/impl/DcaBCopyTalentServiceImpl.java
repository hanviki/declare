package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTalent;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyTalentMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTalentService;
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
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyTalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyTalentServiceImpl extends ServiceImpl<DcaBCopyTalentMapper, DcaBCopyTalent> implements IDcaBCopyTalentService {


@Override
public IPage<DcaBCopyTalent> findDcaBCopyTalents(QueryRequest request, DcaBCopyTalent dcaBCopyTalent){
        try{
        LambdaQueryWrapper<DcaBCopyTalent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyTalent::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyTalent.getDcaYear())) {
                                queryWrapper.like(DcaBCopyTalent::getDcaYear, dcaBCopyTalent.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTalent.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyTalent::getUserAccountName, dcaBCopyTalent.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTalent.getUserAccount())) {
                                queryWrapper.like(DcaBCopyTalent::getUserAccount, dcaBCopyTalent.getUserAccount());
                                }

        Page<DcaBCopyTalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyTalent> findDcaBCopyTalentList (QueryRequest request, DcaBCopyTalent dcaBCopyTalent){
        try{
        Page<DcaBCopyTalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyTalent(page,dcaBCopyTalent);
        }catch(Exception e){
        log.error("获取任现职以来完成研究生教学人才培养情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyTalent(DcaBCopyTalent dcaBCopyTalent){
                dcaBCopyTalent.setId(UUID.randomUUID().toString());
        dcaBCopyTalent.setCreateTime(new Date());
        dcaBCopyTalent.setIsDeletemark(1);
        this.save(dcaBCopyTalent);
        }

@Override
@Transactional
public void updateDcaBCopyTalent(DcaBCopyTalent dcaBCopyTalent){
        dcaBCopyTalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyTalent(dcaBCopyTalent);
        }

@Override
@Transactional
public void deleteDcaBCopyTalents(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyTalent> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyTalent> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyTalent::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyTalent::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }