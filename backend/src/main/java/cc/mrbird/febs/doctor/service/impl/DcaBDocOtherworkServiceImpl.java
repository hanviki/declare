package cc.mrbird.febs.doctor.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.doctor.entity.DcaBDocOtherwork;
import cc.mrbird.febs.doctor.dao.DcaBDocOtherworkMapper;
import cc.mrbird.febs.doctor.service.IDcaBDocOtherworkService;
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
 * 其他工作及成果，拟聘岗位工作思路及预期目标，个人总结 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-11
 */
@Slf4j
@Service("IDcaBDocOtherworkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBDocOtherworkServiceImpl extends ServiceImpl<DcaBDocOtherworkMapper, DcaBDocOtherwork> implements IDcaBDocOtherworkService {


@Override
@DS("slave")
public IPage<DcaBDocOtherwork> findDcaBDocOtherworks(QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork){
        try{
        LambdaQueryWrapper<DcaBDocOtherwork> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBDocOtherwork::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBDocOtherwork.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBDocOtherwork::getUserAccount, dcaBDocOtherwork.getUserAccount()).or()
        .like(DcaBDocOtherwork::getUserAccountName, dcaBDocOtherwork.getUserAccount()));

        }
        if (dcaBDocOtherwork.getState()!=null) {
        queryWrapper.eq(DcaBDocOtherwork::getState, dcaBDocOtherwork.getState());
        }
       /** if (dcaBDocOtherwork.getAuditState()!=null && (dcaBDocOtherwork.getAuditState()>=0)) {
        queryWrapper.eq(DcaBDocOtherwork::getAuditState, dcaBDocOtherwork.getAuditState());
        }*/
                                if (StringUtils.isNotBlank(dcaBDocOtherwork.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBDocOtherwork.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBDocOtherwork::getCreateTime, dcaBDocOtherwork.getCreateTimeFrom())
                                .le(DcaBDocOtherwork::getCreateTime, dcaBDocOtherwork.getCreateTimeTo());
                                }

        Page<DcaBDocOtherwork> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<DcaBDocOtherwork> findDcaBDocOtherworkList (QueryRequest request, DcaBDocOtherwork dcaBDocOtherwork){
        try{
        Page<DcaBDocOtherwork> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBDocOtherwork(page,dcaBDocOtherwork);
        }catch(Exception e){
        log.error("获取其他工作及成果，拟聘岗位工作思路及预期目标，个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createDcaBDocOtherwork(DcaBDocOtherwork dcaBDocOtherwork){
                dcaBDocOtherwork.setId(UUID.randomUUID().toString());
        dcaBDocOtherwork.setCreateTime(new Date());
        dcaBDocOtherwork.setIsDeletemark(1);
        this.save(dcaBDocOtherwork);
        }

@Override
@Transactional
@DS("slave")
public void updateDcaBDocOtherwork(DcaBDocOtherwork dcaBDocOtherwork){
        dcaBDocOtherwork.setModifyTime(new Date());
        this.baseMapper.updateDcaBDocOtherwork(dcaBDocOtherwork);
        }

@Override
@Transactional
@DS("slave")
public void deleteDcaBDocOtherworks(String[]Ids){
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