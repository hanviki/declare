package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBOtherwork;
import cc.mrbird.febs.dca.dao.DcaBOtherworkMapper;
import cc.mrbird.febs.dca.service.IDcaBOtherworkService;
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
 * 其他工作及成果，拟聘岗位工作思路及预期目标，个人总结 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-16
 */
@Slf4j
@Service("IDcaBOtherworkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBOtherworkServiceImpl extends ServiceImpl<DcaBOtherworkMapper, DcaBOtherwork> implements IDcaBOtherworkService {


@Override
public IPage<DcaBOtherwork> findDcaBOtherworks(QueryRequest request, DcaBOtherwork dcaBOtherwork){
        try{
        LambdaQueryWrapper<DcaBOtherwork> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBOtherwork::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBOtherwork.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBOtherwork::getUserAccount, dcaBOtherwork.getUserAccount()).or()
                        .like(DcaBOtherwork::getUserAccountName, dcaBOtherwork.getUserAccount()));

            }
                                if (dcaBOtherwork.getState()!=null) {
                                queryWrapper.eq(DcaBOtherwork::getState, dcaBOtherwork.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBOtherwork.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBOtherwork.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBOtherwork::getCreateTime, dcaBOtherwork.getCreateTimeFrom())
                                .le(DcaBOtherwork::getCreateTime, dcaBOtherwork.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBOtherwork.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBOtherwork.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBOtherwork::getModifyTime, dcaBOtherwork.getModifyTimeFrom())
                                .le(DcaBOtherwork::getModifyTime, dcaBOtherwork.getModifyTimeTo());
                                }

        Page<DcaBOtherwork> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBOtherwork> findDcaBOtherworkList (QueryRequest request, DcaBOtherwork dcaBOtherwork){
        try{
        Page<DcaBOtherwork> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBOtherwork(page,dcaBOtherwork);
        }catch(Exception e){
        log.error("获取其他工作及成果，拟聘岗位工作思路及预期目标，个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBOtherwork(DcaBOtherwork dcaBOtherwork){
                dcaBOtherwork.setId(UUID.randomUUID().toString());
        dcaBOtherwork.setCreateTime(new Date());
        dcaBOtherwork.setIsDeletemark(1);
        this.save(dcaBOtherwork);
        }

@Override
@Transactional
public void updateDcaBOtherwork(DcaBOtherwork dcaBOtherwork){
        dcaBOtherwork.setModifyTime(new Date());
        this.baseMapper.updateDcaBOtherwork(dcaBOtherwork);
        }

@Override
@Transactional
public void deleteDcaBOtherworks(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }