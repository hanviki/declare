package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBDoctorturtor;
import cc.mrbird.febs.dca.dao.DcaBDoctorturtorMapper;
import cc.mrbird.febs.dca.service.IDcaBDoctorturtorService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
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
 * 担任博导硕导 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBDoctorturtorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDoctorturtorServiceImpl extends ServiceImpl<DcaBDoctorturtorMapper, DcaBDoctorturtor> implements IDcaBDoctorturtorService {


        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBDoctorturtor> findDcaBDoctorturtors(QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor){
        try{
        LambdaQueryWrapper<DcaBDoctorturtor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDoctorturtor::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDoctorturtor.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDoctorturtor::getUserAccount, dcaBDoctorturtor.getUserAccount()).or()
        .like(DcaBDoctorturtor::getUserAccountName, dcaBDoctorturtor.getUserAccount()));

        }
        if (dcaBDoctorturtor.getState()!=null) {
        queryWrapper.eq(DcaBDoctorturtor::getState, dcaBDoctorturtor.getState());
        }
       /** if (dcaBDoctorturtor.getAuditState()!=null && (dcaBDoctorturtor.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDoctorturtor::getAuditState, dcaBDoctorturtor.getAuditState());
        }*/
                if(StringUtils.isNotBlank(dcaBDoctorturtor.getAuditManName())){// 年度 和高级、中级、初级
                        List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBDoctorturtor.getAuditMan(),dcaBDoctorturtor.getAuditManName());
                        if(userAccountsList.size()==0){
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBDoctorturtor::getUserAccount,userAccountsList);
                }
                                if (StringUtils.isNotBlank(dcaBDoctorturtor.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDoctorturtor.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDoctorturtor::getCreateTime, dcaBDoctorturtor.getCreateTimeFrom())
                                .le(DcaBDoctorturtor::getCreateTime, dcaBDoctorturtor.getCreateTimeTo());
                                }

        Page<DcaBDoctorturtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBDoctorturtor> findDcaBDoctorturtorList (QueryRequest request, DcaBDoctorturtor dcaBDoctorturtor){
        try{
        Page<DcaBDoctorturtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDoctorturtor(page,dcaBDoctorturtor);
        }catch(Exception e){
        log.error("获取担任博导硕导失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBDoctorturtor(DcaBDoctorturtor dcaBDoctorturtor){
                dcaBDoctorturtor.setId(UUID.randomUUID().toString());
        dcaBDoctorturtor.setCreateTime(new Date());
        dcaBDoctorturtor.setIsDeletemark(1);
        this.save(dcaBDoctorturtor);
        }

@Override
@Transactional
public void updateDcaBDoctorturtor(DcaBDoctorturtor dcaBDoctorturtor){
        dcaBDoctorturtor.setModifyTime(new Date());
        this.baseMapper.updateDcaBDoctorturtor(dcaBDoctorturtor);
        }

@Override
@Transactional
public void deleteDcaBDoctorturtors(String[]Ids){
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