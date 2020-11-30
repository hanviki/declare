package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyLastemploy;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyLastemployMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyLastemployService;
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
 * 完成上一聘期 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyLastemployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyLastemployServiceImpl extends ServiceImpl<DcaBCopyLastemployMapper, DcaBCopyLastemploy> implements IDcaBCopyLastemployService {


@Override
public IPage<DcaBCopyLastemploy> findDcaBCopyLastemploys(QueryRequest request, DcaBCopyLastemploy dcaBCopyLastemploy){
        try{
        LambdaQueryWrapper<DcaBCopyLastemploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyLastemploy::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyLastemploy.getDcaYear())) {
                                queryWrapper.like(DcaBCopyLastemploy::getDcaYear, dcaBCopyLastemploy.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyLastemploy.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyLastemploy::getUserAccountName, dcaBCopyLastemploy.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyLastemploy.getUserAccount())) {
                                queryWrapper.like(DcaBCopyLastemploy::getUserAccount, dcaBCopyLastemploy.getUserAccount());
                                }

        Page<DcaBCopyLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyLastemploy> findDcaBCopyLastemployList (QueryRequest request, DcaBCopyLastemploy dcaBCopyLastemploy){
        try{
        Page<DcaBCopyLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyLastemploy(page,dcaBCopyLastemploy);
        }catch(Exception e){
        log.error("获取完成上一聘期失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyLastemploy(DcaBCopyLastemploy dcaBCopyLastemploy){
                dcaBCopyLastemploy.setId(UUID.randomUUID().toString());
        dcaBCopyLastemploy.setCreateTime(new Date());
        dcaBCopyLastemploy.setIsDeletemark(1);
        this.save(dcaBCopyLastemploy);
        }

@Override
@Transactional
public void updateDcaBCopyLastemploy(DcaBCopyLastemploy dcaBCopyLastemploy){
        dcaBCopyLastemploy.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyLastemploy(dcaBCopyLastemploy);
        }

@Override
@Transactional
public void deleteDcaBCopyLastemploys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyLastemploy> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyLastemploy> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyLastemploy::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyLastemploy::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }