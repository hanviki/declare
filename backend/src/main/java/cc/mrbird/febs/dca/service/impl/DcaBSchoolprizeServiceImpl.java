package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBSchoolprize;
import cc.mrbird.febs.dca.dao.DcaBSchoolprizeMapper;
import cc.mrbird.febs.dca.service.IDcaBSchoolprizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @since 2020-11-03
 */
@Slf4j
@Service("IDcaBSchoolprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSchoolprizeServiceImpl extends ServiceImpl<DcaBSchoolprizeMapper, DcaBSchoolprize> implements IDcaBSchoolprizeService {


@Override
public IPage<DcaBSchoolprize> findDcaBSchoolprizes(QueryRequest request, DcaBSchoolprize dcaBSchoolprize){
        try{
        LambdaQueryWrapper<DcaBSchoolprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBSchoolprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBSchoolprize.getUserAccount())) {
                                queryWrapper.like(DcaBSchoolprize::getUserAccount, dcaBSchoolprize.getUserAccount());
                                }
                                if (dcaBSchoolprize.getState()!=null) {
                                queryWrapper.eq(DcaBSchoolprize::getState, dcaBSchoolprize.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBSchoolprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSchoolprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBSchoolprize::getCreateTime, dcaBSchoolprize.getCreateTimeFrom())
                                .le(DcaBSchoolprize::getCreateTime, dcaBSchoolprize.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBSchoolprize.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBSchoolprize.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBSchoolprize::getModifyTime, dcaBSchoolprize.getModifyTimeFrom())
                                .le(DcaBSchoolprize::getModifyTime, dcaBSchoolprize.getModifyTimeTo());
                                }

        Page<DcaBSchoolprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBSchoolprize> findDcaBSchoolprizeList (QueryRequest request, DcaBSchoolprize dcaBSchoolprize){
        try{
        Page<DcaBSchoolprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBSchoolprize(page,dcaBSchoolprize);
        }catch(Exception e){
        log.error("获取校教学质量奖、校教学成果奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize){
                dcaBSchoolprize.setId(UUID.randomUUID().toString());
        dcaBSchoolprize.setCreateTime(new Date());
        dcaBSchoolprize.setIsDeletemark(1);
        this.save(dcaBSchoolprize);
        }

@Override
@Transactional
public void updateDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize){
        dcaBSchoolprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBSchoolprize(dcaBSchoolprize);
        }

@Override
@Transactional
public void deleteDcaBSchoolprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }