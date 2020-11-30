package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyExportcountry;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyExportcountryMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyExportcountryService;
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
 * 出国情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyExportcountryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyExportcountryServiceImpl extends ServiceImpl<DcaBCopyExportcountryMapper, DcaBCopyExportcountry> implements IDcaBCopyExportcountryService {


@Override
public IPage<DcaBCopyExportcountry> findDcaBCopyExportcountrys(QueryRequest request, DcaBCopyExportcountry dcaBCopyExportcountry){
        try{
        LambdaQueryWrapper<DcaBCopyExportcountry> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyExportcountry::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyExportcountry.getDcaYear())) {
                                queryWrapper.like(DcaBCopyExportcountry::getDcaYear, dcaBCopyExportcountry.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyExportcountry.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyExportcountry::getUserAccountName, dcaBCopyExportcountry.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyExportcountry.getUserAccount())) {
                                queryWrapper.like(DcaBCopyExportcountry::getUserAccount, dcaBCopyExportcountry.getUserAccount());
                                }

        Page<DcaBCopyExportcountry> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyExportcountry> findDcaBCopyExportcountryList (QueryRequest request, DcaBCopyExportcountry dcaBCopyExportcountry){
        try{
        Page<DcaBCopyExportcountry> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyExportcountry(page,dcaBCopyExportcountry);
        }catch(Exception e){
        log.error("获取出国情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyExportcountry(DcaBCopyExportcountry dcaBCopyExportcountry){
                dcaBCopyExportcountry.setId(UUID.randomUUID().toString());
        dcaBCopyExportcountry.setCreateTime(new Date());
        dcaBCopyExportcountry.setIsDeletemark(1);
        this.save(dcaBCopyExportcountry);
        }

@Override
@Transactional
public void updateDcaBCopyExportcountry(DcaBCopyExportcountry dcaBCopyExportcountry){
        dcaBCopyExportcountry.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyExportcountry(dcaBCopyExportcountry);
        }

@Override
@Transactional
public void deleteDcaBCopyExportcountrys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyExportcountry> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyExportcountry> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyExportcountry::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyExportcountry::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }