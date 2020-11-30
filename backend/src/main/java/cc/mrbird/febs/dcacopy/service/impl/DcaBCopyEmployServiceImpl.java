package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyEmploy;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyEmployMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyEmployService;
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
 * 任职培养 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyEmployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyEmployServiceImpl extends ServiceImpl<DcaBCopyEmployMapper, DcaBCopyEmploy> implements IDcaBCopyEmployService {


@Override
public IPage<DcaBCopyEmploy> findDcaBCopyEmploys(QueryRequest request, DcaBCopyEmploy dcaBCopyEmploy){
        try{
        LambdaQueryWrapper<DcaBCopyEmploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyEmploy::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyEmploy.getDcaYear())) {
                                queryWrapper.like(DcaBCopyEmploy::getDcaYear, dcaBCopyEmploy.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyEmploy.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyEmploy::getUserAccountName, dcaBCopyEmploy.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyEmploy.getUserAccount())) {
                                queryWrapper.like(DcaBCopyEmploy::getUserAccount, dcaBCopyEmploy.getUserAccount());
                                }

        Page<DcaBCopyEmploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyEmploy> findDcaBCopyEmployList (QueryRequest request, DcaBCopyEmploy dcaBCopyEmploy){
        try{
        Page<DcaBCopyEmploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyEmploy(page,dcaBCopyEmploy);
        }catch(Exception e){
        log.error("获取任职培养失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyEmploy(DcaBCopyEmploy dcaBCopyEmploy){
                dcaBCopyEmploy.setId(UUID.randomUUID().toString());
        dcaBCopyEmploy.setCreateTime(new Date());
        dcaBCopyEmploy.setIsDeletemark(1);
        this.save(dcaBCopyEmploy);
        }

@Override
@Transactional
public void updateDcaBCopyEmploy(DcaBCopyEmploy dcaBCopyEmploy){
        dcaBCopyEmploy.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyEmploy(dcaBCopyEmploy);
        }

@Override
@Transactional
public void deleteDcaBCopyEmploys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyEmploy> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyEmploy> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyEmploy::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyEmploy::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }