package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduate;
import cc.mrbird.febs.doctor.dao.DcaBDocUndergraduateMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocUndergraduateService;
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
 * 本科教学情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocUndergraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocUndergraduateServiceImpl extends ServiceImpl<DcaBDocUndergraduateMapper, DcaBDocUndergraduate> implements IDcaBDocUndergraduateService {


@Override
@DS("slave")
public IPage<DcaBDocUndergraduate> findDcaBDocUndergraduates(QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate){
        try{
        LambdaQueryWrapper<DcaBDocUndergraduate> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocUndergraduate::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocUndergraduate.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocUndergraduate::getUserAccount, dcaBDocUndergraduate.getUserAccount()).or()
        .like(DcaBDocUndergraduate::getUserAccountName, dcaBDocUndergraduate.getUserAccount()));

        }
        if (dcaBDocUndergraduate.getState()!=null) {
        queryWrapper.eq(DcaBDocUndergraduate::getState, dcaBDocUndergraduate.getState());
        }
       /** if (dcaBDocUndergraduate.getAuditState()!=null && (dcaBDocUndergraduate.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocUndergraduate::getAuditState, dcaBDocUndergraduate.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocUndergraduate.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocUndergraduate.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocUndergraduate::getCreateTime, dcaBDocUndergraduate.getCreateTimeFrom())
                                .le(DcaBDocUndergraduate::getCreateTime, dcaBDocUndergraduate.getCreateTimeTo());
                                }

        Page<DcaBDocUndergraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocUndergraduate> findDcaBDocUndergraduateList (QueryRequest request, DcaBDocUndergraduate dcaBDocUndergraduate){
        try{
        Page<DcaBDocUndergraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocUndergraduate(page,dcaBDocUndergraduate);
        }catch(Exception e){
        log.error("获取本科教学情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocUndergraduate(DcaBDocUndergraduate dcaBDocUndergraduate){
                dcaBDocUndergraduate.setId(UUID.randomUUID().toString());
        dcaBDocUndergraduate.setCreateTime(new Date());
        dcaBDocUndergraduate.setIsDeletemark(1);
        this.save(dcaBDocUndergraduate);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocUndergraduate(DcaBDocUndergraduate dcaBDocUndergraduate){
        dcaBDocUndergraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocUndergraduate(dcaBDocUndergraduate);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocUndergraduates(String[]Ids){
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