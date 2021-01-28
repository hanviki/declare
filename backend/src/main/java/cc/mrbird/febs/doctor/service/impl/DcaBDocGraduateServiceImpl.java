package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocGraduate;
import cc.mrbird.febs.doctor.dao.DcaBDocGraduateMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocGraduateService;
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
 * 研究生情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocGraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocGraduateServiceImpl extends ServiceImpl<DcaBDocGraduateMapper, DcaBDocGraduate> implements IDcaBDocGraduateService {


@Override
@DS("slave")
public IPage<DcaBDocGraduate> findDcaBDocGraduates(QueryRequest request, DcaBDocGraduate dcaBDocGraduate){
        try{
        LambdaQueryWrapper<DcaBDocGraduate> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocGraduate::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocGraduate.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocGraduate::getUserAccount, dcaBDocGraduate.getUserAccount()).or()
        .like(DcaBDocGraduate::getUserAccountName, dcaBDocGraduate.getUserAccount()));

        }
        if (dcaBDocGraduate.getState()!=null) {
        queryWrapper.eq(DcaBDocGraduate::getState, dcaBDocGraduate.getState());
        }
       /** if (dcaBDocGraduate.getAuditState()!=null && (dcaBDocGraduate.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocGraduate::getAuditState, dcaBDocGraduate.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocGraduate.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocGraduate.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocGraduate::getCreateTime, dcaBDocGraduate.getCreateTimeFrom())
                                .le(DcaBDocGraduate::getCreateTime, dcaBDocGraduate.getCreateTimeTo());
                                }

        Page<DcaBDocGraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocGraduate> findDcaBDocGraduateList (QueryRequest request, DcaBDocGraduate dcaBDocGraduate){
        try{
        Page<DcaBDocGraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocGraduate(page,dcaBDocGraduate);
        }catch(Exception e){
        log.error("获取研究生情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocGraduate(DcaBDocGraduate dcaBDocGraduate){
                dcaBDocGraduate.setId(UUID.randomUUID().toString());
        dcaBDocGraduate.setCreateTime(new Date());
        dcaBDocGraduate.setIsDeletemark(1);
        this.save(dcaBDocGraduate);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocGraduate(DcaBDocGraduate dcaBDocGraduate){
        dcaBDocGraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocGraduate(dcaBDocGraduate);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocGraduates(String[]Ids){
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