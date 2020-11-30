package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyOtherwork;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyOtherworkMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyOtherworkService;
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
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyOtherworkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyOtherworkServiceImpl extends ServiceImpl<DcaBCopyOtherworkMapper, DcaBCopyOtherwork> implements IDcaBCopyOtherworkService {


@Override
public IPage<DcaBCopyOtherwork> findDcaBCopyOtherworks(QueryRequest request, DcaBCopyOtherwork dcaBCopyOtherwork){
        try{
        LambdaQueryWrapper<DcaBCopyOtherwork> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyOtherwork::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyOtherwork.getDcaYear())) {
                                queryWrapper.like(DcaBCopyOtherwork::getDcaYear, dcaBCopyOtherwork.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyOtherwork.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyOtherwork::getUserAccountName, dcaBCopyOtherwork.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyOtherwork.getUserAccount())) {
                                queryWrapper.like(DcaBCopyOtherwork::getUserAccount, dcaBCopyOtherwork.getUserAccount());
                                }

        Page<DcaBCopyOtherwork> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyOtherwork> findDcaBCopyOtherworkList (QueryRequest request, DcaBCopyOtherwork dcaBCopyOtherwork){
        try{
        Page<DcaBCopyOtherwork> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyOtherwork(page,dcaBCopyOtherwork);
        }catch(Exception e){
        log.error("获取其他工作及成果，拟聘岗位工作思路及预期目标，个人总结失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyOtherwork(DcaBCopyOtherwork dcaBCopyOtherwork){
                dcaBCopyOtherwork.setId(UUID.randomUUID().toString());
        dcaBCopyOtherwork.setCreateTime(new Date());
        dcaBCopyOtherwork.setIsDeletemark(1);
        this.save(dcaBCopyOtherwork);
        }

@Override
@Transactional
public void updateDcaBCopyOtherwork(DcaBCopyOtherwork dcaBCopyOtherwork){
        dcaBCopyOtherwork.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyOtherwork(dcaBCopyOtherwork);
        }

@Override
@Transactional
public void deleteDcaBCopyOtherworks(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyOtherwork> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyOtherwork> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyOtherwork::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyOtherwork::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }