package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTeacherprize;
import cc.mrbird.febs.dca.dao.DcaBTeacherprizeMapper;
import cc.mrbird.febs.dca.service.IDcaBTeacherprizeService;
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
 * 任现职以来教学获奖 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
@Slf4j
@Service("IDcaBTeacherprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTeacherprizeServiceImpl extends ServiceImpl<DcaBTeacherprizeMapper, DcaBTeacherprize> implements IDcaBTeacherprizeService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBTeacherprize> findDcaBTeacherprizes(QueryRequest request, DcaBTeacherprize dcaBTeacherprize){
        try{
        LambdaQueryWrapper<DcaBTeacherprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTeacherprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBTeacherprize.getUserAccount())) {
                                queryWrapper.like(DcaBTeacherprize::getUserAccount, dcaBTeacherprize.getUserAccount());
                                }

            if(StringUtils.isNotBlank(dcaBTeacherprize.getAuditManName())){// 年度 和高级、中级、初级
                List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBTeacherprize.getAuditMan(),dcaBTeacherprize.getAuditManName());
                if(userAccountsList.size()==0){
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBTeacherprize::getUserAccount,userAccountsList);
            }
                                if (dcaBTeacherprize.getState()!=null) {
                                queryWrapper.eq(DcaBTeacherprize::getState, dcaBTeacherprize.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBTeacherprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTeacherprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeacherprize::getCreateTime, dcaBTeacherprize.getCreateTimeFrom())
                                .le(DcaBTeacherprize::getCreateTime, dcaBTeacherprize.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTeacherprize.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTeacherprize.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTeacherprize::getModifyTime, dcaBTeacherprize.getModifyTimeFrom())
                                .le(DcaBTeacherprize::getModifyTime, dcaBTeacherprize.getModifyTimeTo());
                                }

        Page<DcaBTeacherprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTeacherprize> findDcaBTeacherprizeList (QueryRequest request, DcaBTeacherprize dcaBTeacherprize){
        try{
        Page<DcaBTeacherprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTeacherprize(page,dcaBTeacherprize);
        }catch(Exception e){
        log.error("获取任现职以来教学获奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTeacherprize(DcaBTeacherprize dcaBTeacherprize){
                dcaBTeacherprize.setId(UUID.randomUUID().toString());
        dcaBTeacherprize.setCreateTime(new Date());
        dcaBTeacherprize.setIsDeletemark(1);
        this.save(dcaBTeacherprize);
        }

@Override
@Transactional
public void updateDcaBTeacherprize(DcaBTeacherprize dcaBTeacherprize){
        dcaBTeacherprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBTeacherprize(dcaBTeacherprize);
        }

@Override
@Transactional
public void deleteDcaBTeacherprizes(String[]Ids){
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