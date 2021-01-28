package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocAttachfile;
import cc.mrbird.febs.doctor.dao.DcaBDocAttachfileMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocAttachfileService;
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
 * 其他附件 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocAttachfileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocAttachfileServiceImpl extends ServiceImpl<DcaBDocAttachfileMapper, DcaBDocAttachfile> implements IDcaBDocAttachfileService {


@Override
@DS("slave")
public IPage<DcaBDocAttachfile> findDcaBDocAttachfiles(QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile){
        try{
        LambdaQueryWrapper<DcaBDocAttachfile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocAttachfile::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocAttachfile.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocAttachfile::getUserAccount, dcaBDocAttachfile.getUserAccount()).or()
        .like(DcaBDocAttachfile::getUserAccountName, dcaBDocAttachfile.getUserAccount()));

        }
        if (dcaBDocAttachfile.getState()!=null) {
        queryWrapper.eq(DcaBDocAttachfile::getState, dcaBDocAttachfile.getState());
        }
       /** if (dcaBDocAttachfile.getAuditState()!=null && (dcaBDocAttachfile.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocAttachfile::getAuditState, dcaBDocAttachfile.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocAttachfile.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocAttachfile.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocAttachfile::getCreateTime, dcaBDocAttachfile.getCreateTimeFrom())
                                .le(DcaBDocAttachfile::getCreateTime, dcaBDocAttachfile.getCreateTimeTo());
                                }

        Page<DcaBDocAttachfile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocAttachfile> findDcaBDocAttachfileList (QueryRequest request, DcaBDocAttachfile dcaBDocAttachfile){
        try{
        Page<DcaBDocAttachfile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocAttachfile(page,dcaBDocAttachfile);
        }catch(Exception e){
        log.error("获取其他附件失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocAttachfile(DcaBDocAttachfile dcaBDocAttachfile){
                dcaBDocAttachfile.setId(UUID.randomUUID().toString());
        dcaBDocAttachfile.setCreateTime(new Date());
        dcaBDocAttachfile.setIsDeletemark(1);
        this.save(dcaBDocAttachfile);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocAttachfile(DcaBDocAttachfile dcaBDocAttachfile){
        dcaBDocAttachfile.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocAttachfile(dcaBDocAttachfile);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocAttachfiles(String[]Ids){
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