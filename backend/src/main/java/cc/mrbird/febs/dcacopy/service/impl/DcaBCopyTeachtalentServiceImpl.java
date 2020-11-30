package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeachtalent;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyTeachtalentMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeachtalentService;
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
 * 任现职以来完成教学、人才培养情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyTeachtalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyTeachtalentServiceImpl extends ServiceImpl<DcaBCopyTeachtalentMapper, DcaBCopyTeachtalent> implements IDcaBCopyTeachtalentService {


@Override
public IPage<DcaBCopyTeachtalent> findDcaBCopyTeachtalents(QueryRequest request, DcaBCopyTeachtalent dcaBCopyTeachtalent){
        try{
        LambdaQueryWrapper<DcaBCopyTeachtalent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyTeachtalent::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyTeachtalent.getDcaYear())) {
                                queryWrapper.like(DcaBCopyTeachtalent::getDcaYear, dcaBCopyTeachtalent.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTeachtalent.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyTeachtalent::getUserAccountName, dcaBCopyTeachtalent.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTeachtalent.getUserAccount())) {
                                queryWrapper.like(DcaBCopyTeachtalent::getUserAccount, dcaBCopyTeachtalent.getUserAccount());
                                }

        Page<DcaBCopyTeachtalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyTeachtalent> findDcaBCopyTeachtalentList (QueryRequest request, DcaBCopyTeachtalent dcaBCopyTeachtalent){
        try{
        Page<DcaBCopyTeachtalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyTeachtalent(page,dcaBCopyTeachtalent);
        }catch(Exception e){
        log.error("获取任现职以来完成教学、人才培养情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyTeachtalent(DcaBCopyTeachtalent dcaBCopyTeachtalent){
                dcaBCopyTeachtalent.setId(UUID.randomUUID().toString());
        dcaBCopyTeachtalent.setCreateTime(new Date());
        dcaBCopyTeachtalent.setIsDeletemark(1);
        this.save(dcaBCopyTeachtalent);
        }

@Override
@Transactional
public void updateDcaBCopyTeachtalent(DcaBCopyTeachtalent dcaBCopyTeachtalent){
        dcaBCopyTeachtalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyTeachtalent(dcaBCopyTeachtalent);
        }

@Override
@Transactional
public void deleteDcaBCopyTeachtalents(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyTeachtalent> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyTeachtalent> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyTeachtalent::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyTeachtalent::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }