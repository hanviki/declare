package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBUndergraduate;
import cc.mrbird.febs.dca.dao.DcaBUndergraduateMapper;
import cc.mrbird.febs.dca.service.IDcaBUndergraduateService;
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
 * 本科教学情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
@Slf4j
@Service("IDcaBUndergraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUndergraduateServiceImpl extends ServiceImpl<DcaBUndergraduateMapper, DcaBUndergraduate> implements IDcaBUndergraduateService {


@Override
public IPage<DcaBUndergraduate> findDcaBUndergraduates(QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        try{
        LambdaQueryWrapper<DcaBUndergraduate> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUndergraduate::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBUndergraduate.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBUndergraduate.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBUndergraduate::getCreateTime, dcaBUndergraduate.getCreateTimeFrom())
                                .le(DcaBUndergraduate::getCreateTime, dcaBUndergraduate.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBUndergraduate.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBUndergraduate.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBUndergraduate::getModifyTime, dcaBUndergraduate.getModifyTimeFrom())
                                .le(DcaBUndergraduate::getModifyTime, dcaBUndergraduate.getModifyTimeTo());
                                }

        Page<DcaBUndergraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBUndergraduate> findDcaBUndergraduateList (QueryRequest request, DcaBUndergraduate dcaBUndergraduate){
        try{
        Page<DcaBUndergraduate> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBUndergraduate(page,dcaBUndergraduate);
        }catch(Exception e){
        log.error("获取本科教学情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate){
                dcaBUndergraduate.setId(UUID.randomUUID().toString());
        dcaBUndergraduate.setCreateTime(new Date());
        dcaBUndergraduate.setIsDeletemark(1);
        this.save(dcaBUndergraduate);
        }

@Override
@Transactional
public void updateDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate){
        dcaBUndergraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBUndergraduate(dcaBUndergraduate);
        }

@Override
@Transactional
public void deleteDcaBUndergraduates(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }