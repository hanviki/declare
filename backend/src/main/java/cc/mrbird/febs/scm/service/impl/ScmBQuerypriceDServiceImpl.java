package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBQuerypriceD;
import cc.mrbird.febs.scm.dao.ScmBQuerypriceDMapper;
import cc.mrbird.febs.scm.service.IScmBQuerypriceDService;
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
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-12-26
 */
@Slf4j
@Service("IScmBQuerypriceDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBQuerypriceDServiceImpl extends ServiceImpl<ScmBQuerypriceDMapper, ScmBQuerypriceD> implements IScmBQuerypriceDService {


@Override
public IPage<ScmBQuerypriceD> findScmBQuerypriceDs(QueryRequest request, ScmBQuerypriceD scmBQuerypriceD){
        try{
        LambdaQueryWrapper<ScmBQuerypriceD> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(scmBQuerypriceD.getGysaccount())) {
        queryWrapper.eq(ScmBQuerypriceD::getGysaccount, scmBQuerypriceD.getGysaccount());
        }
        if(scmBQuerypriceD.getBaseId()!=null)
        {
                queryWrapper.eq(ScmBQuerypriceD::getBaseId,scmBQuerypriceD.getBaseId());
        }
        queryWrapper.eq(ScmBQuerypriceD::getIsDeletemark, 1);//1是未删 0是已删
        Page<ScmBQuerypriceD> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmBQuerypriceD(ScmBQuerypriceD scmBQuerypriceD){
        scmBQuerypriceD.setId(UUID.randomUUID().toString());
        scmBQuerypriceD.setCreateTime(new Date());
        scmBQuerypriceD.setIsDeletemark(1);
        this.save(scmBQuerypriceD);
        }

@Override
@Transactional
public void updateScmBQuerypriceD(ScmBQuerypriceD scmBQuerypriceD){
        scmBQuerypriceD.setModifyTime(new Date());
        this.baseMapper.updateScmBQuerypriceD(scmBQuerypriceD);
        }

@Override
@Transactional
public void deleteScmBQuerypriceDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        @Override
        @Transactional
        public void updateScmBQuotedpriceDState(String gysaccount,Long baseid){
                this.baseMapper.updateScmBQuertpriceSate(gysaccount,baseid);
        }

        }