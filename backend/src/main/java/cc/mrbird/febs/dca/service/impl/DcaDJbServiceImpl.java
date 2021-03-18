package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaDJb;
import cc.mrbird.febs.dca.dao.DcaDJbMapper;
import cc.mrbird.febs.dca.service.IDcaDJbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-03-09
 */
@Slf4j
@Service("IDcaDJbService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDJbServiceImpl extends ServiceImpl<DcaDJbMapper, DcaDJb> implements IDcaDJbService {


@Override
public IPage<DcaDJb> findDcaDJbs(QueryRequest request, DcaDJb dcaDJb){
        try{
        LambdaQueryWrapper<DcaDJb> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDJb::getIsDeletemark, 1);//1是未删 0是已删


        Page<DcaDJb> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDJb> findDcaDJbList (QueryRequest request, DcaDJb dcaDJb){
        try{
        Page<DcaDJb> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDJb(page,dcaDJb);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaDJb(DcaDJb dcaDJb){
                dcaDJb.setId(UUID.randomUUID().toString());
        dcaDJb.setCreateTime(new Date());
        dcaDJb.setIsDeletemark(1);
        this.save(dcaDJb);
        }

@Override
@Transactional
public void updateDcaDJb(DcaDJb dcaDJb){
        dcaDJb.setModifyTime(new Date());
        this.baseMapper.updateDcaDJb(dcaDJb);
        }

@Override
@Transactional
public void deleteDcaDJbs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaDJb> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaDJb> queryWrapper=new LambdaQueryWrapper<>();

      return  this.baseMapper.selectList(queryWrapper);
        }

        }