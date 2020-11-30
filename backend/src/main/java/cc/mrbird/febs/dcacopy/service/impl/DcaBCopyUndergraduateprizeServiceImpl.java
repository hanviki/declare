package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyUndergraduateprize;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyUndergraduateprizeMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyUndergraduateprizeService;
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
 * 任现职以来本科教学工作获奖情况(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyUndergraduateprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyUndergraduateprizeServiceImpl extends ServiceImpl<DcaBCopyUndergraduateprizeMapper, DcaBCopyUndergraduateprize> implements IDcaBCopyUndergraduateprizeService {


@Override
public IPage<DcaBCopyUndergraduateprize> findDcaBCopyUndergraduateprizes(QueryRequest request, DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize){
        try{
        LambdaQueryWrapper<DcaBCopyUndergraduateprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyUndergraduateprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyUndergraduateprize.getDcaYear())) {
                                queryWrapper.like(DcaBCopyUndergraduateprize::getDcaYear, dcaBCopyUndergraduateprize.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyUndergraduateprize.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyUndergraduateprize::getUserAccountName, dcaBCopyUndergraduateprize.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyUndergraduateprize.getUserAccount())) {
                                queryWrapper.like(DcaBCopyUndergraduateprize::getUserAccount, dcaBCopyUndergraduateprize.getUserAccount());
                                }

        Page<DcaBCopyUndergraduateprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyUndergraduateprize> findDcaBCopyUndergraduateprizeList (QueryRequest request, DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize){
        try{
        Page<DcaBCopyUndergraduateprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyUndergraduateprize(page,dcaBCopyUndergraduateprize);
        }catch(Exception e){
        log.error("获取任现职以来本科教学工作获奖情况(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyUndergraduateprize(DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize){
                dcaBCopyUndergraduateprize.setId(UUID.randomUUID().toString());
        dcaBCopyUndergraduateprize.setCreateTime(new Date());
        dcaBCopyUndergraduateprize.setIsDeletemark(1);
        this.save(dcaBCopyUndergraduateprize);
        }

@Override
@Transactional
public void updateDcaBCopyUndergraduateprize(DcaBCopyUndergraduateprize dcaBCopyUndergraduateprize){
        dcaBCopyUndergraduateprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyUndergraduateprize(dcaBCopyUndergraduateprize);
        }

@Override
@Transactional
public void deleteDcaBCopyUndergraduateprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyUndergraduateprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyUndergraduateprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyUndergraduateprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyUndergraduateprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }