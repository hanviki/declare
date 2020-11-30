package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyAttachfile;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyAttachfileMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyAttachfileService;
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
 * 其他附件材料 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyAttachfileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyAttachfileServiceImpl extends ServiceImpl<DcaBCopyAttachfileMapper, DcaBCopyAttachfile> implements IDcaBCopyAttachfileService {


@Override
public IPage<DcaBCopyAttachfile> findDcaBCopyAttachfiles(QueryRequest request, DcaBCopyAttachfile dcaBCopyAttachfile){
        try{
        LambdaQueryWrapper<DcaBCopyAttachfile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyAttachfile::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyAttachfile.getDcaYear())) {
                                queryWrapper.like(DcaBCopyAttachfile::getDcaYear, dcaBCopyAttachfile.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAttachfile.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyAttachfile::getUserAccountName, dcaBCopyAttachfile.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyAttachfile.getUserAccount())) {
                                queryWrapper.like(DcaBCopyAttachfile::getUserAccount, dcaBCopyAttachfile.getUserAccount());
                                }

        Page<DcaBCopyAttachfile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyAttachfile> findDcaBCopyAttachfileList (QueryRequest request, DcaBCopyAttachfile dcaBCopyAttachfile){
        try{
        Page<DcaBCopyAttachfile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyAttachfile(page,dcaBCopyAttachfile);
        }catch(Exception e){
        log.error("获取其他附件材料失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyAttachfile(DcaBCopyAttachfile dcaBCopyAttachfile){
                dcaBCopyAttachfile.setId(UUID.randomUUID().toString());
        dcaBCopyAttachfile.setCreateTime(new Date());
        dcaBCopyAttachfile.setIsDeletemark(1);
        this.save(dcaBCopyAttachfile);
        }

@Override
@Transactional
public void updateDcaBCopyAttachfile(DcaBCopyAttachfile dcaBCopyAttachfile){
        dcaBCopyAttachfile.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyAttachfile(dcaBCopyAttachfile);
        }

@Override
@Transactional
public void deleteDcaBCopyAttachfiles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyAttachfile> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyAttachfile> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyAttachfile::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyAttachfile::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }