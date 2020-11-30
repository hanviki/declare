package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencesearch;
import cc.mrbird.febs.dcacopy.dao.DcaBCopySciencesearchMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySciencesearchService;
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
 * 任现职以来承担的科研项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopySciencesearchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopySciencesearchServiceImpl extends ServiceImpl<DcaBCopySciencesearchMapper, DcaBCopySciencesearch> implements IDcaBCopySciencesearchService {


@Override
public IPage<DcaBCopySciencesearch> findDcaBCopySciencesearchs(QueryRequest request, DcaBCopySciencesearch dcaBCopySciencesearch){
        try{
        LambdaQueryWrapper<DcaBCopySciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySciencesearch::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopySciencesearch.getDcaYear())) {
                                queryWrapper.like(DcaBCopySciencesearch::getDcaYear, dcaBCopySciencesearch.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopySciencesearch.getUserAccountName())) {
                                queryWrapper.like(DcaBCopySciencesearch::getUserAccountName, dcaBCopySciencesearch.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopySciencesearch.getUserAccount())) {
                                queryWrapper.like(DcaBCopySciencesearch::getUserAccount, dcaBCopySciencesearch.getUserAccount());
                                }

        Page<DcaBCopySciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopySciencesearch> findDcaBCopySciencesearchList (QueryRequest request, DcaBCopySciencesearch dcaBCopySciencesearch){
        try{
        Page<DcaBCopySciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopySciencesearch(page,dcaBCopySciencesearch);
        }catch(Exception e){
        log.error("获取任现职以来承担的科研项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopySciencesearch(DcaBCopySciencesearch dcaBCopySciencesearch){
                dcaBCopySciencesearch.setId(UUID.randomUUID().toString());
        dcaBCopySciencesearch.setCreateTime(new Date());
        dcaBCopySciencesearch.setIsDeletemark(1);
        this.save(dcaBCopySciencesearch);
        }

@Override
@Transactional
public void updateDcaBCopySciencesearch(DcaBCopySciencesearch dcaBCopySciencesearch){
        dcaBCopySciencesearch.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopySciencesearch(dcaBCopySciencesearch);
        }

@Override
@Transactional
public void deleteDcaBCopySciencesearchs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopySciencesearch> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopySciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopySciencesearch::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopySciencesearch::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }