package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocSchoolprize;
import cc.mrbird.febs.doctor.dao.DcaBDocSchoolprizeMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocSchoolprizeService;
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
 * 校教学质量奖、校教学成果奖 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocSchoolprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocSchoolprizeServiceImpl extends ServiceImpl<DcaBDocSchoolprizeMapper, DcaBDocSchoolprize> implements IDcaBDocSchoolprizeService {


@Override
@DS("slave")
public IPage<DcaBDocSchoolprize> findDcaBDocSchoolprizes(QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize){
        try{
        LambdaQueryWrapper<DcaBDocSchoolprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocSchoolprize::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocSchoolprize.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocSchoolprize::getUserAccount, dcaBDocSchoolprize.getUserAccount()).or()
        .like(DcaBDocSchoolprize::getUserAccountName, dcaBDocSchoolprize.getUserAccount()));

        }
        if (dcaBDocSchoolprize.getState()!=null) {
        queryWrapper.eq(DcaBDocSchoolprize::getState, dcaBDocSchoolprize.getState());
        }
       /** if (dcaBDocSchoolprize.getAuditState()!=null && (dcaBDocSchoolprize.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocSchoolprize::getAuditState, dcaBDocSchoolprize.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocSchoolprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocSchoolprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocSchoolprize::getCreateTime, dcaBDocSchoolprize.getCreateTimeFrom())
                                .le(DcaBDocSchoolprize::getCreateTime, dcaBDocSchoolprize.getCreateTimeTo());
                                }

        Page<DcaBDocSchoolprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocSchoolprize> findDcaBDocSchoolprizeList (QueryRequest request, DcaBDocSchoolprize dcaBDocSchoolprize){
        try{
        Page<DcaBDocSchoolprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocSchoolprize(page,dcaBDocSchoolprize);
        }catch(Exception e){
        log.error("获取校教学质量奖、校教学成果奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocSchoolprize(DcaBDocSchoolprize dcaBDocSchoolprize){
                dcaBDocSchoolprize.setId(UUID.randomUUID().toString());
        dcaBDocSchoolprize.setCreateTime(new Date());
        dcaBDocSchoolprize.setIsDeletemark(1);
        this.save(dcaBDocSchoolprize);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocSchoolprize(DcaBDocSchoolprize dcaBDocSchoolprize){
        dcaBDocSchoolprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocSchoolprize(dcaBDocSchoolprize);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocSchoolprizes(String[]Ids){
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