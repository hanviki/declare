package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBFivecomment;
import cc.mrbird.febs.dca.dao.DcaBFivecommentMapper;
import cc.mrbird.febs.dca.service.IDcaBFivecommentService;
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
 * 近五年总体项目评价 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBFivecommentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBFivecommentServiceImpl extends ServiceImpl<DcaBFivecommentMapper, DcaBFivecomment> implements IDcaBFivecommentService {


@Override
public IPage<DcaBFivecomment> findDcaBFivecomments(QueryRequest request, DcaBFivecomment dcaBFivecomment){
        try{
        LambdaQueryWrapper<DcaBFivecomment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBFivecomment::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBFivecomment.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBFivecomment::getUserAccount, dcaBFivecomment.getUserAccount()).or()
                        .like(DcaBFivecomment::getUserAccountName, dcaBFivecomment.getUserAccount()));

            }
                                if (dcaBFivecomment.getState()!=null) {
                                queryWrapper.eq(DcaBFivecomment::getState, dcaBFivecomment.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBFivecomment.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBFivecomment.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBFivecomment::getCreateTime, dcaBFivecomment.getCreateTimeFrom())
                                .le(DcaBFivecomment::getCreateTime, dcaBFivecomment.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBFivecomment.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBFivecomment.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBFivecomment::getModifyTime, dcaBFivecomment.getModifyTimeFrom())
                                .le(DcaBFivecomment::getModifyTime, dcaBFivecomment.getModifyTimeTo());
                                }

        Page<DcaBFivecomment> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBFivecomment> findDcaBFivecommentList (QueryRequest request, DcaBFivecomment dcaBFivecomment){
        try{
        Page<DcaBFivecomment> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBFivecomment(page,dcaBFivecomment);
        }catch(Exception e){
        log.error("获取近五年总体项目评价失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBFivecomment(DcaBFivecomment dcaBFivecomment){
                dcaBFivecomment.setId(UUID.randomUUID().toString());
        dcaBFivecomment.setCreateTime(new Date());
        dcaBFivecomment.setIsDeletemark(1);
        this.save(dcaBFivecomment);
        }

@Override
@Transactional
public void updateDcaBFivecomment(DcaBFivecomment dcaBFivecomment){
        dcaBFivecomment.setModifyTime(new Date());
        this.baseMapper.updateDcaBFivecomment(dcaBFivecomment);
        }

@Override
@Transactional
public void deleteDcaBFivecomments(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }