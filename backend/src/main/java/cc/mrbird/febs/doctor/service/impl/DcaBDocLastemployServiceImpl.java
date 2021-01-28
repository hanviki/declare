package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocLastemploy;
import cc.mrbird.febs.doctor.dao.DcaBDocLastemployMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocLastemployService;
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
 * 完成上一聘期 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocLastemployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocLastemployServiceImpl extends ServiceImpl<DcaBDocLastemployMapper, DcaBDocLastemploy> implements IDcaBDocLastemployService {


@Override
@DS("slave")
public IPage<DcaBDocLastemploy> findDcaBDocLastemploys(QueryRequest request, DcaBDocLastemploy dcaBDocLastemploy){
        try{
        LambdaQueryWrapper<DcaBDocLastemploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocLastemploy::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocLastemploy.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocLastemploy::getUserAccount, dcaBDocLastemploy.getUserAccount()).or()
        .like(DcaBDocLastemploy::getUserAccountName, dcaBDocLastemploy.getUserAccount()));

        }
        if (dcaBDocLastemploy.getState()!=null) {
        queryWrapper.eq(DcaBDocLastemploy::getState, dcaBDocLastemploy.getState());
        }
       /** if (dcaBDocLastemploy.getAuditState()!=null && (dcaBDocLastemploy.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocLastemploy::getAuditState, dcaBDocLastemploy.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocLastemploy.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocLastemploy.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocLastemploy::getCreateTime, dcaBDocLastemploy.getCreateTimeFrom())
                                .le(DcaBDocLastemploy::getCreateTime, dcaBDocLastemploy.getCreateTimeTo());
                                }

        Page<DcaBDocLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocLastemploy> findDcaBDocLastemployList (QueryRequest request, DcaBDocLastemploy dcaBDocLastemploy){
        try{
        Page<DcaBDocLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocLastemploy(page,dcaBDocLastemploy);
        }catch(Exception e){
        log.error("获取完成上一聘期失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocLastemploy(DcaBDocLastemploy dcaBDocLastemploy){
                dcaBDocLastemploy.setId(UUID.randomUUID().toString());
        dcaBDocLastemploy.setCreateTime(new Date());
        dcaBDocLastemploy.setIsDeletemark(1);
        this.save(dcaBDocLastemploy);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocLastemploy(DcaBDocLastemploy dcaBDocLastemploy){
        dcaBDocLastemploy.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocLastemploy(dcaBDocLastemploy);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocLastemploys(String[]Ids){
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