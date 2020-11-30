package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPublicarticle;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyPublicarticleMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyPublicarticleService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyPublicarticleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyPublicarticleServiceImpl extends ServiceImpl<DcaBCopyPublicarticleMapper, DcaBCopyPublicarticle> implements IDcaBCopyPublicarticleService {


@Override
public IPage<DcaBCopyPublicarticle> findDcaBCopyPublicarticles(QueryRequest request, DcaBCopyPublicarticle dcaBCopyPublicarticle){
        try{
        LambdaQueryWrapper<DcaBCopyPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyPublicarticle::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyPublicarticle.getDcaYear())) {
                                queryWrapper.like(DcaBCopyPublicarticle::getDcaYear, dcaBCopyPublicarticle.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPublicarticle.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyPublicarticle::getUserAccountName, dcaBCopyPublicarticle.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPublicarticle.getUserAccount())) {
                                queryWrapper.like(DcaBCopyPublicarticle::getUserAccount, dcaBCopyPublicarticle.getUserAccount());
                                }

        Page<DcaBCopyPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyPublicarticle> findDcaBCopyPublicarticleList (QueryRequest request, DcaBCopyPublicarticle dcaBCopyPublicarticle){
        try{
        Page<DcaBCopyPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyPublicarticle(page,dcaBCopyPublicarticle);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyPublicarticle(DcaBCopyPublicarticle dcaBCopyPublicarticle){
                dcaBCopyPublicarticle.setId(UUID.randomUUID().toString());
        dcaBCopyPublicarticle.setCreateTime(new Date());
        dcaBCopyPublicarticle.setIsDeletemark(1);
        this.save(dcaBCopyPublicarticle);
        }

@Override
@Transactional
public void updateDcaBCopyPublicarticle(DcaBCopyPublicarticle dcaBCopyPublicarticle){
        dcaBCopyPublicarticle.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyPublicarticle(dcaBCopyPublicarticle);
        }

@Override
@Transactional
public void deleteDcaBCopyPublicarticles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyPublicarticle> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyPublicarticle::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyPublicarticle::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }