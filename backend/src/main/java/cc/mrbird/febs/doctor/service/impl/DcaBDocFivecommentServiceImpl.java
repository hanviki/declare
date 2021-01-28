package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocFivecomment;
import cc.mrbird.febs.doctor.dao.DcaBDocFivecommentMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocFivecommentService;
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
 * 近五年总体项目评价 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocFivecommentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocFivecommentServiceImpl extends ServiceImpl<DcaBDocFivecommentMapper, DcaBDocFivecomment> implements IDcaBDocFivecommentService {


@Override
@DS("slave")
public IPage<DcaBDocFivecomment> findDcaBDocFivecomments(QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment){
        try{
        LambdaQueryWrapper<DcaBDocFivecomment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocFivecomment::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocFivecomment.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocFivecomment::getUserAccount, dcaBDocFivecomment.getUserAccount()).or()
        .like(DcaBDocFivecomment::getUserAccountName, dcaBDocFivecomment.getUserAccount()));

        }
        if (dcaBDocFivecomment.getState()!=null) {
        queryWrapper.eq(DcaBDocFivecomment::getState, dcaBDocFivecomment.getState());
        }
       /** if (dcaBDocFivecomment.getAuditState()!=null && (dcaBDocFivecomment.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocFivecomment::getAuditState, dcaBDocFivecomment.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocFivecomment.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocFivecomment.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocFivecomment::getCreateTime, dcaBDocFivecomment.getCreateTimeFrom())
                                .le(DcaBDocFivecomment::getCreateTime, dcaBDocFivecomment.getCreateTimeTo());
                                }

        Page<DcaBDocFivecomment> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocFivecomment> findDcaBDocFivecommentList (QueryRequest request, DcaBDocFivecomment dcaBDocFivecomment){
        try{
        Page<DcaBDocFivecomment> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocFivecomment(page,dcaBDocFivecomment);
        }catch(Exception e){
        log.error("获取近五年总体项目评价失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocFivecomment(DcaBDocFivecomment dcaBDocFivecomment){
                dcaBDocFivecomment.setId(UUID.randomUUID().toString());
        dcaBDocFivecomment.setCreateTime(new Date());
        dcaBDocFivecomment.setIsDeletemark(1);
        this.save(dcaBDocFivecomment);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocFivecomment(DcaBDocFivecomment dcaBDocFivecomment){
        dcaBDocFivecomment.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocFivecomment(dcaBDocFivecomment);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocFivecomments(String[]Ids){
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