package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocExportcountry;
import cc.mrbird.febs.doctor.dao.DcaBDocExportcountryMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocExportcountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.dynamic.datasource.annotation.DS;
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
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocExportcountryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocExportcountryServiceImpl extends ServiceImpl<DcaBDocExportcountryMapper, DcaBDocExportcountry> implements IDcaBDocExportcountryService {


@Override
@DS("slave")
public IPage<DcaBDocExportcountry> findDcaBDocExportcountrys(QueryRequest request, DcaBDocExportcountry dcaBDocExportcountry){
        try{
        LambdaQueryWrapper<DcaBDocExportcountry> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocExportcountry::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocExportcountry.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocExportcountry::getUserAccount, dcaBDocExportcountry.getUserAccount()).or()
        .like(DcaBDocExportcountry::getUserAccountName, dcaBDocExportcountry.getUserAccount()));

        }
        if (dcaBDocExportcountry.getState()!=null) {
        queryWrapper.eq(DcaBDocExportcountry::getState, dcaBDocExportcountry.getState());
        }
       /** if (dcaBDocExportcountry.getAuditState()!=null && (dcaBDocExportcountry.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocExportcountry::getAuditState, dcaBDocExportcountry.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocExportcountry.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocExportcountry.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocExportcountry::getCreateTime, dcaBDocExportcountry.getCreateTimeFrom())
                                .le(DcaBDocExportcountry::getCreateTime, dcaBDocExportcountry.getCreateTimeTo());
                                }

        Page<DcaBDocExportcountry> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocExportcountry> findDcaBDocExportcountryList (QueryRequest request, DcaBDocExportcountry dcaBDocExportcountry){
        try{
        Page<DcaBDocExportcountry> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocExportcountry(page,dcaBDocExportcountry);
        }catch(Exception e){
        log.error("获取出国情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocExportcountry(DcaBDocExportcountry dcaBDocExportcountry){
                dcaBDocExportcountry.setId(UUID.randomUUID().toString());
        dcaBDocExportcountry.setCreateTime(new Date());
        dcaBDocExportcountry.setIsDeletemark(1);
        this.save(dcaBDocExportcountry);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocExportcountry(DcaBDocExportcountry dcaBDocExportcountry){
        dcaBDocExportcountry.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocExportcountry(dcaBDocExportcountry);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocExportcountrys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
@DS("slave")
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }