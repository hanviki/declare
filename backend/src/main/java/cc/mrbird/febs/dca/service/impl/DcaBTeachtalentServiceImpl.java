package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTeachtalent;
import cc.mrbird.febs.dca.dao.DcaBTeachtalentMapper;
import cc.mrbird.febs.dca.service.IDcaBTeachtalentService;
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
 * @since 2020-09-27
 */
@Slf4j
@Service("IDcaBTeachtalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTeachtalentServiceImpl extends ServiceImpl<DcaBTeachtalentMapper, DcaBTeachtalent> implements IDcaBTeachtalentService {


@Override
public IPage<DcaBTeachtalent> findDcaBTeachtalents(QueryRequest request, DcaBTeachtalent dcaBTeachtalent){
        try{
        LambdaQueryWrapper<DcaBTeachtalent> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTeachtalent::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBTeachtalent.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBTeachtalent::getUserAccount, dcaBTeachtalent.getUserAccount()).or()
                        .like(DcaBTeachtalent::getUserAccountName, dcaBTeachtalent.getUserAccount()));

            }
                                if (dcaBTeachtalent.getState()!=null) {
                                queryWrapper.eq(DcaBTeachtalent::getState, dcaBTeachtalent.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBTeachtalent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTeachtalent.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeachtalent::getCreateTime, dcaBTeachtalent.getCreateTimeFrom())
                                .le(DcaBTeachtalent::getCreateTime, dcaBTeachtalent.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTeachtalent.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTeachtalent.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeachtalent::getModifyTime, dcaBTeachtalent.getModifyTimeFrom())
                                .le(DcaBTeachtalent::getModifyTime, dcaBTeachtalent.getModifyTimeTo());
                                }

        Page<DcaBTeachtalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTeachtalent> findDcaBTeachtalentList (QueryRequest request, DcaBTeachtalent dcaBTeachtalent){
        try{
        Page<DcaBTeachtalent> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTeachtalent(page,dcaBTeachtalent);
        }catch(Exception e){
        log.error("获取任现职以来完成教学、人才培养情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent){
                dcaBTeachtalent.setId(UUID.randomUUID().toString());
        dcaBTeachtalent.setCreateTime(new Date());
        dcaBTeachtalent.setIsDeletemark(1);
        this.save(dcaBTeachtalent);
        }

@Override
@Transactional
public void updateDcaBTeachtalent(DcaBTeachtalent dcaBTeachtalent){
        dcaBTeachtalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBTeachtalent(dcaBTeachtalent);
        }

@Override
@Transactional
public void deleteDcaBTeachtalents(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }