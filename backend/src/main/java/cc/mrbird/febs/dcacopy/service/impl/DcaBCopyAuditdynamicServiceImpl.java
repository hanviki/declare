package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAuditdynamic;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAuditdynamicMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAuditdynamicService;
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
 * @since 2020-11-23
 */
@Slf4j
@Service("IDcaBCopyAuditdynamicService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyAuditdynamicServiceImpl extends ServiceImpl<DcaBCopyAuditdynamicMapper, DcaBCopyAuditdynamic> implements IDcaBCopyAuditdynamicService {


@Override
public IPage<DcaBCopyAuditdynamic> findDcaBCopyAuditdynamics(QueryRequest request, DcaBCopyAuditdynamic dcaBCopyAuditdynamic){
        try{
        LambdaQueryWrapper<DcaBCopyAuditdynamic> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyAuditdynamic::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyAuditdynamic.getDcaYear())) {
                                queryWrapper.like(DcaBCopyAuditdynamic::getDcaYear, dcaBCopyAuditdynamic.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAuditdynamic.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyAuditdynamic::getUserAccountName, dcaBCopyAuditdynamic.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAuditdynamic.getUserAccount())) {
                                queryWrapper.like(DcaBCopyAuditdynamic::getUserAccount, dcaBCopyAuditdynamic.getUserAccount());
                                }

        Page<DcaBCopyAuditdynamic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyAuditdynamic> findDcaBCopyAuditdynamicList (QueryRequest request, DcaBCopyAuditdynamic dcaBCopyAuditdynamic){
        try{
        Page<DcaBCopyAuditdynamic> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyAuditdynamic(page,dcaBCopyAuditdynamic);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyAuditdynamic(DcaBCopyAuditdynamic dcaBCopyAuditdynamic){
                dcaBCopyAuditdynamic.setId(UUID.randomUUID().toString());
        dcaBCopyAuditdynamic.setCreateTime(new Date());
        dcaBCopyAuditdynamic.setIsDeletemark(1);
        this.save(dcaBCopyAuditdynamic);
        }

@Override
@Transactional
public void updateDcaBCopyAuditdynamic(DcaBCopyAuditdynamic dcaBCopyAuditdynamic){
        dcaBCopyAuditdynamic.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyAuditdynamic(dcaBCopyAuditdynamic);
        }

@Override
@Transactional
public void deleteDcaBCopyAuditdynamics(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
    @Override
    @Transactional
    public List<DcaBCopyAuditdynamic> GetAllInfo(String userAccount,String dcaYear){
      return  this.baseMapper.getAllByUserAccount(userAccount, dcaYear);
    }

    @Override
    @Transactional
    public String GetZtkhqk(String userAccount,String dcaYear){
        return  this.baseMapper.getZtkhqk(userAccount, dcaYear);
    }
        }