package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.service.IDcaBSciencepublishService;
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
 * 科研论文 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-15
 */
@Slf4j
@Service("IDcaBSciencepublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSciencepublishServiceImpl extends ServiceImpl<DcaBSciencepublishMapper, DcaBSciencepublish> implements IDcaBSciencepublishService {


@Override
public IPage<DcaBSciencepublish> findDcaBSciencepublishs(QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        try{
        LambdaQueryWrapper<DcaBSciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBSciencepublish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBSciencepublish.getUserAccount())) {
                                queryWrapper.like(DcaBSciencepublish::getUserAccount, dcaBSciencepublish.getUserAccount());
                                }
                                if (dcaBSciencepublish.getState()!=null) {
                                queryWrapper.eq(DcaBSciencepublish::getState, dcaBSciencepublish.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBSciencepublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciencepublish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBSciencepublish::getCreateTime, dcaBSciencepublish.getCreateTimeFrom())
                                .le(DcaBSciencepublish::getCreateTime, dcaBSciencepublish.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBSciencepublish.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBSciencepublish.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBSciencepublish::getModifyTime, dcaBSciencepublish.getModifyTimeFrom())
                                .le(DcaBSciencepublish::getModifyTime, dcaBSciencepublish.getModifyTimeTo());
                                }

        Page<DcaBSciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBSciencepublish> findDcaBSciencepublishList (QueryRequest request, DcaBSciencepublish dcaBSciencepublish){
        try{
        Page<DcaBSciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBSciencepublish(page,dcaBSciencepublish);
        }catch(Exception e){
        log.error("获取科研论文失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish){
                dcaBSciencepublish.setId(UUID.randomUUID().toString());
        dcaBSciencepublish.setCreateTime(new Date());
        dcaBSciencepublish.setIsDeletemark(1);
        this.save(dcaBSciencepublish);
        }

@Override
@Transactional
public void updateDcaBSciencepublish(DcaBSciencepublish dcaBSciencepublish){
        dcaBSciencepublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBSciencepublish(dcaBSciencepublish);
        }

@Override
@Transactional
public void deleteDcaBSciencepublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }