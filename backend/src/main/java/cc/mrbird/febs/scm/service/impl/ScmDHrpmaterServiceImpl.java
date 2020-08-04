package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmDHrpmater;
import cc.mrbird.febs.scm.dao.ScmDHrpmaterMapper;
import cc.mrbird.febs.scm.service.IScmDHrpmaterService;
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
 * SAP的药品字典库，这是关联供应商的 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
@Slf4j
@Service("IScmDHrpmaterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDHrpmaterServiceImpl extends ServiceImpl<ScmDHrpmaterMapper, ScmDHrpmater> implements IScmDHrpmaterService {


    @Override
    public IPage<ScmDHrpmater> findScmDHrpmaters(QueryRequest request, ScmDHrpmater scmDHrpmater) {
        try {
            LambdaQueryWrapper<ScmDHrpmater> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmDHrpmater.getCode())) {
                queryWrapper.eq(ScmDHrpmater::getCode, scmDHrpmater.getCode());
            }
            if (StringUtils.isNotBlank(scmDHrpmater.keyword)) {
                queryWrapper.and(wrapper -> wrapper.eq(ScmDHrpmater::getMatnr, scmDHrpmater.keyword).or().like(ScmDHrpmater::getMaktx, scmDHrpmater.keyword).or().like(ScmDHrpmater::getZeinr, scmDHrpmater.keyword));
            }
            Page<ScmDHrpmater> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmDHrpmater(ScmDHrpmater scmDHrpmater) {
        scmDHrpmater.setId(UUID.randomUUID().toString());
        scmDHrpmater.setCreateTime(new Date());
        this.save(scmDHrpmater);
    }

    @Override
    @Transactional
    public void updateScmDHrpmater(ScmDHrpmater scmDHrpmater) {
        scmDHrpmater.setModifyTime(new Date());
        this.baseMapper.updateScmDHrpmater(scmDHrpmater);
    }

    @Override
    @Transactional
    public void deleteScmDHrpmaters(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}