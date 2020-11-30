package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacherprize;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyTeacherprizeMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacherprizeService;
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
 * 任现职以来教学获奖 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyTeacherprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyTeacherprizeServiceImpl extends ServiceImpl<DcaBCopyTeacherprizeMapper, DcaBCopyTeacherprize> implements IDcaBCopyTeacherprizeService {


@Override
public IPage<DcaBCopyTeacherprize> findDcaBCopyTeacherprizes(QueryRequest request, DcaBCopyTeacherprize dcaBCopyTeacherprize){
        try{
        LambdaQueryWrapper<DcaBCopyTeacherprize> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyTeacherprize::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyTeacherprize.getDcaYear())) {
                                queryWrapper.like(DcaBCopyTeacherprize::getDcaYear, dcaBCopyTeacherprize.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTeacherprize.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyTeacherprize::getUserAccountName, dcaBCopyTeacherprize.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyTeacherprize.getUserAccount())) {
                                queryWrapper.like(DcaBCopyTeacherprize::getUserAccount, dcaBCopyTeacherprize.getUserAccount());
                                }

        Page<DcaBCopyTeacherprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyTeacherprize> findDcaBCopyTeacherprizeList (QueryRequest request, DcaBCopyTeacherprize dcaBCopyTeacherprize){
        try{
        Page<DcaBCopyTeacherprize> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyTeacherprize(page,dcaBCopyTeacherprize);
        }catch(Exception e){
        log.error("获取任现职以来教学获奖失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyTeacherprize(DcaBCopyTeacherprize dcaBCopyTeacherprize){
                dcaBCopyTeacherprize.setId(UUID.randomUUID().toString());
        dcaBCopyTeacherprize.setCreateTime(new Date());
        dcaBCopyTeacherprize.setIsDeletemark(1);
        this.save(dcaBCopyTeacherprize);
        }

@Override
@Transactional
public void updateDcaBCopyTeacherprize(DcaBCopyTeacherprize dcaBCopyTeacherprize){
        dcaBCopyTeacherprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyTeacherprize(dcaBCopyTeacherprize);
        }

@Override
@Transactional
public void deleteDcaBCopyTeacherprizes(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyTeacherprize> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyTeacherprize> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyTeacherprize::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyTeacherprize::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }