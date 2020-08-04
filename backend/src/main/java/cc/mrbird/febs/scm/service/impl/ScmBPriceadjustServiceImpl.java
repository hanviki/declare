package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBPriceadjust;
import cc.mrbird.febs.scm.dao.ScmBPriceadjustMapper;
import cc.mrbird.febs.scm.service.IScmBPriceadjustService;
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
@Service("IScmBPriceadjustService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBPriceadjustServiceImpl extends ServiceImpl<ScmBPriceadjustMapper, ScmBPriceadjust> implements IScmBPriceadjustService {


@Override
public IPage<ScmBPriceadjust> findScmBPriceadjusts(QueryRequest request, ScmBPriceadjust scmBPriceadjust){
        try{
        LambdaQueryWrapper<ScmBPriceadjust> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(scmBPriceadjust.getCode())) {
        queryWrapper.eq(ScmBPriceadjust::getCode, scmBPriceadjust.getCode());
        }
        queryWrapper.eq(ScmBPriceadjust::getIsDeletemark, 1);//1是未删 0是已删
        Page<ScmBPriceadjust> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmBPriceadjust(ScmBPriceadjust scmBPriceadjust){
       // scmBPriceadjust.setId(UUID.randomUUID().toString());
        scmBPriceadjust.setCreateTime(new Date());
        scmBPriceadjust.setIsDeletemark(1);
        this.save(scmBPriceadjust);
        }

@Override
@Transactional
public void updateScmBPriceadjust(ScmBPriceadjust scmBPriceadjust){
        scmBPriceadjust.setModifyTime(new Date());
        this.baseMapper.updateScmBPriceadjust(scmBPriceadjust);
        }

@Override
@Transactional
public void deleteScmBPriceadjusts(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }