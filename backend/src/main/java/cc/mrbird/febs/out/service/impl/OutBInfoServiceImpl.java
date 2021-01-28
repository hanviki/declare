package cc.mrbird.febs.out.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.out.entity.OutBInfo;
import cc.mrbird.febs.out.dao.OutBInfoMapper;
import cc.mrbird.febs.out.service.IOutBInfoService;
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
 * 接口投票 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-01
 */
@Slf4j
@Service("IOutBInfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutBInfoServiceImpl extends ServiceImpl<OutBInfoMapper, OutBInfo> implements IOutBInfoService {


@Override
public IPage<OutBInfo> findOutBInfos(QueryRequest request, OutBInfo outBInfo){
        try{
        LambdaQueryWrapper<OutBInfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(OutBInfo::getIsDeletemark, 1);//1是未删 0是已删
        if(StringUtils.isNotBlank(outBInfo.getDcayear())){
                queryWrapper.eq(OutBInfo::getDcayear, outBInfo.getDcayear());//1是未删 0是已删
        }
                if(StringUtils.isNotBlank(outBInfo.getTpzb())){
                        queryWrapper.like(OutBInfo::getTpzb, outBInfo.getTpzb());//1是未删 0是已删
                }

        Page<OutBInfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<OutBInfo> findOutBInfoList (QueryRequest request, OutBInfo outBInfo){
        try{
        Page<OutBInfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findOutBInfo(page,outBInfo);
        }catch(Exception e){
        log.error("获取接口投票失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createOutBInfo(OutBInfo outBInfo){
                outBInfo.setCreateTime(new Date());
        outBInfo.setIsDeletemark(1);
        this.save(outBInfo);
        }

@Override
@Transactional
public void updateOutBInfo(OutBInfo outBInfo){
        outBInfo.setModifyTime(new Date());
        this.baseMapper.updateOutBInfo(outBInfo);
        }

@Override
@Transactional
public void deleteOutBInfos(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<OutBInfo> getAll(String dcaYear,String tpzb,String dyzc,String tpbt){
        LambdaQueryWrapper<OutBInfo> queryWrapper=new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(OutBInfo::getDcayear, dcaYear.trim());
        }
        if (StringUtils.isNotBlank(tpzb)) {
                queryWrapper.eq(OutBInfo::getTpzb, tpzb.trim());
        }
        if (StringUtils.isNotBlank(dyzc)) {
                queryWrapper.eq(OutBInfo::getDyzc, dyzc.trim());
        }
        if (StringUtils.isNotBlank(tpbt)) {
                queryWrapper.eq(OutBInfo::getTpbt, tpbt.trim());
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }