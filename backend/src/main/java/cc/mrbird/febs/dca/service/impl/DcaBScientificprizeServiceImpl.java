package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBScientificprize;
import cc.mrbird.febs.dca.dao.DcaBScientificprizeMapper;
import cc.mrbird.febs.dca.service.IDcaBScientificprizeService;
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
 * 自任职以来科研获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-11
 */
@Slf4j
@Service("IDcaBScientificprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBScientificprizeServiceImpl extends ServiceImpl<DcaBScientificprizeMapper, DcaBScientificprize> implements IDcaBScientificprizeService {


@Override
public IPage<DcaBScientificprize> findDcaBScientificprizes(QueryRequest request, DcaBScientificprize dcaBScientificprize){
        try{
        LambdaQueryWrapper<DcaBScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBScientificprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBScientificprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBScientificprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBScientificprize::getCreateTime, dcaBScientificprize.getCreateTimeFrom())
                                .le(DcaBScientificprize::getCreateTime, dcaBScientificprize.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBScientificprize.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBScientificprize.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBScientificprize::getModifyTime, dcaBScientificprize.getModifyTimeFrom())
                                .le(DcaBScientificprize::getModifyTime, dcaBScientificprize.getModifyTimeTo());
                                }

        Page<DcaBScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBScientificprize> findDcaBScientificprizeList (QueryRequest request, DcaBScientificprize dcaBScientificprize){
        try{
        Page<DcaBScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBScientificprize(page,dcaBScientificprize);
        }catch(Exception e){
        log.error("获取自任职以来科研获奖情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBScientificprize(DcaBScientificprize dcaBScientificprize){
                dcaBScientificprize.setId(UUID.randomUUID().toString());
        dcaBScientificprize.setCreateTime(new Date());
        dcaBScientificprize.setIsDeletemark(1);
        this.save(dcaBScientificprize);
        }

@Override
@Transactional
public void updateDcaBScientificprize(DcaBScientificprize dcaBScientificprize){
        dcaBScientificprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBScientificprize(dcaBScientificprize);
        }

@Override
@Transactional
public void deleteDcaBScientificprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }