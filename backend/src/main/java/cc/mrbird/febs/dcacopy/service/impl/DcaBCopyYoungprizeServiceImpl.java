package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyYoungprize;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyYoungprizeMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyYoungprizeService;
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
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyYoungprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyYoungprizeServiceImpl extends ServiceImpl<DcaBCopyYoungprizeMapper, DcaBCopyYoungprize> implements IDcaBCopyYoungprizeService {


@Override
public IPage<DcaBCopyYoungprize> findDcaBCopyYoungprizes(QueryRequest request, DcaBCopyYoungprize dcaBCopyYoungprize){
        try{
        LambdaQueryWrapper<DcaBCopyYoungprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyYoungprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyYoungprize.getDcaYear())) {
                                queryWrapper.like(DcaBCopyYoungprize::getDcaYear, dcaBCopyYoungprize.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyYoungprize.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyYoungprize::getUserAccountName, dcaBCopyYoungprize.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyYoungprize.getUserAccount())) {
                                queryWrapper.like(DcaBCopyYoungprize::getUserAccount, dcaBCopyYoungprize.getUserAccount());
                                }

        Page<DcaBCopyYoungprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyYoungprize> findDcaBCopyYoungprizeList (QueryRequest request, DcaBCopyYoungprize dcaBCopyYoungprize){
        try{
        Page<DcaBCopyYoungprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyYoungprize(page,dcaBCopyYoungprize);
        }catch(Exception e){
        log.error("获取青年教师教学竞赛获奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyYoungprize(DcaBCopyYoungprize dcaBCopyYoungprize){
                dcaBCopyYoungprize.setId(UUID.randomUUID().toString());
        dcaBCopyYoungprize.setCreateTime(new Date());
        dcaBCopyYoungprize.setIsDeletemark(1);
        this.save(dcaBCopyYoungprize);
        }

@Override
@Transactional
public void updateDcaBCopyYoungprize(DcaBCopyYoungprize dcaBCopyYoungprize){
        dcaBCopyYoungprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyYoungprize(dcaBCopyYoungprize);
        }

@Override
@Transactional
public void deleteDcaBCopyYoungprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyYoungprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyYoungprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyYoungprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyYoungprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }