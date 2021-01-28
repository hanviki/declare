package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocTurtor;
import cc.mrbird.febs.doctor.dao.DcaBDocTurtorMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocTurtorService;
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
 * 担任辅导员 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocTurtorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocTurtorServiceImpl extends ServiceImpl<DcaBDocTurtorMapper, DcaBDocTurtor> implements IDcaBDocTurtorService {


@Override
@DS("slave")
public IPage<DcaBDocTurtor> findDcaBDocTurtors(QueryRequest request, DcaBDocTurtor dcaBDocTurtor){
        try{
        LambdaQueryWrapper<DcaBDocTurtor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocTurtor::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocTurtor.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocTurtor::getUserAccount, dcaBDocTurtor.getUserAccount()).or()
        .like(DcaBDocTurtor::getUserAccountName, dcaBDocTurtor.getUserAccount()));

        }
        if (dcaBDocTurtor.getState()!=null) {
        queryWrapper.eq(DcaBDocTurtor::getState, dcaBDocTurtor.getState());
        }
       /** if (dcaBDocTurtor.getAuditState()!=null && (dcaBDocTurtor.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocTurtor::getAuditState, dcaBDocTurtor.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocTurtor.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocTurtor.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocTurtor::getCreateTime, dcaBDocTurtor.getCreateTimeFrom())
                                .le(DcaBDocTurtor::getCreateTime, dcaBDocTurtor.getCreateTimeTo());
                                }

        Page<DcaBDocTurtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocTurtor> findDcaBDocTurtorList (QueryRequest request, DcaBDocTurtor dcaBDocTurtor){
        try{
        Page<DcaBDocTurtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocTurtor(page,dcaBDocTurtor);
        }catch(Exception e){
        log.error("获取担任辅导员失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocTurtor(DcaBDocTurtor dcaBDocTurtor){
                dcaBDocTurtor.setId(UUID.randomUUID().toString());
        dcaBDocTurtor.setCreateTime(new Date());
        dcaBDocTurtor.setIsDeletemark(1);
        this.save(dcaBDocTurtor);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocTurtor(DcaBDocTurtor dcaBDocTurtor){
        dcaBDocTurtor.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocTurtor(dcaBDocTurtor);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocTurtors(String[]Ids){
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