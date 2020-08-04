package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmDMater;
import cc.mrbird.febs.scm.dao.ScmDMaterMapper;
import cc.mrbird.febs.scm.service.IScmDMaterService;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Date;

/**
 * <p>
 * 药品物料库 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */
@Slf4j
@Service("IScmDMaterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDMaterServiceImpl extends ServiceImpl<ScmDMaterMapper, ScmDMater> implements IScmDMaterService {


    @Override
    public IPage<ScmDMater> findScmDMaters(QueryRequest request, ScmDMater scmDMater) {
        try {
            LambdaQueryWrapper<ScmDMater> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmDMater.getCode())) {
                queryWrapper.eq(ScmDMater::getCode, scmDMater.getCode());
            }
            if (StringUtils.isNotBlank(scmDMater.keyword)) {
                queryWrapper.and(wrapper -> wrapper.eq(ScmDMater::getMatnr, scmDMater.keyword).or().like(ScmDMater::getSpellCode, scmDMater.keyword).or().like(ScmDMater::getTxz01, scmDMater.keyword));
            }
            if(StringUtils.isNotBlank(scmDMater.getGysaccount()))
            {
                queryWrapper.eq(ScmDMater::getGysaccount, scmDMater.getGysaccount());
            }
            if(StringUtils.isNotBlank(scmDMater.getTxz01()))
            {
                queryWrapper.like(ScmDMater::getTxz01, scmDMater.getTxz01().trim());
            }
            if(StringUtils.isNotBlank(scmDMater.getSpellCode()))
            {
                queryWrapper.likeLeft(ScmDMater::getSpellCode, scmDMater.getSpellCode());
            }
            if(StringUtils.isNotBlank(scmDMater.getMatnr()))
            {
                queryWrapper.eq(ScmDMater::getMatnr, scmDMater.getMatnr());
            }
            Page<ScmDMater> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ScmDMater> findScmDMaters_send(QueryRequest request, ScmDMater scmDMater) {
        try {
            LambdaQueryWrapper<ScmDMater> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmDMater.getCode())) {
                queryWrapper.eq(ScmDMater::getCode, scmDMater.getCode());
            }
            if (StringUtils.isNotBlank(scmDMater.keyword)) {
                queryWrapper.and(wrapper -> wrapper.eq(ScmDMater::getMatnr, scmDMater.keyword).or().like(ScmDMater::getSpellCode, scmDMater.keyword).or().like(ScmDMater::getTxz01, scmDMater.keyword));
            }
            queryWrapper.apply("LENGTH(GYSACCOUNT)=0");
            if(StringUtils.isNotBlank(scmDMater.getTxz01()))
            {
                queryWrapper.like(ScmDMater::getTxz01, scmDMater.getTxz01().trim());
            }
            if(StringUtils.isNotBlank(scmDMater.getSpellCode()))
            {
                queryWrapper.likeLeft(ScmDMater::getSpellCode, scmDMater.getSpellCode());
            }
            if(StringUtils.isNotBlank(scmDMater.getMatnr()))
            {
                queryWrapper.eq(ScmDMater::getMatnr, scmDMater.getMatnr());
            }
            Page<ScmDMater> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }
    @Override
    @Transactional
    public void createScmDMater(ScmDMater scmDMater) {
        scmDMater.setId(scmDMater.getMatnr());
        scmDMater.setCreateTime(new Date());
        this.save(scmDMater);
    }

    @Override
    @Transactional
    public void updateScmDMater(ScmDMater scmDMater) {
        scmDMater.setId(scmDMater.getMatnr());//药品编码和ID一致
        scmDMater.setModifyTime(new Date());
        this.baseMapper.updateScmDMater(scmDMater);
    }

    @Override
    @Transactional
    public void deleteScmDMaters(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}