package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBEssaypublish;
import cc.mrbird.febs.dca.dao.DcaBEssaypublishMapper;
import cc.mrbird.febs.dca.service.IDcaBEssaypublishService;
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
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBEssaypublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBEssaypublishServiceImpl extends ServiceImpl<DcaBEssaypublishMapper, DcaBEssaypublish> implements IDcaBEssaypublishService {


@Override
public IPage<DcaBEssaypublish> findDcaBEssaypublishs(QueryRequest request, DcaBEssaypublish dcaBEssaypublish){
        try{
        LambdaQueryWrapper<DcaBEssaypublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBEssaypublish::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBEssaypublish.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBEssaypublish::getUserAccount, dcaBEssaypublish.getUserAccount()).or().like(DcaBEssaypublish::getUserAccountName, dcaBEssaypublish.getUserAccount()));
            }
                                if (dcaBEssaypublish.getState()!=null) {
                                queryWrapper.eq(DcaBEssaypublish::getState, dcaBEssaypublish.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBEssaypublish.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBEssaypublish.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBEssaypublish::getCreateTime, dcaBEssaypublish.getCreateTimeFrom())
                                .le(DcaBEssaypublish::getCreateTime, dcaBEssaypublish.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBEssaypublish.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBEssaypublish.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBEssaypublish::getModifyTime, dcaBEssaypublish.getModifyTimeFrom())
                                .le(DcaBEssaypublish::getModifyTime, dcaBEssaypublish.getModifyTimeTo());
                                }

        Page<DcaBEssaypublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBEssaypublish> findDcaBEssaypublishList (QueryRequest request, DcaBEssaypublish dcaBEssaypublish){
        try{
        Page<DcaBEssaypublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBEssaypublish(page,dcaBEssaypublish);
        }catch(Exception e){
        log.error("获取论文出版失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBEssaypublish(DcaBEssaypublish dcaBEssaypublish){
                dcaBEssaypublish.setId(UUID.randomUUID().toString());
        dcaBEssaypublish.setCreateTime(new Date());
        dcaBEssaypublish.setIsDeletemark(1);
        this.save(dcaBEssaypublish);
        }

@Override
@Transactional
public void updateDcaBEssaypublish(DcaBEssaypublish dcaBEssaypublish){
        dcaBEssaypublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBEssaypublish(dcaBEssaypublish);
        }

@Override
@Transactional
public void deleteDcaBEssaypublishs(String[]Ids){
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