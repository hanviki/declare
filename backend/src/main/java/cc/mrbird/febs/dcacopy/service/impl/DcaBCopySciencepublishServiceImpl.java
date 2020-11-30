package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopySciencepublish;
import cc.mrbird.febs.dcacopy.dao.DcaBCopySciencepublishMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopySciencepublishService;
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
 * 任现职以来发表的论文、出版著作 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopySciencepublishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopySciencepublishServiceImpl extends ServiceImpl<DcaBCopySciencepublishMapper, DcaBCopySciencepublish> implements IDcaBCopySciencepublishService {


@Override
public IPage<DcaBCopySciencepublish> findDcaBCopySciencepublishs(QueryRequest request, DcaBCopySciencepublish dcaBCopySciencepublish){
        try{
        LambdaQueryWrapper<DcaBCopySciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopySciencepublish::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopySciencepublish.getDcaYear())) {
                                queryWrapper.like(DcaBCopySciencepublish::getDcaYear, dcaBCopySciencepublish.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopySciencepublish.getUserAccountName())) {
                                queryWrapper.like(DcaBCopySciencepublish::getUserAccountName, dcaBCopySciencepublish.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopySciencepublish.getUserAccount())) {
                                queryWrapper.like(DcaBCopySciencepublish::getUserAccount, dcaBCopySciencepublish.getUserAccount());
                                }

        Page<DcaBCopySciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopySciencepublish> findDcaBCopySciencepublishList (QueryRequest request, DcaBCopySciencepublish dcaBCopySciencepublish){
        try{
        Page<DcaBCopySciencepublish> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopySciencepublish(page,dcaBCopySciencepublish);
        }catch(Exception e){
        log.error("获取任现职以来发表的论文、出版著作失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopySciencepublish(DcaBCopySciencepublish dcaBCopySciencepublish){
                dcaBCopySciencepublish.setId(UUID.randomUUID().toString());
        dcaBCopySciencepublish.setCreateTime(new Date());
        dcaBCopySciencepublish.setIsDeletemark(1);
        this.save(dcaBCopySciencepublish);
        }

@Override
@Transactional
public void updateDcaBCopySciencepublish(DcaBCopySciencepublish dcaBCopySciencepublish){
        dcaBCopySciencepublish.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopySciencepublish(dcaBCopySciencepublish);
        }

@Override
@Transactional
public void deleteDcaBCopySciencepublishs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopySciencepublish> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopySciencepublish> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopySciencepublish::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopySciencepublish::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }