package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocUndergraduateprize;
import cc.mrbird.febs.doctor.dao.DcaBDocUndergraduateprizeMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocUndergraduateprizeService;
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
 * 任现职以来本科教学工作获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocUndergraduateprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocUndergraduateprizeServiceImpl extends ServiceImpl<DcaBDocUndergraduateprizeMapper, DcaBDocUndergraduateprize> implements IDcaBDocUndergraduateprizeService {


@Override
@DS("slave")
public IPage<DcaBDocUndergraduateprize> findDcaBDocUndergraduateprizes(QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
        try{
        LambdaQueryWrapper<DcaBDocUndergraduateprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocUndergraduateprize::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocUndergraduateprize.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocUndergraduateprize::getUserAccount, dcaBDocUndergraduateprize.getUserAccount()).or()
        .like(DcaBDocUndergraduateprize::getUserAccountName, dcaBDocUndergraduateprize.getUserAccount()));

        }
        if (dcaBDocUndergraduateprize.getState()!=null) {
        queryWrapper.eq(DcaBDocUndergraduateprize::getState, dcaBDocUndergraduateprize.getState());
        }
       /** if (dcaBDocUndergraduateprize.getAuditState()!=null && (dcaBDocUndergraduateprize.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocUndergraduateprize::getAuditState, dcaBDocUndergraduateprize.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocUndergraduateprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocUndergraduateprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocUndergraduateprize::getCreateTime, dcaBDocUndergraduateprize.getCreateTimeFrom())
                                .le(DcaBDocUndergraduateprize::getCreateTime, dcaBDocUndergraduateprize.getCreateTimeTo());
                                }

        Page<DcaBDocUndergraduateprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocUndergraduateprize> findDcaBDocUndergraduateprizeList (QueryRequest request, DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
        try{
        Page<DcaBDocUndergraduateprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocUndergraduateprize(page,dcaBDocUndergraduateprize);
        }catch(Exception e){
        log.error("获取任现职以来本科教学工作获奖情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocUndergraduateprize(DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
                dcaBDocUndergraduateprize.setId(UUID.randomUUID().toString());
        dcaBDocUndergraduateprize.setCreateTime(new Date());
        dcaBDocUndergraduateprize.setIsDeletemark(1);
        this.save(dcaBDocUndergraduateprize);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocUndergraduateprize(DcaBDocUndergraduateprize dcaBDocUndergraduateprize){
        dcaBDocUndergraduateprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocUndergraduateprize(dcaBDocUndergraduateprize);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocUndergraduateprizes(String[]Ids){
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