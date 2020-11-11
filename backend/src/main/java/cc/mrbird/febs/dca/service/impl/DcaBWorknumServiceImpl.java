package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBWorknum;
import cc.mrbird.febs.dca.dao.DcaBWorknumMapper;
import cc.mrbird.febs.dca.service.IDcaBWorknumService;
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
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBWorknumService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBWorknumServiceImpl extends ServiceImpl<DcaBWorknumMapper, DcaBWorknum> implements IDcaBWorknumService {


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
                                if (StringUtils.isNotBlank(dcaBWorknum.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBWorknum.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBWorknum::getCreateTime, dcaBWorknum.getCreateTimeFrom())
                                .le(DcaBWorknum::getCreateTime, dcaBWorknum.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBWorknum.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBWorknum.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBWorknum::getModifyTime, dcaBWorknum.getModifyTimeFrom())
                                .le(DcaBWorknum::getModifyTime, dcaBWorknum.getModifyTimeTo());
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
        log.error("获取失败" ,e);
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