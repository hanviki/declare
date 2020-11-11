package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBPaperspublish;
import cc.mrbird.febs.dca.dao.DcaBPaperspublishMapper;
import cc.mrbird.febs.dca.service.IDcaBPaperspublishService;
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
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBPaperspublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBPaperspublishServiceImpl extends ServiceImpl<DcaBPaperspublishMapper, DcaBPaperspublish> implements IDcaBPaperspublishService {


@Override
public IPage<DcaBPaperspublish> findDcaBPaperspublishs(QueryRequest request, DcaBPaperspublish dcaBPaperspublish){
        try{
        LambdaQueryWrapper<DcaBPaperspublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBPaperspublish::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBPaperspublish.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBPaperspublish::getUserAccount, dcaBPaperspublish.getUserAccount()).or()
                        .like(DcaBPaperspublish::getUserAccountName, dcaBPaperspublish.getUserAccount()));

            }
                                if (dcaBPaperspublish.getState()!=null) {
                                queryWrapper.eq(DcaBPaperspublish::getState, dcaBPaperspublish.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBPaperspublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBPaperspublish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBPaperspublish::getCreateTime, dcaBPaperspublish.getCreateTimeFrom())
                                .le(DcaBPaperspublish::getCreateTime, dcaBPaperspublish.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBPaperspublish.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBPaperspublish.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBPaperspublish::getModifyTime, dcaBPaperspublish.getModifyTimeFrom())
                                .le(DcaBPaperspublish::getModifyTime, dcaBPaperspublish.getModifyTimeTo());
                                }

        Page<DcaBPaperspublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBPaperspublish> findDcaBPaperspublishList (QueryRequest request, DcaBPaperspublish dcaBPaperspublish){
        try{
        Page<DcaBPaperspublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBPaperspublish(page,dcaBPaperspublish);
        }catch(Exception e){
        log.error("获取教学论文出版教材失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBPaperspublish(DcaBPaperspublish dcaBPaperspublish){
                dcaBPaperspublish.setId(UUID.randomUUID().toString());
        dcaBPaperspublish.setCreateTime(new Date());
        dcaBPaperspublish.setIsDeletemark(1);
        this.save(dcaBPaperspublish);
        }

@Override
@Transactional
public void updateDcaBPaperspublish(DcaBPaperspublish dcaBPaperspublish){
        dcaBPaperspublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBPaperspublish(dcaBPaperspublish);
        }

@Override
@Transactional
public void deleteDcaBPaperspublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }