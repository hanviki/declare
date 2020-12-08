package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTeacherqualify;
import cc.mrbird.febs.dca.dao.DcaBTeacherqualifyMapper;
import cc.mrbird.febs.dca.service.IDcaBTeacherqualifyService;
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
 * 教师资格 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBTeacherqualifyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTeacherqualifyServiceImpl extends ServiceImpl<DcaBTeacherqualifyMapper, DcaBTeacherqualify> implements IDcaBTeacherqualifyService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBTeacherqualify> findDcaBTeacherqualifys(QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify){
        try{
        LambdaQueryWrapper<DcaBTeacherqualify> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTeacherqualify::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBTeacherqualify.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBTeacherqualify::getUserAccount, dcaBTeacherqualify.getUserAccount()).or()
                        .like(DcaBTeacherqualify::getUserAccountName, dcaBTeacherqualify.getUserAccount()));

            }
            if(StringUtils.isNotBlank(dcaBTeacherqualify.getAuditManName())){// 年度 和高级、中级、初级
                List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBTeacherqualify.getAuditMan(),dcaBTeacherqualify.getAuditManName());
                if(userAccountsList.size()==0){
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBTeacherqualify::getUserAccount,userAccountsList);
            }
                                if (dcaBTeacherqualify.getState()!=null) {
                                queryWrapper.eq(DcaBTeacherqualify::getState, dcaBTeacherqualify.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBTeacherqualify.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTeacherqualify.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeacherqualify::getCreateTime, dcaBTeacherqualify.getCreateTimeFrom())
                                .le(DcaBTeacherqualify::getCreateTime, dcaBTeacherqualify.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTeacherqualify.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTeacherqualify.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeacherqualify::getModifyTime, dcaBTeacherqualify.getModifyTimeFrom())
                                .le(DcaBTeacherqualify::getModifyTime, dcaBTeacherqualify.getModifyTimeTo());
                                }

        Page<DcaBTeacherqualify> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTeacherqualify> findDcaBTeacherqualifyList (QueryRequest request, DcaBTeacherqualify dcaBTeacherqualify){
        try{
        Page<DcaBTeacherqualify> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTeacherqualify(page,dcaBTeacherqualify);
        }catch(Exception e){
        log.error("获取教师资格失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTeacherqualify(DcaBTeacherqualify dcaBTeacherqualify){
                dcaBTeacherqualify.setId(UUID.randomUUID().toString());
        dcaBTeacherqualify.setCreateTime(new Date());
        dcaBTeacherqualify.setIsDeletemark(1);
        this.save(dcaBTeacherqualify);
        }

@Override
@Transactional
public void updateDcaBTeacherqualify(DcaBTeacherqualify dcaBTeacherqualify){
        dcaBTeacherqualify.setModifyTime(new Date());
        this.baseMapper.updateDcaBTeacherqualify(dcaBTeacherqualify);
        }

@Override
@Transactional
public void deleteDcaBTeacherqualifys(String[]Ids){
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