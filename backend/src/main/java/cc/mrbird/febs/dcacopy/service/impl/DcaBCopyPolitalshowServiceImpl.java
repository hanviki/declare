package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyPolitalshow;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyPolitalshowMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyPolitalshowService;
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
 * 个人思想政治表现 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyPolitalshowService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyPolitalshowServiceImpl extends ServiceImpl<DcaBCopyPolitalshowMapper, DcaBCopyPolitalshow> implements IDcaBCopyPolitalshowService {


@Override
public IPage<DcaBCopyPolitalshow> findDcaBCopyPolitalshows(QueryRequest request, DcaBCopyPolitalshow dcaBCopyPolitalshow){
        try{
        LambdaQueryWrapper<DcaBCopyPolitalshow> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyPolitalshow::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyPolitalshow.getDcaYear())) {
                                queryWrapper.like(DcaBCopyPolitalshow::getDcaYear, dcaBCopyPolitalshow.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPolitalshow.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyPolitalshow::getUserAccountName, dcaBCopyPolitalshow.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyPolitalshow.getUserAccount())) {
                                queryWrapper.like(DcaBCopyPolitalshow::getUserAccount, dcaBCopyPolitalshow.getUserAccount());
                                }

        Page<DcaBCopyPolitalshow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyPolitalshow> findDcaBCopyPolitalshowList (QueryRequest request, DcaBCopyPolitalshow dcaBCopyPolitalshow){
        try{
        Page<DcaBCopyPolitalshow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyPolitalshow(page,dcaBCopyPolitalshow);
        }catch(Exception e){
        log.error("获取个人思想政治表现失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyPolitalshow(DcaBCopyPolitalshow dcaBCopyPolitalshow){
                dcaBCopyPolitalshow.setId(UUID.randomUUID().toString());
        dcaBCopyPolitalshow.setCreateTime(new Date());
        dcaBCopyPolitalshow.setIsDeletemark(1);
        this.save(dcaBCopyPolitalshow);
        }

@Override
@Transactional
public void updateDcaBCopyPolitalshow(DcaBCopyPolitalshow dcaBCopyPolitalshow){
        dcaBCopyPolitalshow.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyPolitalshow(dcaBCopyPolitalshow);
        }

@Override
@Transactional
public void deleteDcaBCopyPolitalshows(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyPolitalshow> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyPolitalshow> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyPolitalshow::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyPolitalshow::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }