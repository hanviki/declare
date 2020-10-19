package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBTitleapplyD;
import cc.mrbird.febs.dca.dao.DcaBTitleapplyDMapper;
import cc.mrbird.febs.dca.service.IDcaBTitleapplyDService;
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
 * @since 2020-09-22
 */
@Slf4j
@Service("IDcaBTitleapplyDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTitleapplyDServiceImpl extends ServiceImpl<DcaBTitleapplyDMapper, DcaBTitleapplyD> implements IDcaBTitleapplyDService {


@Override
public IPage<DcaBTitleapplyD> findDcaBTitleapplyDs(QueryRequest request, DcaBTitleapplyD dcaBTitleapplyD){
        try{
        LambdaQueryWrapper<DcaBTitleapplyD> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBTitleapplyD::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBTitleapplyD.getUserAccount())) {
                                queryWrapper.like(DcaBTitleapplyD::getUserAccount, dcaBTitleapplyD.getUserAccount());
                                }
                                if (dcaBTitleapplyD.getState()!=null) {
                                queryWrapper.eq(DcaBTitleapplyD::getState, dcaBTitleapplyD.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBTitleapplyD.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTitleapplyD.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBTitleapplyD::getCreateTime, dcaBTitleapplyD.getCreateTimeFrom())
                                .le(DcaBTitleapplyD::getCreateTime, dcaBTitleapplyD.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBTitleapplyD.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTitleapplyD.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBTitleapplyD::getModifyTime, dcaBTitleapplyD.getModifyTimeFrom())
                                .le(DcaBTitleapplyD::getModifyTime, dcaBTitleapplyD.getModifyTimeTo());
                                }

        Page<DcaBTitleapplyD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBTitleapplyD> findDcaBTitleapplyDList (QueryRequest request, DcaBTitleapplyD dcaBTitleapplyD){
        try{
        Page<DcaBTitleapplyD> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBTitleapplyD(page,dcaBTitleapplyD);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBTitleapplyD(DcaBTitleapplyD dcaBTitleapplyD){
                dcaBTitleapplyD.setId(UUID.randomUUID().toString());
        dcaBTitleapplyD.setCreateTime(new Date());
        dcaBTitleapplyD.setIsDeletemark(1);
        this.save(dcaBTitleapplyD);
        }

@Override
@Transactional
public void updateDcaBTitleapplyD(DcaBTitleapplyD dcaBTitleapplyD){
        dcaBTitleapplyD.setModifyTime(new Date());
        this.baseMapper.updateDcaBTitleapplyD(dcaBTitleapplyD);
        }

@Override
@Transactional
public void deleteDcaBTitleapplyDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }