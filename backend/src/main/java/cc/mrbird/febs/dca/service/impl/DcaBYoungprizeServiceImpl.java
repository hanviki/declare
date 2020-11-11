package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBYoungprize;
import cc.mrbird.febs.dca.dao.DcaBYoungprizeMapper;
import cc.mrbird.febs.dca.service.IDcaBYoungprizeService;
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
 * 青年教师教学竞赛获奖 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
@Slf4j
@Service("IDcaBYoungprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBYoungprizeServiceImpl extends ServiceImpl<DcaBYoungprizeMapper, DcaBYoungprize> implements IDcaBYoungprizeService {


@Override
public IPage<DcaBYoungprize> findDcaBYoungprizes(QueryRequest request, DcaBYoungprize dcaBYoungprize){
        try{
        LambdaQueryWrapper<DcaBYoungprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBYoungprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBYoungprize.getUserAccount())) {
                                queryWrapper.like(DcaBYoungprize::getUserAccount, dcaBYoungprize.getUserAccount());
                                }
                                if (dcaBYoungprize.getState()!=null) {
                                queryWrapper.eq(DcaBYoungprize::getState, dcaBYoungprize.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBYoungprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBYoungprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBYoungprize::getCreateTime, dcaBYoungprize.getCreateTimeFrom())
                                .le(DcaBYoungprize::getCreateTime, dcaBYoungprize.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBYoungprize.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBYoungprize.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBYoungprize::getModifyTime, dcaBYoungprize.getModifyTimeFrom())
                                .le(DcaBYoungprize::getModifyTime, dcaBYoungprize.getModifyTimeTo());
                                }

        Page<DcaBYoungprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBYoungprize> findDcaBYoungprizeList (QueryRequest request, DcaBYoungprize dcaBYoungprize){
        try{
        Page<DcaBYoungprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBYoungprize(page,dcaBYoungprize);
        }catch(Exception e){
        log.error("获取青年教师教学竞赛获奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBYoungprize(DcaBYoungprize dcaBYoungprize){
                dcaBYoungprize.setId(UUID.randomUUID().toString());
        dcaBYoungprize.setCreateTime(new Date());
        dcaBYoungprize.setIsDeletemark(1);
        this.save(dcaBYoungprize);
        }

@Override
@Transactional
public void updateDcaBYoungprize(DcaBYoungprize dcaBYoungprize){
        dcaBYoungprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBYoungprize(dcaBYoungprize);
        }

@Override
@Transactional
public void deleteDcaBYoungprizes(String[]Ids){
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