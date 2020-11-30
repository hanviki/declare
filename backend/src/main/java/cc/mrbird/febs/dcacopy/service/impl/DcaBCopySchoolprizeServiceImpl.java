package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySchoolprize;
import cc.mrbird.febs.dcacopy.dao.DcaBCopySchoolprizeMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySchoolprizeService;
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
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopySchoolprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopySchoolprizeServiceImpl extends ServiceImpl<DcaBCopySchoolprizeMapper, DcaBCopySchoolprize> implements IDcaBCopySchoolprizeService {


@Override
public IPage<DcaBCopySchoolprize> findDcaBCopySchoolprizes(QueryRequest request, DcaBCopySchoolprize dcaBCopySchoolprize){
        try{
        LambdaQueryWrapper<DcaBCopySchoolprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySchoolprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopySchoolprize.getDcaYear())) {
                                queryWrapper.like(DcaBCopySchoolprize::getDcaYear, dcaBCopySchoolprize.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopySchoolprize.getUserAccountName())) {
                                queryWrapper.like(DcaBCopySchoolprize::getUserAccountName, dcaBCopySchoolprize.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopySchoolprize.getUserAccount())) {
                                queryWrapper.like(DcaBCopySchoolprize::getUserAccount, dcaBCopySchoolprize.getUserAccount());
                                }

        Page<DcaBCopySchoolprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopySchoolprize> findDcaBCopySchoolprizeList (QueryRequest request, DcaBCopySchoolprize dcaBCopySchoolprize){
        try{
        Page<DcaBCopySchoolprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopySchoolprize(page,dcaBCopySchoolprize);
        }catch(Exception e){
        log.error("获取校教学质量奖、校教学成果奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopySchoolprize(DcaBCopySchoolprize dcaBCopySchoolprize){
                dcaBCopySchoolprize.setId(UUID.randomUUID().toString());
        dcaBCopySchoolprize.setCreateTime(new Date());
        dcaBCopySchoolprize.setIsDeletemark(1);
        this.save(dcaBCopySchoolprize);
        }

@Override
@Transactional
public void updateDcaBCopySchoolprize(DcaBCopySchoolprize dcaBCopySchoolprize){
        dcaBCopySchoolprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopySchoolprize(dcaBCopySchoolprize);
        }

@Override
@Transactional
public void deleteDcaBCopySchoolprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopySchoolprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopySchoolprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopySchoolprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopySchoolprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }