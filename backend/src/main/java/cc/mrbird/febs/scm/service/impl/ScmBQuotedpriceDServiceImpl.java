package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBQuotedpriceD;
import cc.mrbird.febs.scm.dao.ScmBQuotedpriceDMapper;
import cc.mrbird.febs.scm.service.IScmBQuotedpriceDService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-12-27
 */
@Slf4j
@Service("IScmBQuotedpriceDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBQuotedpriceDServiceImpl extends ServiceImpl<ScmBQuotedpriceDMapper, ScmBQuotedpriceD> implements IScmBQuotedpriceDService {


@Override
public IPage<ScmBQuotedpriceD> findScmBQuotedpriceDs(QueryRequest request, ScmBQuotedpriceD scmBQuotedpriceD){
        try{
        LambdaQueryWrapper<ScmBQuotedpriceD> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(scmBQuotedpriceD.getHospitalName())) {
        queryWrapper.eq(ScmBQuotedpriceD::getHospitalName, scmBQuotedpriceD.getHospitalName());
        }
        queryWrapper.eq(ScmBQuotedpriceD::getIsDeletemark, 1);//1是未删 0是已删
        Page<ScmBQuotedpriceD> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmBQuotedpriceD(ScmBQuotedpriceD scmBQuotedpriceD){
        scmBQuotedpriceD.setId(UUID.randomUUID().toString());
        scmBQuotedpriceD.setCreateTime(new Date());
        scmBQuotedpriceD.setIsDeletemark(1);
        this.save(scmBQuotedpriceD);
        }

@Override
@Transactional
public void updateScmBQuotedpriceD(ScmBQuotedpriceD scmBQuotedpriceD){
        scmBQuotedpriceD.setModifyTime(new Date());
        this.baseMapper.updateScmBQuotedpriceD(scmBQuotedpriceD);
        }

@Override
@Transactional
public void deleteScmBQuotedpriceDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }