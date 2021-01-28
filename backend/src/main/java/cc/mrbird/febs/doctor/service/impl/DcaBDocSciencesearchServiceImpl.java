package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocSciencesearch;
import cc.mrbird.febs.doctor.dao.DcaBDocSciencesearchMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocSciencesearchService;
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
 * 科研项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocSciencesearchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocSciencesearchServiceImpl extends ServiceImpl<DcaBDocSciencesearchMapper, DcaBDocSciencesearch> implements IDcaBDocSciencesearchService {


@Override
@DS("slave")
public IPage<DcaBDocSciencesearch> findDcaBDocSciencesearchs(QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch){
        try{
        LambdaQueryWrapper<DcaBDocSciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocSciencesearch::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocSciencesearch.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocSciencesearch::getUserAccount, dcaBDocSciencesearch.getUserAccount()).or()
        .like(DcaBDocSciencesearch::getUserAccountName, dcaBDocSciencesearch.getUserAccount()));

        }
        if (dcaBDocSciencesearch.getState()!=null) {
        queryWrapper.eq(DcaBDocSciencesearch::getState, dcaBDocSciencesearch.getState());
        }
       /** if (dcaBDocSciencesearch.getAuditState()!=null && (dcaBDocSciencesearch.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocSciencesearch::getAuditState, dcaBDocSciencesearch.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocSciencesearch.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocSciencesearch.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocSciencesearch::getCreateTime, dcaBDocSciencesearch.getCreateTimeFrom())
                                .le(DcaBDocSciencesearch::getCreateTime, dcaBDocSciencesearch.getCreateTimeTo());
                                }

        Page<DcaBDocSciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocSciencesearch> findDcaBDocSciencesearchList (QueryRequest request, DcaBDocSciencesearch dcaBDocSciencesearch){
        try{
        Page<DcaBDocSciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocSciencesearch(page,dcaBDocSciencesearch);
        }catch(Exception e){
        log.error("获取科研项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocSciencesearch(DcaBDocSciencesearch dcaBDocSciencesearch){
                dcaBDocSciencesearch.setId(UUID.randomUUID().toString());
        dcaBDocSciencesearch.setCreateTime(new Date());
        dcaBDocSciencesearch.setIsDeletemark(1);
        this.save(dcaBDocSciencesearch);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocSciencesearch(DcaBDocSciencesearch dcaBDocSciencesearch){
        dcaBDocSciencesearch.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocSciencesearch(dcaBDocSciencesearch);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocSciencesearchs(String[]Ids){
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