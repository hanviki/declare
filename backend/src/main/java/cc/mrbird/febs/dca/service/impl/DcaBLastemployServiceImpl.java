package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBLastemploy;
import cc.mrbird.febs.dca.dao.DcaBLastemployMapper;
import cc.mrbird.febs.dca.service.IDcaBLastemployService;
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
 * 完成上一聘期 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBLastemployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBLastemployServiceImpl extends ServiceImpl<DcaBLastemployMapper, DcaBLastemploy> implements IDcaBLastemployService {


@Override
public IPage<DcaBLastemploy> findDcaBLastemploys(QueryRequest request, DcaBLastemploy dcaBLastemploy){
        try{
        LambdaQueryWrapper<DcaBLastemploy> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBLastemploy::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBLastemploy.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBLastemploy::getUserAccount, dcaBLastemploy.getUserAccount()).or()
                        .like(DcaBLastemploy::getUserAccountName, dcaBLastemploy.getUserAccount()));

            }
                                if (dcaBLastemploy.getState()!=null) {
                                queryWrapper.eq(DcaBLastemploy::getState, dcaBLastemploy.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBLastemploy.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBLastemploy.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBLastemploy::getCreateTime, dcaBLastemploy.getCreateTimeFrom())
                                .le(DcaBLastemploy::getCreateTime, dcaBLastemploy.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBLastemploy.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBLastemploy.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBLastemploy::getModifyTime, dcaBLastemploy.getModifyTimeFrom())
                                .le(DcaBLastemploy::getModifyTime, dcaBLastemploy.getModifyTimeTo());
                                }

        Page<DcaBLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBLastemploy> findDcaBLastemployList (QueryRequest request, DcaBLastemploy dcaBLastemploy){
        try{
        Page<DcaBLastemploy> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBLastemploy(page,dcaBLastemploy);
        }catch(Exception e){
        log.error("获取完成上一聘期失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBLastemploy(DcaBLastemploy dcaBLastemploy){
                dcaBLastemploy.setId(UUID.randomUUID().toString());
        dcaBLastemploy.setCreateTime(new Date());
        dcaBLastemploy.setIsDeletemark(1);
        this.save(dcaBLastemploy);
        }

@Override
@Transactional
public void updateDcaBLastemploy(DcaBLastemploy dcaBLastemploy){
        dcaBLastemploy.setModifyTime(new Date());
        this.baseMapper.updateDcaBLastemploy(dcaBLastemploy);
        }

@Override
@Transactional
public void deleteDcaBLastemploys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }