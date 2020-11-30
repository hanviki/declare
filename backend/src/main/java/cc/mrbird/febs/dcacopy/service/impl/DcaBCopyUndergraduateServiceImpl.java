package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduate;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyUndergraduateMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyUndergraduateService;
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
 * 本科教学情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyUndergraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyUndergraduateServiceImpl extends ServiceImpl<DcaBCopyUndergraduateMapper, DcaBCopyUndergraduate> implements IDcaBCopyUndergraduateService {


@Override
public IPage<DcaBCopyUndergraduate> findDcaBCopyUndergraduates(QueryRequest request, DcaBCopyUndergraduate dcaBCopyUndergraduate){
        try{
        LambdaQueryWrapper<DcaBCopyUndergraduate> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyUndergraduate::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyUndergraduate.getDcaYear())) {
                                queryWrapper.like(DcaBCopyUndergraduate::getDcaYear, dcaBCopyUndergraduate.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyUndergraduate.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyUndergraduate::getUserAccountName, dcaBCopyUndergraduate.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyUndergraduate.getUserAccount())) {
                                queryWrapper.like(DcaBCopyUndergraduate::getUserAccount, dcaBCopyUndergraduate.getUserAccount());
                                }

        Page<DcaBCopyUndergraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyUndergraduate> findDcaBCopyUndergraduateList (QueryRequest request, DcaBCopyUndergraduate dcaBCopyUndergraduate){
        try{
        Page<DcaBCopyUndergraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyUndergraduate(page,dcaBCopyUndergraduate);
        }catch(Exception e){
        log.error("获取本科教学情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyUndergraduate(DcaBCopyUndergraduate dcaBCopyUndergraduate){
                dcaBCopyUndergraduate.setId(UUID.randomUUID().toString());
        dcaBCopyUndergraduate.setCreateTime(new Date());
        dcaBCopyUndergraduate.setIsDeletemark(1);
        this.save(dcaBCopyUndergraduate);
        }

@Override
@Transactional
public void updateDcaBCopyUndergraduate(DcaBCopyUndergraduate dcaBCopyUndergraduate){
        dcaBCopyUndergraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyUndergraduate(dcaBCopyUndergraduate);
        }

@Override
@Transactional
public void deleteDcaBCopyUndergraduates(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyUndergraduate> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyUndergraduate> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyUndergraduate::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyUndergraduate::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }