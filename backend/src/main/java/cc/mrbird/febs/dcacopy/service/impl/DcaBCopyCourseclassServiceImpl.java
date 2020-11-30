package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyCourseclass;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyCourseclassMapper;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyCourseclassService;
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
 * 精品课程 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyCourseclassService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyCourseclassServiceImpl extends ServiceImpl<DcaBCopyCourseclassMapper, DcaBCopyCourseclass> implements IDcaBCopyCourseclassService {


@Override
public IPage<DcaBCopyCourseclass> findDcaBCopyCourseclasss(QueryRequest request, DcaBCopyCourseclass dcaBCopyCourseclass){
        try{
        LambdaQueryWrapper<DcaBCopyCourseclass> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyCourseclass::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyCourseclass.getDcaYear())) {
                                queryWrapper.like(DcaBCopyCourseclass::getDcaYear, dcaBCopyCourseclass.getDcaYear());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyCourseclass.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyCourseclass::getUserAccountName, dcaBCopyCourseclass.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyCourseclass.getUserAccount())) {
                                queryWrapper.like(DcaBCopyCourseclass::getUserAccount, dcaBCopyCourseclass.getUserAccount());
                                }

        Page<DcaBCopyCourseclass> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyCourseclass> findDcaBCopyCourseclassList (QueryRequest request, DcaBCopyCourseclass dcaBCopyCourseclass){
        try{
        Page<DcaBCopyCourseclass> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyCourseclass(page,dcaBCopyCourseclass);
        }catch(Exception e){
        log.error("获取精品课程失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyCourseclass(DcaBCopyCourseclass dcaBCopyCourseclass){
                dcaBCopyCourseclass.setId(UUID.randomUUID().toString());
        dcaBCopyCourseclass.setCreateTime(new Date());
        dcaBCopyCourseclass.setIsDeletemark(1);
        this.save(dcaBCopyCourseclass);
        }

@Override
@Transactional
public void updateDcaBCopyCourseclass(DcaBCopyCourseclass dcaBCopyCourseclass){
        dcaBCopyCourseclass.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyCourseclass(dcaBCopyCourseclass);
        }

@Override
@Transactional
public void deleteDcaBCopyCourseclasss(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyCourseclass> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyCourseclass> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyCourseclass::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyCourseclass::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }