package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBReport;
import cc.mrbird.febs.scm.dao.ScmBReportMapper;
import cc.mrbird.febs.scm.service.IScmBReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

/**
 * <p>
 * 公告表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
@Slf4j
@Service("IScmBReportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBReportServiceImpl extends ServiceImpl<ScmBReportMapper, ScmBReport> implements IScmBReportService {


    @Override
    public IPage<ScmBReport> findScmBReports(QueryRequest request, ScmBReport scmBReport) {
        try {
            LambdaQueryWrapper<ScmBReport> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmBReport.getCode())) {
                queryWrapper.eq(ScmBReport::getCode, scmBReport.getCode());
            }
            if (StringUtils.isNotBlank(scmBReport.getName())) {
                queryWrapper.like(ScmBReport::getName, scmBReport.getName());
            }
            if (StringUtils.isNotBlank(scmBReport.getId())) {
                queryWrapper.eq(ScmBReport::getId, scmBReport.getId());
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isNotBlank(scmBReport.getCreateTimeFrom()))
            {
                Date from= sdf.parse(scmBReport.getCreateTimeFrom());
                queryWrapper.ge(ScmBReport::getCreateTime, from);
            }
            if(StringUtils.isNotBlank(scmBReport.getCreateTimeTo()))
            {
                Date to= sdf.parse(scmBReport.getCreateTimeTo());
                queryWrapper.le(ScmBReport::getCreateTime, to);
            }
            Page<ScmBReport> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmBReport(ScmBReport scmBReport) {
        scmBReport.setId(UUID.randomUUID().toString());
        scmBReport.setCreateTime(new Date());
        this.save(scmBReport);
    }

    @Override
    @Transactional
    public void updateScmBReport(ScmBReport scmBReport) {
        scmBReport.setModifyTime(new Date());
        this.baseMapper.updateScmBReport(scmBReport);
    }

    @Override
    @Transactional
    public void deleteScmBReports(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}