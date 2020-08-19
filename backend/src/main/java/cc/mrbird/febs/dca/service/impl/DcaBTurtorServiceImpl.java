package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTurtor;
import cc.mrbird.febs.dca.dao.DcaBTurtorMapper;
import cc.mrbird.febs.dca.service.IDcaBTurtorService;
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
 * 担任辅导员 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
@Slf4j
@Service("IDcaBTurtorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTurtorServiceImpl extends ServiceImpl<DcaBTurtorMapper, DcaBTurtor> implements IDcaBTurtorService {


@Override
public IPage<DcaBTurtor> findDcaBTurtors(QueryRequest request, DcaBTurtor dcaBTurtor){
        try{
        LambdaQueryWrapper<DcaBTurtor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTurtor::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBTurtor.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTurtor.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTurtor::getCreateTime, dcaBTurtor.getCreateTimeFrom())
                                .le(DcaBTurtor::getCreateTime, dcaBTurtor.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTurtor.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTurtor.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTurtor::getModifyTime, dcaBTurtor.getModifyTimeFrom())
                                .le(DcaBTurtor::getModifyTime, dcaBTurtor.getModifyTimeTo());
                                }

        Page<DcaBTurtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTurtor> findDcaBTurtorList (QueryRequest request, DcaBTurtor dcaBTurtor){
        try{
        Page<DcaBTurtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTurtor(page,dcaBTurtor);
        }catch(Exception e){
        log.error("获取担任辅导员失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTurtor(DcaBTurtor dcaBTurtor){
                dcaBTurtor.setId(UUID.randomUUID().toString());
        dcaBTurtor.setCreateTime(new Date());
        dcaBTurtor.setIsDeletemark(1);
        this.save(dcaBTurtor);
        }

@Override
@Transactional
public void updateDcaBTurtor(DcaBTurtor dcaBTurtor){
        dcaBTurtor.setModifyTime(new Date());
        this.baseMapper.updateDcaBTurtor(dcaBTurtor);
        }

@Override
@Transactional
public void deleteDcaBTurtors(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }