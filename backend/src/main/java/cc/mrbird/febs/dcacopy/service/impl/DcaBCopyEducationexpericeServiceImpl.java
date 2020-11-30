package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyEducationexperice;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyEducationexpericeMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyEducationexpericeService;
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
 * 学习工作经历 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyEducationexpericeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyEducationexpericeServiceImpl extends ServiceImpl<DcaBCopyEducationexpericeMapper, DcaBCopyEducationexperice> implements IDcaBCopyEducationexpericeService {


@Override
public IPage<DcaBCopyEducationexperice> findDcaBCopyEducationexperices(QueryRequest request, DcaBCopyEducationexperice dcaBCopyEducationexperice){
        try{
        LambdaQueryWrapper<DcaBCopyEducationexperice> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyEducationexperice::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyEducationexperice.getDcaYear())) {
                                queryWrapper.like(DcaBCopyEducationexperice::getDcaYear, dcaBCopyEducationexperice.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyEducationexperice.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyEducationexperice::getUserAccountName, dcaBCopyEducationexperice.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyEducationexperice.getUserAccount())) {
                                queryWrapper.like(DcaBCopyEducationexperice::getUserAccount, dcaBCopyEducationexperice.getUserAccount());
                                }

        Page<DcaBCopyEducationexperice> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyEducationexperice> findDcaBCopyEducationexpericeList (QueryRequest request, DcaBCopyEducationexperice dcaBCopyEducationexperice){
        try{
        Page<DcaBCopyEducationexperice> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyEducationexperice(page,dcaBCopyEducationexperice);
        }catch(Exception e){
        log.error("获取学习工作经历失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyEducationexperice(DcaBCopyEducationexperice dcaBCopyEducationexperice){
                dcaBCopyEducationexperice.setId(UUID.randomUUID().toString());
        dcaBCopyEducationexperice.setCreateTime(new Date());
        dcaBCopyEducationexperice.setIsDeletemark(1);
        this.save(dcaBCopyEducationexperice);
        }

@Override
@Transactional
public void updateDcaBCopyEducationexperice(DcaBCopyEducationexperice dcaBCopyEducationexperice){
        dcaBCopyEducationexperice.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyEducationexperice(dcaBCopyEducationexperice);
        }

@Override
@Transactional
public void deleteDcaBCopyEducationexperices(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyEducationexperice> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyEducationexperice> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyEducationexperice::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyEducationexperice::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }