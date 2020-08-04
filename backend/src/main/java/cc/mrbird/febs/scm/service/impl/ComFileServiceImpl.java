package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.dao.ComFileMapper;
import cc.mrbird.febs.scm.service.IComFileService;
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
 * 附件 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
@Slf4j
@Service("IComFileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ComFileServiceImpl extends ServiceImpl<ComFileMapper, ComFile> implements IComFileService {


    @Override
    public IPage<ComFile> findComFiles(QueryRequest request, ComFile comFile) {
        try {
            LambdaQueryWrapper<ComFile> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(comFile.getId())) {
                queryWrapper.eq(ComFile::getId, comFile.getId());
            }
            Page<ComFile> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createComFile(ComFile comFile) {
        // comFile.setId(UUID.randomUUID().toString());
        comFile.setCreateTime(new Date());
        this.save(comFile);
    }

    @Override
    @Transactional
    public void updateComFile(ComFile comFile) {
        comFile.setModifyTime(new Date());
        this.baseMapper.updateComFile(comFile);
    }

    @Override
    @Transactional
    public void deleteComFiles(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}