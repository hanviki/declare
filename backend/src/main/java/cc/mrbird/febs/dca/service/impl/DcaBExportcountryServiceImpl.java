package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBExportcountry;
import cc.mrbird.febs.dca.dao.DcaBExportcountryMapper;
import cc.mrbird.febs.dca.service.IDcaBExportcountryService;
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
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import org.springframework.beans.factory.annotation.Autowired;

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
 * @since 2020-10-22
 */
@Slf4j
@Service("IDcaBExportcountryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBExportcountryServiceImpl extends ServiceImpl<DcaBExportcountryMapper, DcaBExportcountry> implements IDcaBExportcountryService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;

@Override
public IPage<DcaBExportcountry> findDcaBExportcountrys(QueryRequest request, DcaBExportcountry dcaBExportcountry){
        try{
        LambdaQueryWrapper<DcaBExportcountry> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBExportcountry::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBExportcountry.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBExportcountry::getUserAccount, dcaBExportcountry.getUserAccount()).or().like(DcaBExportcountry::getUserAccountName, dcaBExportcountry.getUserAccount()));
            }

            if(StringUtils.isNotBlank(dcaBExportcountry.getAuditManName())){// 年度 和高级、中级、初级
                List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBExportcountry.getAuditMan(),dcaBExportcountry.getAuditManName());
                if(userAccountsList.size()==0){
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBExportcountry::getUserAccount,userAccountsList);
            }
                                if (dcaBExportcountry.getState()!=null) {
                                queryWrapper.eq(DcaBExportcountry::getState, dcaBExportcountry.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBExportcountry.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBExportcountry.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBExportcountry::getCreateTime, dcaBExportcountry.getCreateTimeFrom())
                                .le(DcaBExportcountry::getCreateTime, dcaBExportcountry.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBExportcountry.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBExportcountry.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBExportcountry::getModifyTime, dcaBExportcountry.getModifyTimeFrom())
                                .le(DcaBExportcountry::getModifyTime, dcaBExportcountry.getModifyTimeTo());
                                }

        Page<DcaBExportcountry> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBExportcountry> findDcaBExportcountryList (QueryRequest request, DcaBExportcountry dcaBExportcountry){
        try{
        Page<DcaBExportcountry> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBExportcountry(page,dcaBExportcountry);
        }catch(Exception e){
        log.error("获取出国情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBExportcountry(DcaBExportcountry dcaBExportcountry){
                dcaBExportcountry.setId(UUID.randomUUID().toString());
        dcaBExportcountry.setCreateTime(new Date());
        dcaBExportcountry.setIsDeletemark(1);
        this.save(dcaBExportcountry);
        }

@Override
@Transactional
public void updateDcaBExportcountry(DcaBExportcountry dcaBExportcountry){
        dcaBExportcountry.setModifyTime(new Date());
        this.baseMapper.updateDcaBExportcountry(dcaBExportcountry);
        }

@Override
@Transactional
public void deleteDcaBExportcountrys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }