package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPaperspublish;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyPaperspublishMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyPaperspublishService;
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
 * 教学论文出版教材 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyPaperspublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyPaperspublishServiceImpl extends ServiceImpl<DcaBCopyPaperspublishMapper, DcaBCopyPaperspublish> implements IDcaBCopyPaperspublishService {


@Override
public IPage<DcaBCopyPaperspublish> findDcaBCopyPaperspublishs(QueryRequest request, DcaBCopyPaperspublish dcaBCopyPaperspublish){
        try{
        LambdaQueryWrapper<DcaBCopyPaperspublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyPaperspublish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyPaperspublish.getDcaYear())) {
                                queryWrapper.like(DcaBCopyPaperspublish::getDcaYear, dcaBCopyPaperspublish.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPaperspublish.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyPaperspublish::getUserAccountName, dcaBCopyPaperspublish.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPaperspublish.getUserAccount())) {
                                queryWrapper.like(DcaBCopyPaperspublish::getUserAccount, dcaBCopyPaperspublish.getUserAccount());
                                }

        Page<DcaBCopyPaperspublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyPaperspublish> findDcaBCopyPaperspublishList (QueryRequest request, DcaBCopyPaperspublish dcaBCopyPaperspublish){
        try{
        Page<DcaBCopyPaperspublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyPaperspublish(page,dcaBCopyPaperspublish);
        }catch(Exception e){
        log.error("获取教学论文出版教材失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyPaperspublish(DcaBCopyPaperspublish dcaBCopyPaperspublish){
                dcaBCopyPaperspublish.setId(UUID.randomUUID().toString());
        dcaBCopyPaperspublish.setCreateTime(new Date());
        dcaBCopyPaperspublish.setIsDeletemark(1);
        this.save(dcaBCopyPaperspublish);
        }

@Override
@Transactional
public void updateDcaBCopyPaperspublish(DcaBCopyPaperspublish dcaBCopyPaperspublish){
        dcaBCopyPaperspublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyPaperspublish(dcaBCopyPaperspublish);
        }

@Override
@Transactional
public void deleteDcaBCopyPaperspublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyPaperspublish> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyPaperspublish> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyPaperspublish::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyPaperspublish::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }