package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBQuerypriceProduct;
import cc.mrbird.febs.scm.dao.ScmBQuerypriceProductMapper;
import cc.mrbird.febs.scm.service.IScmBQuerypriceProductService;
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
@Service("IScmBQuerypriceProductService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBQuerypriceProductServiceImpl extends ServiceImpl<ScmBQuerypriceProductMapper, ScmBQuerypriceProduct> implements IScmBQuerypriceProductService {


@Override
public IPage<ScmBQuerypriceProduct> findScmBQuerypriceProducts(QueryRequest request, ScmBQuerypriceProduct scmBQuerypriceProduct){
        try{
        LambdaQueryWrapper<ScmBQuerypriceProduct> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(scmBQuerypriceProduct.getProductName())) {
        queryWrapper.eq(ScmBQuerypriceProduct::getProductName, scmBQuerypriceProduct.getProductName());
        }
        queryWrapper.eq(ScmBQuerypriceProduct::getIsDeletemark, 1);//1是未删 0是已删
        Page<ScmBQuerypriceProduct> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmBQuerypriceProduct(ScmBQuerypriceProduct scmBQuerypriceProduct){
        scmBQuerypriceProduct.setId(UUID.randomUUID().toString());
        scmBQuerypriceProduct.setCreateTime(new Date());
        scmBQuerypriceProduct.setIsDeletemark(1);
        this.save(scmBQuerypriceProduct);
        }

@Override
@Transactional
public void updateScmBQuerypriceProduct(ScmBQuerypriceProduct scmBQuerypriceProduct){
        scmBQuerypriceProduct.setModifyTime(new Date());
        this.baseMapper.updateScmBQuerypriceProduct(scmBQuerypriceProduct);
        }

@Override
@Transactional
public void deleteScmBQuerypriceProducts(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }