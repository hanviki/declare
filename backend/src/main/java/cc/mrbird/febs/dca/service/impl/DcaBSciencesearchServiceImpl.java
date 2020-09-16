package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBSciencesearch;
import cc.mrbird.febs.dca.dao.DcaBSciencesearchMapper;
import cc.mrbird.febs.dca.service.IDcaBSciencesearchService;
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
 * 科研项目 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-15
 */
@Slf4j
@Service("IDcaBSciencesearchService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSciencesearchServiceImpl extends ServiceImpl<DcaBSciencesearchMapper, DcaBSciencesearch> implements IDcaBSciencesearchService {


@Override
public IPage<DcaBSciencesearch> findDcaBSciencesearchs(QueryRequest request, DcaBSciencesearch dcaBSciencesearch){
        try{
        LambdaQueryWrapper<DcaBSciencesearch> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBSciencesearch::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBSciencesearch.getUserAccount())) {
                                queryWrapper.like(DcaBSciencesearch::getUserAccount, dcaBSciencesearch.getUserAccount());
                                }
                                if (dcaBSciencesearch.getState()!=null) {
                                queryWrapper.eq(DcaBSciencesearch::getState, dcaBSciencesearch.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBSciencesearch.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSciencesearch.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBSciencesearch::getCreateTime, dcaBSciencesearch.getCreateTimeFrom())
                                .le(DcaBSciencesearch::getCreateTime, dcaBSciencesearch.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBSciencesearch.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBSciencesearch.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBSciencesearch::getModifyTime, dcaBSciencesearch.getModifyTimeFrom())
                                .le(DcaBSciencesearch::getModifyTime, dcaBSciencesearch.getModifyTimeTo());
                                }

        Page<DcaBSciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBSciencesearch> findDcaBSciencesearchList (QueryRequest request, DcaBSciencesearch dcaBSciencesearch){
        try{
        Page<DcaBSciencesearch> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBSciencesearch(page,dcaBSciencesearch);
        }catch(Exception e){
        log.error("获取科研项目失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch){
                dcaBSciencesearch.setId(UUID.randomUUID().toString());
        dcaBSciencesearch.setCreateTime(new Date());
        dcaBSciencesearch.setIsDeletemark(1);
        this.save(dcaBSciencesearch);
        }

@Override
@Transactional
public void updateDcaBSciencesearch(DcaBSciencesearch dcaBSciencesearch){
        dcaBSciencesearch.setModifyTime(new Date());
        this.baseMapper.updateDcaBSciencesearch(dcaBSciencesearch);
        }

@Override
@Transactional
public void deleteDcaBSciencesearchs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }