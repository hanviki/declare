package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyParttimejob;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyParttimejobMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyParttimejobService;
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
 * 社会兼职表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyParttimejobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyParttimejobServiceImpl extends ServiceImpl<DcaBCopyParttimejobMapper, DcaBCopyParttimejob> implements IDcaBCopyParttimejobService {


@Override
public IPage<DcaBCopyParttimejob> findDcaBCopyParttimejobs(QueryRequest request, DcaBCopyParttimejob dcaBCopyParttimejob){
        try{
        LambdaQueryWrapper<DcaBCopyParttimejob> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyParttimejob::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyParttimejob.getDcaYear())) {
                                queryWrapper.like(DcaBCopyParttimejob::getDcaYear, dcaBCopyParttimejob.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyParttimejob.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyParttimejob::getUserAccountName, dcaBCopyParttimejob.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyParttimejob.getUserAccount())) {
                                queryWrapper.like(DcaBCopyParttimejob::getUserAccount, dcaBCopyParttimejob.getUserAccount());
                                }

        Page<DcaBCopyParttimejob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyParttimejob> findDcaBCopyParttimejobList (QueryRequest request, DcaBCopyParttimejob dcaBCopyParttimejob){
        try{
        Page<DcaBCopyParttimejob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyParttimejob(page,dcaBCopyParttimejob);
        }catch(Exception e){
        log.error("获取社会兼职表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyParttimejob(DcaBCopyParttimejob dcaBCopyParttimejob){
                dcaBCopyParttimejob.setId(UUID.randomUUID().toString());
        dcaBCopyParttimejob.setCreateTime(new Date());
        dcaBCopyParttimejob.setIsDeletemark(1);
        this.save(dcaBCopyParttimejob);
        }

@Override
@Transactional
public void updateDcaBCopyParttimejob(DcaBCopyParttimejob dcaBCopyParttimejob){
        dcaBCopyParttimejob.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyParttimejob(dcaBCopyParttimejob);
        }

@Override
@Transactional
public void deleteDcaBCopyParttimejobs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyParttimejob> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyParttimejob> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyParttimejob::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyParttimejob::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }