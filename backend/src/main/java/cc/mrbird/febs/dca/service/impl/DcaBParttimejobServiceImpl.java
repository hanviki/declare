package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBParttimejob;
import cc.mrbird.febs.dca.dao.DcaBParttimejobMapper;
import cc.mrbird.febs.dca.service.IDcaBParttimejobService;
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
 * 社会兼职表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-04
 */
@Slf4j
@Service("IDcaBParttimejobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBParttimejobServiceImpl extends ServiceImpl<DcaBParttimejobMapper, DcaBParttimejob> implements IDcaBParttimejobService {


@Override
public IPage<DcaBParttimejob> findDcaBParttimejobs(QueryRequest request, DcaBParttimejob dcaBParttimejob){
        try{
        LambdaQueryWrapper<DcaBParttimejob> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBParttimejob::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBParttimejob.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBParttimejob.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBParttimejob::getCreateTime, dcaBParttimejob.getCreateTimeFrom())
                                .le(DcaBParttimejob::getCreateTime, dcaBParttimejob.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBParttimejob.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBParttimejob.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBParttimejob::getModifyTime, dcaBParttimejob.getModifyTimeFrom())
                                .le(DcaBParttimejob::getModifyTime, dcaBParttimejob.getModifyTimeTo());
                                }

        Page<DcaBParttimejob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBParttimejob> findDcaBParttimejobList (QueryRequest request, DcaBParttimejob dcaBParttimejob){
        try{
        Page<DcaBParttimejob> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBParttimejob(page,dcaBParttimejob);
        }catch(Exception e){
        log.error("获取社会兼职表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBParttimejob(DcaBParttimejob dcaBParttimejob){
                dcaBParttimejob.setId(UUID.randomUUID().toString());
        dcaBParttimejob.setCreateTime(new Date());
        dcaBParttimejob.setIsDeletemark(1);
        this.save(dcaBParttimejob);
        }

@Override
@Transactional
public void updateDcaBParttimejob(DcaBParttimejob dcaBParttimejob){
        dcaBParttimejob.setModifyTime(new Date());
        this.baseMapper.updateDcaBParttimejob(dcaBParttimejob);
        }

@Override
@Transactional
public void deleteDcaBParttimejobs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
        public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }


        }