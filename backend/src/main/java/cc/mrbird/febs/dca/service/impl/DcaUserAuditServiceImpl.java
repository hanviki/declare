package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaUserAudit;
import cc.mrbird.febs.dca.dao.DcaUserAuditMapper;
import cc.mrbird.febs.dca.service.IDcaUserAuditService;
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
 * 用户审核字段表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */
@Slf4j
@Service("IDcaUserAuditService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaUserAuditServiceImpl extends ServiceImpl<DcaUserAuditMapper, DcaUserAudit> implements IDcaUserAuditService {


@Override
public IPage<DcaUserAudit> findDcaUserAudits(QueryRequest request, DcaUserAudit dcaUserAudit){
        try{
        LambdaQueryWrapper<DcaUserAudit> queryWrapper=new LambdaQueryWrapper<>();



        Page<DcaUserAudit> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaUserAudit> findDcaUserAuditList (QueryRequest request, DcaUserAudit dcaUserAudit){
        try{
        Page<DcaUserAudit> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaUserAudit(page,dcaUserAudit);
        }catch(Exception e){
        log.error("获取用户审核字段表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaUserAudit(DcaUserAudit dcaUserAudit){

        this.save(dcaUserAudit);
        }

@Override
@Transactional
public void updateDcaUserAudit(DcaUserAudit dcaUserAudit){

        this.baseMapper.updateDcaUserAudit(dcaUserAudit);
        }

@Override
@Transactional
public void deleteDcaUserAudits(String[]Ids){
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