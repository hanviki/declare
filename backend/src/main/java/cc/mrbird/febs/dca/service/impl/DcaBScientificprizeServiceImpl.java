package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBScientificprize;
import cc.mrbird.febs.dca.dao.DcaBScientificprizeMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBScientificprizeService;
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
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 * 任现职以来科研获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
@Slf4j
@Service("IDcaBScientificprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBScientificprizeServiceImpl extends ServiceImpl<DcaBScientificprizeMapper, DcaBScientificprize> implements IDcaBScientificprizeService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBScientificprize> findDcaBScientificprizes(QueryRequest request, DcaBScientificprize dcaBScientificprize) {
        try {
            LambdaQueryWrapper<DcaBScientificprize> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBScientificprize::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBScientificprize.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBScientificprize::getUserAccount, dcaBScientificprize.getUserAccount()).or()
                        .like(DcaBScientificprize::getUserAccountName, dcaBScientificprize.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBScientificprize.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBScientificprize.getAuditMan(), dcaBScientificprize.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBScientificprize::getUserAccount, userAccountsList);
            }
            if (dcaBScientificprize.getState() != null) {
                queryWrapper.eq(DcaBScientificprize::getState, dcaBScientificprize.getState());
            }
            if (dcaBScientificprize.getAuditState() != null && (dcaBScientificprize.getAuditState() >= 0)) {
                queryWrapper.eq(DcaBScientificprize::getAuditState, dcaBScientificprize.getAuditState());
            }
            if (StringUtils.isNotBlank(dcaBScientificprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBScientificprize.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBScientificprize::getCreateTime, dcaBScientificprize.getCreateTimeFrom())
                        .le(DcaBScientificprize::getCreateTime, dcaBScientificprize.getCreateTimeTo());
            }

            if (dcaBScientificprize.getAuditXuhaoS() != null && (dcaBScientificprize.getAuditXuhaoS() > 0)) {
                if (dcaBScientificprize.getAuditXuhaoE() == null || dcaBScientificprize.getAuditXuhaoE().equals(0)) {
                    dcaBScientificprize.setAuditXuhaoE(dcaBScientificprize.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_scientificprize.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBScientificprize.getAuditXuhaoS() + " and" +
                        " " + dcaBScientificprize.getAuditXuhaoE() + ")");
            }
            Page<DcaBScientificprize> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBScientificprize> result = this.page(page, queryWrapper);
            for (DcaBScientificprize item : result.getRecords()
            ) {
                List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    item.setAuditXuhao(list2.get(0).getPatentRanknum());
                }
            }
            return  result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBScientificprize> findDcaBScientificprizeList(QueryRequest request, DcaBScientificprize dcaBScientificprize) {
        try {
            Page<DcaBScientificprize> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBScientificprize(page, dcaBScientificprize);
        } catch (Exception e) {
            log.error("获取任现职以来科研获奖情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBScientificprize(DcaBScientificprize dcaBScientificprize) {
        dcaBScientificprize.setId(UUID.randomUUID().toString());
        dcaBScientificprize.setCreateTime(new Date());
        dcaBScientificprize.setIsDeletemark(1);
        this.save(dcaBScientificprize);
    }

    @Override
    @Transactional
    public void updateDcaBScientificprize(DcaBScientificprize dcaBScientificprize) {
        dcaBScientificprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBScientificprize(dcaBScientificprize);
    }

    @Override
    @Transactional
    public void deleteDcaBScientificprizes(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void deleteByuseraccount(String userAccount) {
        this.baseMapper.deleteByAccount(userAccount);
    }

    @Override
    @Transactional
    public int getMaxDisplayIndexByuseraccount(String userAccount) {
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
    }
}