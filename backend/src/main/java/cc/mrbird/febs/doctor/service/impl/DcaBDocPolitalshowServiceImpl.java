package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocPolitalshow;
import cc.mrbird.febs.doctor.dao.DcaBDocPolitalshowMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocPolitalshowService;
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
 * 个人思想政治表现 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocPolitalshowService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocPolitalshowServiceImpl extends ServiceImpl<DcaBDocPolitalshowMapper, DcaBDocPolitalshow> implements IDcaBDocPolitalshowService {


@Override
@DS("slave")
public IPage<DcaBDocPolitalshow> findDcaBDocPolitalshows(QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow){
        try{
        LambdaQueryWrapper<DcaBDocPolitalshow> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocPolitalshow::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocPolitalshow.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocPolitalshow::getUserAccount, dcaBDocPolitalshow.getUserAccount()).or()
        .like(DcaBDocPolitalshow::getUserAccountName, dcaBDocPolitalshow.getUserAccount()));

        }
        if (dcaBDocPolitalshow.getState()!=null) {
        queryWrapper.eq(DcaBDocPolitalshow::getState, dcaBDocPolitalshow.getState());
        }
       /** if (dcaBDocPolitalshow.getAuditState()!=null && (dcaBDocPolitalshow.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocPolitalshow::getAuditState, dcaBDocPolitalshow.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocPolitalshow.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocPolitalshow.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocPolitalshow::getCreateTime, dcaBDocPolitalshow.getCreateTimeFrom())
                                .le(DcaBDocPolitalshow::getCreateTime, dcaBDocPolitalshow.getCreateTimeTo());
                                }

        Page<DcaBDocPolitalshow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocPolitalshow> findDcaBDocPolitalshowList (QueryRequest request, DcaBDocPolitalshow dcaBDocPolitalshow){
        try{
        Page<DcaBDocPolitalshow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocPolitalshow(page,dcaBDocPolitalshow);
        }catch(Exception e){
        log.error("获取个人思想政治表现失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocPolitalshow(DcaBDocPolitalshow dcaBDocPolitalshow){
                dcaBDocPolitalshow.setId(UUID.randomUUID().toString());
        dcaBDocPolitalshow.setCreateTime(new Date());
        dcaBDocPolitalshow.setIsDeletemark(1);
        this.save(dcaBDocPolitalshow);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocPolitalshow(DcaBDocPolitalshow dcaBDocPolitalshow){
        dcaBDocPolitalshow.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocPolitalshow(dcaBDocPolitalshow);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocPolitalshows(String[]Ids){
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