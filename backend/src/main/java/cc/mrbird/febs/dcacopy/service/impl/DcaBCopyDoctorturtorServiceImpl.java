package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyDoctorturtor;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyDoctorturtorMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyDoctorturtorService;
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
 * 担任博导硕导 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBCopyDoctorturtorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyDoctorturtorServiceImpl extends ServiceImpl<DcaBCopyDoctorturtorMapper, DcaBCopyDoctorturtor> implements IDcaBCopyDoctorturtorService {


@Override
public IPage<DcaBCopyDoctorturtor> findDcaBCopyDoctorturtors(QueryRequest request, DcaBCopyDoctorturtor dcaBCopyDoctorturtor){
        try{
        LambdaQueryWrapper<DcaBCopyDoctorturtor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyDoctorturtor::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyDoctorturtor.getDcaYear())) {
                                queryWrapper.like(DcaBCopyDoctorturtor::getDcaYear, dcaBCopyDoctorturtor.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyDoctorturtor.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyDoctorturtor::getUserAccountName, dcaBCopyDoctorturtor.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyDoctorturtor.getUserAccount())) {
                                queryWrapper.like(DcaBCopyDoctorturtor::getUserAccount, dcaBCopyDoctorturtor.getUserAccount());
                                }

        Page<DcaBCopyDoctorturtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyDoctorturtor> findDcaBCopyDoctorturtorList (QueryRequest request, DcaBCopyDoctorturtor dcaBCopyDoctorturtor){
        try{
        Page<DcaBCopyDoctorturtor> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyDoctorturtor(page,dcaBCopyDoctorturtor);
        }catch(Exception e){
        log.error("获取担任博导硕导失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyDoctorturtor(DcaBCopyDoctorturtor dcaBCopyDoctorturtor){
                dcaBCopyDoctorturtor.setId(UUID.randomUUID().toString());
        dcaBCopyDoctorturtor.setCreateTime(new Date());
        dcaBCopyDoctorturtor.setIsDeletemark(1);
        this.save(dcaBCopyDoctorturtor);
        }

@Override
@Transactional
public void updateDcaBCopyDoctorturtor(DcaBCopyDoctorturtor dcaBCopyDoctorturtor){
        dcaBCopyDoctorturtor.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyDoctorturtor(dcaBCopyDoctorturtor);
        }

@Override
@Transactional
public void deleteDcaBCopyDoctorturtors(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyDoctorturtor> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyDoctorturtor> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyDoctorturtor::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyDoctorturtor::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }