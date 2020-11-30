package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyFivecomment;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyFivecommentMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyFivecommentService;
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
 * 近五年总体项目评价 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyFivecommentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyFivecommentServiceImpl extends ServiceImpl<DcaBCopyFivecommentMapper, DcaBCopyFivecomment> implements IDcaBCopyFivecommentService {


@Override
public IPage<DcaBCopyFivecomment> findDcaBCopyFivecomments(QueryRequest request, DcaBCopyFivecomment dcaBCopyFivecomment){
        try{
        LambdaQueryWrapper<DcaBCopyFivecomment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyFivecomment::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyFivecomment.getDcaYear())) {
                                queryWrapper.like(DcaBCopyFivecomment::getDcaYear, dcaBCopyFivecomment.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyFivecomment.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyFivecomment::getUserAccountName, dcaBCopyFivecomment.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyFivecomment.getUserAccount())) {
                                queryWrapper.like(DcaBCopyFivecomment::getUserAccount, dcaBCopyFivecomment.getUserAccount());
                                }

        Page<DcaBCopyFivecomment> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyFivecomment> findDcaBCopyFivecommentList (QueryRequest request, DcaBCopyFivecomment dcaBCopyFivecomment){
        try{
        Page<DcaBCopyFivecomment> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyFivecomment(page,dcaBCopyFivecomment);
        }catch(Exception e){
        log.error("获取近五年总体项目评价失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyFivecomment(DcaBCopyFivecomment dcaBCopyFivecomment){
                dcaBCopyFivecomment.setId(UUID.randomUUID().toString());
        dcaBCopyFivecomment.setCreateTime(new Date());
        dcaBCopyFivecomment.setIsDeletemark(1);
        this.save(dcaBCopyFivecomment);
        }

@Override
@Transactional
public void updateDcaBCopyFivecomment(DcaBCopyFivecomment dcaBCopyFivecomment){
        dcaBCopyFivecomment.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyFivecomment(dcaBCopyFivecomment);
        }

@Override
@Transactional
public void deleteDcaBCopyFivecomments(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyFivecomment> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyFivecomment> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyFivecomment::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyFivecomment::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }