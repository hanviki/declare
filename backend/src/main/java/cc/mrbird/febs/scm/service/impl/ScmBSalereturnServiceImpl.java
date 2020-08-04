package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBSalereturn;
import cc.mrbird.febs.scm.dao.ScmBSalereturnMapper;
import cc.mrbird.febs.scm.entity.VScmBSalereturn;
import cc.mrbird.febs.scm.service.IScmBSalereturnService;
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
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-12-09
 */
@Slf4j
@Service("IScmBSalereturnService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBSalereturnServiceImpl extends ServiceImpl<ScmBSalereturnMapper, ScmBSalereturn> implements IScmBSalereturnService {


    @Override
    public IPage<VScmBSalereturn> findScmBSalereturns(QueryRequest request, VScmBSalereturn vScmBSalereturn) {
        try {
            LambdaQueryWrapper<VScmBSalereturn> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(VScmBSalereturn::getState, vScmBSalereturn.getState());
            if (StringUtils.isNotBlank(vScmBSalereturn.getGysaccount())) {
                queryWrapper.eq(VScmBSalereturn::getGysaccount, vScmBSalereturn.getGysaccount());
            }
            if (StringUtils.isNotBlank(vScmBSalereturn.getGysname())) {
                queryWrapper.like(VScmBSalereturn::getGysname, vScmBSalereturn.getGysname());
            }
            if (StringUtils.isNotBlank(vScmBSalereturn.getMatnr())) {
                queryWrapper.eq(VScmBSalereturn::getMatnr, vScmBSalereturn.getMatnr());
            }
            if (StringUtils.isNotBlank(vScmBSalereturn.getTxz01())) {
                queryWrapper.like(VScmBSalereturn::getTxz01, vScmBSalereturn.getTxz01());
            }
            if (StringUtils.isNotBlank(vScmBSalereturn.getId())) {
                queryWrapper.eq(VScmBSalereturn::getId, vScmBSalereturn.getId());
            }

            Page<ScmBSalereturn> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.baseMapper.findViewSaleReturn(page, vScmBSalereturn);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmBSalereturn(ScmBSalereturn scmBSalereturn) {
        scmBSalereturn.setId(UUID.randomUUID().toString());
        scmBSalereturn.setCreateTime(new Date());
        scmBSalereturn.setIsDeletemark(1);
        this.save(scmBSalereturn);
    }

    @Override
    @Transactional
    public void updateScmBSalereturn(ScmBSalereturn scmBSalereturn) {
        scmBSalereturn.setModifyTime(new Date());
        this.baseMapper.updateScmBSalereturn(scmBSalereturn);
    }

    @Override
    @Transactional
    public void deleteScmBSalereturns(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}