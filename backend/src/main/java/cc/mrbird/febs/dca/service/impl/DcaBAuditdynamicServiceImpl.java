package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAuditdynamic;
import cc.mrbird.febs.dca.dao.DcaBAuditdynamicMapper;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
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
 * @since 2020-10-27
 */
@Slf4j
@Service("IDcaBAuditdynamicService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAuditdynamicServiceImpl extends ServiceImpl<DcaBAuditdynamicMapper, DcaBAuditdynamic> implements IDcaBAuditdynamicService {


@Override
public IPage<DcaBAuditdynamic> findDcaBAuditdynamics(QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic){
        try{
        LambdaQueryWrapper<DcaBAuditdynamic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAuditdynamic::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBAuditdynamic.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBAuditdynamic::getUserAccount, dcaBAuditdynamic.getUserAccount()).or().like(DcaBAuditdynamic::getUserAccountName, dcaBAuditdynamic.getUserAccount()));
            }
                                if (dcaBAuditdynamic.getState()!=null) {
                                queryWrapper.eq(DcaBAuditdynamic::getState, dcaBAuditdynamic.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBAuditdynamic.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAuditdynamic.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBAuditdynamic::getCreateTime, dcaBAuditdynamic.getCreateTimeFrom())
                                .le(DcaBAuditdynamic::getCreateTime, dcaBAuditdynamic.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBAuditdynamic.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBAuditdynamic.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBAuditdynamic::getModifyTime, dcaBAuditdynamic.getModifyTimeFrom())
                                .le(DcaBAuditdynamic::getModifyTime, dcaBAuditdynamic.getModifyTimeTo());
                                }

        Page<DcaBAuditdynamic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBAuditdynamic> findDcaBAuditdynamicList (QueryRequest request, DcaBAuditdynamic dcaBAuditdynamic){
        try{
        Page<DcaBAuditdynamic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBAuditdynamic(page,dcaBAuditdynamic);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBAuditdynamic(DcaBAuditdynamic dcaBAuditdynamic){
                dcaBAuditdynamic.setId(UUID.randomUUID().toString());
        dcaBAuditdynamic.setCreateTime(new Date());
        dcaBAuditdynamic.setIsDeletemark(1);
        this.save(dcaBAuditdynamic);
        }

@Override
@Transactional
public void updateDcaBAuditdynamic(DcaBAuditdynamic dcaBAuditdynamic){
        dcaBAuditdynamic.setModifyTime(new Date());
        this.baseMapper.updateDcaBAuditdynamic(dcaBAuditdynamic);
        }

@Override
@Transactional
public void deleteDcaBAuditdynamics(String[]Ids){
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

    @Override
    @Transactional
    public  List<DcaBAuditdynamic> findAllAuditdynamics(String userAccount){
       return  this.baseMapper.getAllByUserAccount(userAccount);
    }
        }