package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocScientificprize;
import cc.mrbird.febs.doctor.dao.DcaBDocScientificprizeMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocScientificprizeService;
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
 * 自任职以来科研获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocScientificprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocScientificprizeServiceImpl extends ServiceImpl<DcaBDocScientificprizeMapper, DcaBDocScientificprize> implements IDcaBDocScientificprizeService {


@Override
@DS("slave")
public IPage<DcaBDocScientificprize> findDcaBDocScientificprizes(QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize){
        try{
        LambdaQueryWrapper<DcaBDocScientificprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocScientificprize::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocScientificprize.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocScientificprize::getUserAccount, dcaBDocScientificprize.getUserAccount()).or()
        .like(DcaBDocScientificprize::getUserAccountName, dcaBDocScientificprize.getUserAccount()));

        }
        if (dcaBDocScientificprize.getState()!=null) {
        queryWrapper.eq(DcaBDocScientificprize::getState, dcaBDocScientificprize.getState());
        }
       /** if (dcaBDocScientificprize.getAuditState()!=null && (dcaBDocScientificprize.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocScientificprize::getAuditState, dcaBDocScientificprize.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocScientificprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocScientificprize.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocScientificprize::getCreateTime, dcaBDocScientificprize.getCreateTimeFrom())
                                .le(DcaBDocScientificprize::getCreateTime, dcaBDocScientificprize.getCreateTimeTo());
                                }

        Page<DcaBDocScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocScientificprize> findDcaBDocScientificprizeList (QueryRequest request, DcaBDocScientificprize dcaBDocScientificprize){
        try{
        Page<DcaBDocScientificprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocScientificprize(page,dcaBDocScientificprize);
        }catch(Exception e){
        log.error("获取自任职以来科研获奖情况失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocScientificprize(DcaBDocScientificprize dcaBDocScientificprize){
                dcaBDocScientificprize.setId(UUID.randomUUID().toString());
        dcaBDocScientificprize.setCreateTime(new Date());
        dcaBDocScientificprize.setIsDeletemark(1);
        this.save(dcaBDocScientificprize);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocScientificprize(DcaBDocScientificprize dcaBDocScientificprize){
        dcaBDocScientificprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocScientificprize(dcaBDocScientificprize);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocScientificprizes(String[]Ids){
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