package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBPolitalshow;
import cc.mrbird.febs.dca.dao.DcaBPolitalshowMapper;
import cc.mrbird.febs.dca.service.IDcaBPolitalshowService;
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
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBPolitalshowService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBPolitalshowServiceImpl extends ServiceImpl<DcaBPolitalshowMapper, DcaBPolitalshow> implements IDcaBPolitalshowService {


@Override
public IPage<DcaBPolitalshow> findDcaBPolitalshows(QueryRequest request, DcaBPolitalshow dcaBPolitalshow){
        try{
        LambdaQueryWrapper<DcaBPolitalshow> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBPolitalshow::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBPolitalshow.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBPolitalshow::getUserAccount, dcaBPolitalshow.getUserAccount()).or()
                        .like(DcaBPolitalshow::getUserAccountName, dcaBPolitalshow.getUserAccount()));

            }
                                if (dcaBPolitalshow.getState()!=null) {
                                queryWrapper.eq(DcaBPolitalshow::getState, dcaBPolitalshow.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBPolitalshow.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBPolitalshow.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBPolitalshow::getCreateTime, dcaBPolitalshow.getCreateTimeFrom())
                                .le(DcaBPolitalshow::getCreateTime, dcaBPolitalshow.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBPolitalshow.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBPolitalshow.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBPolitalshow::getModifyTime, dcaBPolitalshow.getModifyTimeFrom())
                                .le(DcaBPolitalshow::getModifyTime, dcaBPolitalshow.getModifyTimeTo());
                                }

        Page<DcaBPolitalshow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBPolitalshow> findDcaBPolitalshowList (QueryRequest request, DcaBPolitalshow dcaBPolitalshow){
        try{
        Page<DcaBPolitalshow> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBPolitalshow(page,dcaBPolitalshow);
        }catch(Exception e){
        log.error("获取个人思想政治表现失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBPolitalshow(DcaBPolitalshow dcaBPolitalshow){
                dcaBPolitalshow.setId(UUID.randomUUID().toString());
        dcaBPolitalshow.setCreateTime(new Date());
        dcaBPolitalshow.setIsDeletemark(1);
        this.save(dcaBPolitalshow);
        }

@Override
@Transactional
public void updateDcaBPolitalshow(DcaBPolitalshow dcaBPolitalshow){
        dcaBPolitalshow.setModifyTime(new Date());
        this.baseMapper.updateDcaBPolitalshow(dcaBPolitalshow);
        }

@Override
@Transactional
public void deleteDcaBPolitalshows(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }