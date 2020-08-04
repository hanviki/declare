package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.VScmBSalereturn;
import cc.mrbird.febs.scm.dao.VScmBSalereturnMapper;
import cc.mrbird.febs.scm.service.IVScmBSalereturnService;
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
 * VIEW 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-12-09
 */
@Slf4j
@Service("IVScmBSalereturnService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VScmBSalereturnServiceImpl extends ServiceImpl<VScmBSalereturnMapper, VScmBSalereturn> implements IVScmBSalereturnService {


@Override
public IPage<VScmBSalereturn> findVScmBSalereturns(QueryRequest request, VScmBSalereturn vScmBSalereturn){
        try{
        LambdaQueryWrapper<VScmBSalereturn> queryWrapper=new LambdaQueryWrapper<>();

        Page<VScmBSalereturn> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createVScmBSalereturn(VScmBSalereturn vScmBSalereturn){
        vScmBSalereturn.setId(UUID.randomUUID().toString());
      //  vScmBSalereturn.setCreateTime(new Date());
       // vScmBSalereturn.setIsDeletemark(1);
        this.save(vScmBSalereturn);
        }

@Override
@Transactional
public void updateVScmBSalereturn(VScmBSalereturn vScmBSalereturn){
      //  vScmBSalereturn.setModifyTime(new Date());
        this.baseMapper.updateVScmBSalereturn(vScmBSalereturn);
        }

@Override
@Transactional
public void deleteVScmBSalereturns(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }