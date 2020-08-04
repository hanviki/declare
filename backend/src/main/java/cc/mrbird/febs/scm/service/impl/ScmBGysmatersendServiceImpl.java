package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.dao.ScmDMaterMapper;
import cc.mrbird.febs.scm.entity.ScmBGysMaterPic;
import cc.mrbird.febs.scm.entity.ScmBGysmatersend;
import cc.mrbird.febs.scm.dao.ScmBGysmatersendMapper;
import cc.mrbird.febs.scm.entity.ScmDMater;
import cc.mrbird.febs.scm.service.IScmBGysmatersendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2020-06-03
 */
@Slf4j
@Service("IScmBGysmatersendService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBGysmatersendServiceImpl extends ServiceImpl<ScmBGysmatersendMapper, ScmBGysmatersend> implements IScmBGysmatersendService {
    @Autowired
    private ScmDMaterMapper scmDMaterMapper;

    @Override
    public IPage<ScmBGysmatersend> findScmBGysmatersends(QueryRequest request, ScmBGysmatersend scmBGysmatersend, String keyword_mater, String keyword_gys) {
        try {
            LambdaQueryWrapper<ScmBGysmatersend> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmBGysmatersend.getId())) {
                queryWrapper.eq(ScmBGysmatersend::getId, scmBGysmatersend.getId());
            }
            if (scmBGysmatersend.getState()!=null) {
                queryWrapper.eq(ScmBGysmatersend::getState, scmBGysmatersend.getState());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getMaterId())) {
                queryWrapper.eq(ScmBGysmatersend::getMaterId, scmBGysmatersend.getMaterId());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getMatnr())) {
                queryWrapper.eq(ScmBGysmatersend::getMatnr, scmBGysmatersend.getMatnr());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getTxz01())) {
                queryWrapper.like(ScmBGysmatersend::getTxz01, scmBGysmatersend.getTxz01());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getProduceArea())) {
                queryWrapper.like(ScmBGysmatersend::getProduceArea, scmBGysmatersend.getProduceArea());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getSpec())) {
                queryWrapper.like(ScmBGysmatersend::getSpec, scmBGysmatersend.getSpec());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getGysaccount())) {
                queryWrapper.eq(ScmBGysmatersend::getGysaccount, scmBGysmatersend.getGysaccount());
            }
            if (StringUtils.isNotBlank(scmBGysmatersend.getName())) {
                queryWrapper.like(ScmBGysmatersend::getName, scmBGysmatersend.getName());
            }
            if (scmBGysmatersend.getSendStartTime() != null) {
                queryWrapper.ge(ScmBGysmatersend::getSendStartTime, scmBGysmatersend.getSendStartTime());
            }
            if (scmBGysmatersend.getSendEndTime() != null) {
                queryWrapper.le(ScmBGysmatersend::getSendEndTime, scmBGysmatersend.getSendEndTime());
            }
            if (StringUtils.isNotBlank(keyword_mater)) {
                queryWrapper.and(qw -> qw.eq(ScmBGysmatersend::getMatnr, keyword_mater).or().like(ScmBGysmatersend::getTxz01, keyword_mater));
            }
            if (StringUtils.isNotBlank(keyword_gys)) {
                queryWrapper.and(qw -> qw.eq(ScmBGysmatersend::getGysaccount, keyword_gys).or().like(ScmBGysmatersend::getName, keyword_gys));
            }
            queryWrapper.eq(ScmBGysmatersend::getIsDeletemark, 1);//1是未删 0是已删
            Page<ScmBGysmatersend> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmBGysmatersend(ScmBGysmatersend scmBGysmatersend) {
        scmBGysmatersend.setId(UUID.randomUUID().toString());
        scmBGysmatersend.setCreateTime(new Date());
        scmBGysmatersend.setIsDeletemark(1);
//        String matnr = scmBGysmatersend.getMaterId();


        //ScmDMater scmDMater = this.scmDMaterMapper.selectById(matnr.trim());
        scmBGysmatersend.setState(0);
//        scmBGysmatersend.setMatnr(scmDMater.getMatnr());
//        scmBGysmatersend.setMseht(scmDMater.getMseht());
//        scmBGysmatersend.setProduceArea(scmDMater.getProduceArea());
//        scmBGysmatersend.setSpellCode(scmDMater.getSpellCode());
//        scmBGysmatersend.setSpec(scmDMater.getSpec());
//        scmBGysmatersend.setTxz01(scmDMater.getTxz01());
        this.save(scmBGysmatersend);
    }

    @Override
    @Transactional
    public void updateScmBGysmatersend(ScmBGysmatersend scmBGysmatersend) {
        scmBGysmatersend.setModifyTime(new Date());
        this.baseMapper.updateScmBGysmatersend(scmBGysmatersend);
    }

    @Override
    @Transactional
    public void deleteScmBGysmatersends(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}