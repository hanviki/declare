package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyScientificprize;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyScientificprizeMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyScientificprizeService;
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
 * 任现职以来科研获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyScientificprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyScientificprizeServiceImpl extends ServiceImpl<DcaBCopyScientificprizeMapper, DcaBCopyScientificprize> implements IDcaBCopyScientificprizeService {


@Override
public IPage<DcaBCopyScientificprize> findDcaBCopyScientificprizes(QueryRequest request, DcaBCopyScientificprize dcaBCopyScientificprize){
        try{
        LambdaQueryWrapper<DcaBCopyScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyScientificprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyScientificprize.getDcaYear())) {
                                queryWrapper.like(DcaBCopyScientificprize::getDcaYear, dcaBCopyScientificprize.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyScientificprize.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyScientificprize::getUserAccountName, dcaBCopyScientificprize.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyScientificprize.getUserAccount())) {
                                queryWrapper.like(DcaBCopyScientificprize::getUserAccount, dcaBCopyScientificprize.getUserAccount());
                                }

        Page<DcaBCopyScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyScientificprize> findDcaBCopyScientificprizeList (QueryRequest request, DcaBCopyScientificprize dcaBCopyScientificprize){
        try{
        Page<DcaBCopyScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyScientificprize(page,dcaBCopyScientificprize);
        }catch(Exception e){
        log.error("获取任现职以来科研获奖情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyScientificprize(DcaBCopyScientificprize dcaBCopyScientificprize){
                dcaBCopyScientificprize.setId(UUID.randomUUID().toString());
        dcaBCopyScientificprize.setCreateTime(new Date());
        dcaBCopyScientificprize.setIsDeletemark(1);
        this.save(dcaBCopyScientificprize);
        }

@Override
@Transactional
public void updateDcaBCopyScientificprize(DcaBCopyScientificprize dcaBCopyScientificprize){
        dcaBCopyScientificprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyScientificprize(dcaBCopyScientificprize);
        }

@Override
@Transactional
public void deleteDcaBCopyScientificprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyScientificprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyScientificprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyScientificprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }