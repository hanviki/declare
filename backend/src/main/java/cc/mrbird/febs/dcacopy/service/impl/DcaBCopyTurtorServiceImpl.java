package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTurtor;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyTurtorMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTurtorService;
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
 * 担任辅导员教师班主任情况(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyTurtorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyTurtorServiceImpl extends ServiceImpl<DcaBCopyTurtorMapper, DcaBCopyTurtor> implements IDcaBCopyTurtorService {


@Override
public IPage<DcaBCopyTurtor> findDcaBCopyTurtors(QueryRequest request, DcaBCopyTurtor dcaBCopyTurtor){
        try{
        LambdaQueryWrapper<DcaBCopyTurtor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyTurtor::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyTurtor.getDcaYear())) {
                                queryWrapper.like(DcaBCopyTurtor::getDcaYear, dcaBCopyTurtor.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTurtor.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyTurtor::getUserAccountName, dcaBCopyTurtor.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTurtor.getUserAccount())) {
                                queryWrapper.like(DcaBCopyTurtor::getUserAccount, dcaBCopyTurtor.getUserAccount());
                                }

        Page<DcaBCopyTurtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyTurtor> findDcaBCopyTurtorList (QueryRequest request, DcaBCopyTurtor dcaBCopyTurtor){
        try{
        Page<DcaBCopyTurtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyTurtor(page,dcaBCopyTurtor);
        }catch(Exception e){
        log.error("获取担任辅导员教师班主任情况(教师系列需填写)失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyTurtor(DcaBCopyTurtor dcaBCopyTurtor){
                dcaBCopyTurtor.setId(UUID.randomUUID().toString());
        dcaBCopyTurtor.setCreateTime(new Date());
        dcaBCopyTurtor.setIsDeletemark(1);
        this.save(dcaBCopyTurtor);
        }

@Override
@Transactional
public void updateDcaBCopyTurtor(DcaBCopyTurtor dcaBCopyTurtor){
        dcaBCopyTurtor.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyTurtor(dcaBCopyTurtor);
        }

@Override
@Transactional
public void deleteDcaBCopyTurtors(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyTurtor> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyTurtor> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyTurtor::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyTurtor::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }