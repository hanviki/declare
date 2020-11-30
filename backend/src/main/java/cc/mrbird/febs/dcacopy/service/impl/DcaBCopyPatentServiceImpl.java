package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPatent;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyPatentMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyPatentService;
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
 * 任现职以来申请专利情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyPatentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyPatentServiceImpl extends ServiceImpl<DcaBCopyPatentMapper, DcaBCopyPatent> implements IDcaBCopyPatentService {


@Override
public IPage<DcaBCopyPatent> findDcaBCopyPatents(QueryRequest request, DcaBCopyPatent dcaBCopyPatent){
        try{
        LambdaQueryWrapper<DcaBCopyPatent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyPatent::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyPatent.getDcaYear())) {
                                queryWrapper.like(DcaBCopyPatent::getDcaYear, dcaBCopyPatent.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPatent.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyPatent::getUserAccountName, dcaBCopyPatent.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPatent.getUserAccount())) {
                                queryWrapper.like(DcaBCopyPatent::getUserAccount, dcaBCopyPatent.getUserAccount());
                                }

        Page<DcaBCopyPatent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyPatent> findDcaBCopyPatentList (QueryRequest request, DcaBCopyPatent dcaBCopyPatent){
        try{
        Page<DcaBCopyPatent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyPatent(page,dcaBCopyPatent);
        }catch(Exception e){
        log.error("获取任现职以来申请专利情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyPatent(DcaBCopyPatent dcaBCopyPatent){
                dcaBCopyPatent.setId(UUID.randomUUID().toString());
        dcaBCopyPatent.setCreateTime(new Date());
        dcaBCopyPatent.setIsDeletemark(1);
        this.save(dcaBCopyPatent);
        }

@Override
@Transactional
public void updateDcaBCopyPatent(DcaBCopyPatent dcaBCopyPatent){
        dcaBCopyPatent.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyPatent(dcaBCopyPatent);
        }

@Override
@Transactional
public void deleteDcaBCopyPatents(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyPatent> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyPatent> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyPatent::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyPatent::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }