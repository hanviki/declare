package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocEducationexperice;
import cc.mrbird.febs.doctor.dao.DcaBDocEducationexpericeMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocEducationexpericeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.dynamic.datasource.annotation.DS;
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
 * 学习工作经历 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocEducationexpericeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocEducationexpericeServiceImpl extends ServiceImpl<DcaBDocEducationexpericeMapper, DcaBDocEducationexperice> implements IDcaBDocEducationexpericeService {


@Override
@DS("slave")
public IPage<DcaBDocEducationexperice> findDcaBDocEducationexperices(QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice){
        try{
        LambdaQueryWrapper<DcaBDocEducationexperice> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocEducationexperice::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocEducationexperice.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocEducationexperice::getUserAccount, dcaBDocEducationexperice.getUserAccount()).or()
        .like(DcaBDocEducationexperice::getUserAccountName, dcaBDocEducationexperice.getUserAccount()));

        }
        if (dcaBDocEducationexperice.getState()!=null) {
        queryWrapper.eq(DcaBDocEducationexperice::getState, dcaBDocEducationexperice.getState());
        }
       /** if (dcaBDocEducationexperice.getAuditState()!=null && (dcaBDocEducationexperice.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocEducationexperice::getAuditState, dcaBDocEducationexperice.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocEducationexperice.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocEducationexperice.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocEducationexperice::getCreateTime, dcaBDocEducationexperice.getCreateTimeFrom())
                                .le(DcaBDocEducationexperice::getCreateTime, dcaBDocEducationexperice.getCreateTimeTo());
                                }

        Page<DcaBDocEducationexperice> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocEducationexperice> findDcaBDocEducationexpericeList (QueryRequest request, DcaBDocEducationexperice dcaBDocEducationexperice){
        try{
        Page<DcaBDocEducationexperice> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocEducationexperice(page,dcaBDocEducationexperice);
        }catch(Exception e){
        log.error("获取学习工作经历失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocEducationexperice(DcaBDocEducationexperice dcaBDocEducationexperice){
                dcaBDocEducationexperice.setId(UUID.randomUUID().toString());
        dcaBDocEducationexperice.setCreateTime(new Date());
        dcaBDocEducationexperice.setIsDeletemark(1);
        this.save(dcaBDocEducationexperice);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocEducationexperice(DcaBDocEducationexperice dcaBDocEducationexperice){
        dcaBDocEducationexperice.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocEducationexperice(dcaBDocEducationexperice);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocEducationexperices(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
@DS("slave")
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }