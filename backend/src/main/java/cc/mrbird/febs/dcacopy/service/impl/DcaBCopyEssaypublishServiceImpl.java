package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyEssaypublish;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyEssaypublishMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyEssaypublishService;
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
 * 论文出版 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyEssaypublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyEssaypublishServiceImpl extends ServiceImpl<DcaBCopyEssaypublishMapper, DcaBCopyEssaypublish> implements IDcaBCopyEssaypublishService {


@Override
public IPage<DcaBCopyEssaypublish> findDcaBCopyEssaypublishs(QueryRequest request, DcaBCopyEssaypublish dcaBCopyEssaypublish){
        try{
        LambdaQueryWrapper<DcaBCopyEssaypublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyEssaypublish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyEssaypublish.getDcaYear())) {
                                queryWrapper.like(DcaBCopyEssaypublish::getDcaYear, dcaBCopyEssaypublish.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyEssaypublish.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyEssaypublish::getUserAccountName, dcaBCopyEssaypublish.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyEssaypublish.getUserAccount())) {
                                queryWrapper.like(DcaBCopyEssaypublish::getUserAccount, dcaBCopyEssaypublish.getUserAccount());
                                }

        Page<DcaBCopyEssaypublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyEssaypublish> findDcaBCopyEssaypublishList (QueryRequest request, DcaBCopyEssaypublish dcaBCopyEssaypublish){
        try{
        Page<DcaBCopyEssaypublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyEssaypublish(page,dcaBCopyEssaypublish);
        }catch(Exception e){
        log.error("获取论文出版失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyEssaypublish(DcaBCopyEssaypublish dcaBCopyEssaypublish){
                dcaBCopyEssaypublish.setId(UUID.randomUUID().toString());
        dcaBCopyEssaypublish.setCreateTime(new Date());
        dcaBCopyEssaypublish.setIsDeletemark(1);
        this.save(dcaBCopyEssaypublish);
        }

@Override
@Transactional
public void updateDcaBCopyEssaypublish(DcaBCopyEssaypublish dcaBCopyEssaypublish){
        dcaBCopyEssaypublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyEssaypublish(dcaBCopyEssaypublish);
        }

@Override
@Transactional
public void deleteDcaBCopyEssaypublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyEssaypublish> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyEssaypublish> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyEssaypublish::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyEssaypublish::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }