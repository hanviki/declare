package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBWorknum;
import cc.mrbird.febs.dca.dao.DcaBWorknumMapper;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import cc.mrbird.febs.dca.service.IDcaBWorknumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 近三年业务工作量 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-28
 */
@Slf4j
@Service("IDcaBWorknumService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBWorknumServiceImpl extends ServiceImpl<DcaBWorknumMapper, DcaBWorknum> implements IDcaBWorknumService {

        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBWorknum> findDcaBWorknums(QueryRequest request, DcaBWorknum dcaBWorknum){
        try{
        LambdaQueryWrapper<DcaBWorknum> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBWorknum::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBWorknum.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBWorknum::getUserAccount, dcaBWorknum.getUserAccount()).or()
        .like(DcaBWorknum::getUserAccountName, dcaBWorknum.getUserAccount()));

        }
        if (dcaBWorknum.getState()!=null) {
        queryWrapper.eq(DcaBWorknum::getState, dcaBWorknum.getState());
        }
       /** if (dcaBWorknum.getAuditState()!=null && (dcaBWorknum.getAuditState()>=0)) {
        queryWrapper.eq(DcaBWorknum::getAuditState, dcaBWorknum.getAuditState());
        }*/
                if(StringUtils.isNotBlank(dcaBWorknum.getAuditManName())){// 年度 和高级、中级、初级
                        List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBWorknum.getAuditMan(),dcaBWorknum.getAuditManName());
                        if(userAccountsList.size()==0){
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBWorknum::getUserAccount,userAccountsList);
                }
                                if (StringUtils.isNotBlank(dcaBWorknum.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBWorknum.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBWorknum::getCreateTime, dcaBWorknum.getCreateTimeFrom())
                                .le(DcaBWorknum::getCreateTime, dcaBWorknum.getCreateTimeTo());
                                }

        Page<DcaBWorknum> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBWorknum> findDcaBWorknumList (QueryRequest request, DcaBWorknum dcaBWorknum){
        try{
        Page<DcaBWorknum> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBWorknum(page,dcaBWorknum);
        }catch(Exception e){
        log.error("获取近三年业务工作量失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBWorknum(DcaBWorknum dcaBWorknum){
                dcaBWorknum.setId(UUID.randomUUID().toString());
        dcaBWorknum.setCreateTime(new Date());
        dcaBWorknum.setIsDeletemark(1);
        this.save(dcaBWorknum);
        }

@Override
@Transactional
public void updateDcaBWorknum(DcaBWorknum dcaBWorknum){
        dcaBWorknum.setModifyTime(new Date());
        this.baseMapper.updateDcaBWorknum(dcaBWorknum);
        }

@Override
@Transactional
public void deleteDcaBWorknums(String[]Ids){
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