package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocParttimejob;
import cc.mrbird.febs.doctor.dao.DcaBDocParttimejobMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocParttimejobService;
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
 * 社会兼职表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocParttimejobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocParttimejobServiceImpl extends ServiceImpl<DcaBDocParttimejobMapper, DcaBDocParttimejob> implements IDcaBDocParttimejobService {


@Override
@DS("slave")
public IPage<DcaBDocParttimejob> findDcaBDocParttimejobs(QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob){
        try{
        LambdaQueryWrapper<DcaBDocParttimejob> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocParttimejob::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocParttimejob.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocParttimejob::getUserAccount, dcaBDocParttimejob.getUserAccount()).or()
        .like(DcaBDocParttimejob::getUserAccountName, dcaBDocParttimejob.getUserAccount()));

        }
        if (dcaBDocParttimejob.getState()!=null) {
        queryWrapper.eq(DcaBDocParttimejob::getState, dcaBDocParttimejob.getState());
        }
       /** if (dcaBDocParttimejob.getAuditState()!=null && (dcaBDocParttimejob.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocParttimejob::getAuditState, dcaBDocParttimejob.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocParttimejob.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocParttimejob.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocParttimejob::getCreateTime, dcaBDocParttimejob.getCreateTimeFrom())
                                .le(DcaBDocParttimejob::getCreateTime, dcaBDocParttimejob.getCreateTimeTo());
                                }

        Page<DcaBDocParttimejob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocParttimejob> findDcaBDocParttimejobList (QueryRequest request, DcaBDocParttimejob dcaBDocParttimejob){
        try{
        Page<DcaBDocParttimejob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocParttimejob(page,dcaBDocParttimejob);
        }catch(Exception e){
        log.error("获取社会兼职表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocParttimejob(DcaBDocParttimejob dcaBDocParttimejob){
                dcaBDocParttimejob.setId(UUID.randomUUID().toString());
        dcaBDocParttimejob.setCreateTime(new Date());
        dcaBDocParttimejob.setIsDeletemark(1);
        this.save(dcaBDocParttimejob);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocParttimejob(DcaBDocParttimejob dcaBDocParttimejob){
        dcaBDocParttimejob.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocParttimejob(dcaBDocParttimejob);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocParttimejobs(String[]Ids){
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