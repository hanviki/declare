package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAssitant;
import cc.mrbird.febs.dca.dao.DcaBAssitantMapper;
import cc.mrbird.febs.dca.service.IDcaBAssitantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 * 支援情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-03-02
 */
@Slf4j
@Service("IDcaBAssitantService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAssitantServiceImpl extends ServiceImpl<DcaBAssitantMapper, DcaBAssitant> implements IDcaBAssitantService {


@Override
public IPage<DcaBAssitant> findDcaBAssitants(QueryRequest request, DcaBAssitant dcaBAssitant){
        try{
        LambdaQueryWrapper<DcaBAssitant> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAssitant::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBAssitant.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAssitant.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBAssitant::getCreateTime, dcaBAssitant.getCreateTimeFrom())
                                .le(DcaBAssitant::getCreateTime, dcaBAssitant.getCreateTimeTo());
                                }

        Page<DcaBAssitant> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBAssitant> findDcaBAssitantList (QueryRequest request, DcaBAssitant dcaBAssitant){
        try{
        Page<DcaBAssitant> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBAssitant(page,dcaBAssitant);
        }catch(Exception e){
        log.error("获取支援情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBAssitant(DcaBAssitant dcaBAssitant){
                dcaBAssitant.setId(UUID.randomUUID().toString());
        dcaBAssitant.setCreateTime(new Date());
        dcaBAssitant.setIsDeletemark(1);
        this.save(dcaBAssitant);
        }

@Override
@Transactional
public void updateDcaBAssitant(DcaBAssitant dcaBAssitant){
        dcaBAssitant.setModifyTime(new Date());
        this.baseMapper.updateDcaBAssitant(dcaBAssitant);
        }

@Override
@Transactional
public void deleteDcaBAssitants(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBAssitant> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBAssitant> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBAssitant::getUserAccount, userAccount);
        }
//        if (StringUtils.isNotBlank(dcaYear)) {
//        queryWrapper.eq(DcaBAssitant::getDcaYear, dcaYear);
//        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }