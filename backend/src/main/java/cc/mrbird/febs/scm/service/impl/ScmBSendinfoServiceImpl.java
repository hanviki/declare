package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBSendinfo;
import cc.mrbird.febs.scm.dao.ScmBSendinfoMapper;
import cc.mrbird.febs.scm.service.IScmBSendinfoService;
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
 * 送货单 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
@Slf4j
@Service("IScmBSendinfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBSendinfoServiceImpl extends ServiceImpl<ScmBSendinfoMapper, ScmBSendinfo> implements IScmBSendinfoService {


    @Override
    public IPage<ScmBSendinfo> findScmBSendinfos(QueryRequest request, ScmBSendinfo scmBSendinfo) {
        try {
            LambdaQueryWrapper<ScmBSendinfo> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmBSendinfo.getGysaccount())) {
                queryWrapper.eq(ScmBSendinfo::getGysaccount, scmBSendinfo.getGysaccount());
            }
            if (scmBSendinfo.getId() != null) {
                queryWrapper.eq(ScmBSendinfo::getId, scmBSendinfo.getId());
            }
            if (scmBSendinfo.getMatnr() != null) {
                queryWrapper.eq(ScmBSendinfo::getMatnr, scmBSendinfo.getMatnr());
            }
            if (scmBSendinfo.getTxz01() != null) {
                queryWrapper.like(ScmBSendinfo::getTxz01, scmBSendinfo.getTxz01());
            }
            queryWrapper.eq(ScmBSendinfo::getIsDeletemark, 1);
            Page<ScmBSendinfo> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmBSendinfo(ScmBSendinfo scmBSendinfo) {
        //scmBSendinfo.setId(UUID.randomUUID().toString());
        scmBSendinfo.setCreateTime(new Date());
        scmBSendinfo.setIsDeletemark(1);
        this.save(scmBSendinfo);
    }

    @Override
    @Transactional
    public void updateScmBSendinfo(ScmBSendinfo scmBSendinfo) {
        scmBSendinfo.setModifyTime(new Date());
        this.baseMapper.updateScmBSendinfo(scmBSendinfo);
    }

    @Override
    @Transactional
    public void deleteScmBSendinfos(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}