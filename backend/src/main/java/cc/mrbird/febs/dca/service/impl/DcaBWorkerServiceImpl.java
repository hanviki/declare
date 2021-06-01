package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBWorker;
import cc.mrbird.febs.dca.dao.DcaBWorkerMapper;
import cc.mrbird.febs.dca.service.IDcaBWorkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

/**
 * <p>
 * 协和医院合同制职工信息确认表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-05-24
 */
@Slf4j
@Service("IDcaBWorkerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBWorkerServiceImpl extends ServiceImpl<DcaBWorkerMapper, DcaBWorker> implements IDcaBWorkerService {


    @Override
    public IPage<DcaBWorker> findDcaBWorkers(QueryRequest request, DcaBWorker dcaBWorker) {
        try {
            LambdaQueryWrapper<DcaBWorker> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBWorker::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBWorker.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBWorker::getUserAccount, dcaBWorker.getUserAccount()).or()
                        .like(DcaBWorker::getUserAccountName, dcaBWorker.getUserAccount()));

            }
            if (dcaBWorker.getState() != null) {
                queryWrapper.eq(DcaBWorker::getState, dcaBWorker.getState());
            }

            Page<DcaBWorker> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBWorker> findDcaBWorkerList(QueryRequest request, DcaBWorker dcaBWorker) {
        try {
            Page<DcaBWorker> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBWorker(page, dcaBWorker);
        } catch (Exception e) {
            log.error("获取协和医院合同制职工信息确认表失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBWorker(DcaBWorker dcaBWorker) {
        dcaBWorker.setId(UUID.randomUUID().toString());
        dcaBWorker.setCreateTime(new Date());
        dcaBWorker.setIsDeletemark(1);
        this.save(dcaBWorker);
    }

    @Override
    @Transactional
    public void updateDcaBWorker(DcaBWorker dcaBWorker) {
        dcaBWorker.setModifyTime(new Date());
        this.baseMapper.updateDcaBWorker(dcaBWorker);
    }

    @Override
    @Transactional
    public void deleteDcaBWorkers(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public List<DcaBWorker> getAll(String userAccount, String dcaYear) {
        LambdaQueryWrapper<DcaBWorker> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.eq(DcaBWorker::getUserAccount, userAccount);
        }

        return this.baseMapper.selectList(queryWrapper);
    }

}