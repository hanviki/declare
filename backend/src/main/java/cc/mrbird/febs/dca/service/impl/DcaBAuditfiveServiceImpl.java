package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBAuditfive;
import cc.mrbird.febs.dca.dao.DcaBAuditfiveMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBAuditfiveService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
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
import java.util.stream.Collectors;

/**
 * <p>
 * 近五年总体评价情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-16
 */
@Slf4j
@Service("IDcaBAuditfiveService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAuditfiveServiceImpl extends ServiceImpl<DcaBAuditfiveMapper, DcaBAuditfive> implements IDcaBAuditfiveService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBAuditfive> findDcaBAuditfives(QueryRequest request, DcaBAuditfive dcaBAuditfive) {
        try {
            LambdaQueryWrapper<DcaBAuditfive> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBAuditfive::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBAuditfive.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBAuditfive::getUserAccount, dcaBAuditfive.getUserAccount()).or()
                        .like(DcaBAuditfive::getUserAccountName, dcaBAuditfive.getUserAccount()));

            }
            if (dcaBAuditfive.getState() != null) {
                queryWrapper.eq(DcaBAuditfive::getState, dcaBAuditfive.getState());
            }
            /** if (dcaBAuditfive.getAuditState()!=null && (dcaBAuditfive.getAuditState()>=0)) {
             queryWrapper.eq(DcaBAuditfive::getAuditState, dcaBAuditfive.getAuditState());
             }*/
            if (StringUtils.isNotBlank(dcaBAuditfive.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBAuditfive.getAuditMan(), dcaBAuditfive.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBAuditfive::getUserAccount, userAccountsList);
            }
            if (StringUtils.isNotBlank(dcaBAuditfive.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAuditfive.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBAuditfive::getCreateTime, dcaBAuditfive.getCreateTimeFrom())
                        .le(DcaBAuditfive::getCreateTime, dcaBAuditfive.getCreateTimeTo());
            }
            if (dcaBAuditfive.getAuditXuhaoS() != null && (dcaBAuditfive.getAuditXuhaoS() > 0)) {
                if (dcaBAuditfive.getAuditXuhaoE() == null || dcaBAuditfive.getAuditXuhaoE().equals(0)) {
                    dcaBAuditfive.setAuditXuhaoE(dcaBAuditfive.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_auditfive.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBAuditfive.getAuditXuhaoS() + " and " + dcaBAuditfive.getAuditXuhaoE() + ")");
            }

            Page<DcaBAuditfive> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBAuditfive> result = this.page(page, queryWrapper);
            for (DcaBAuditfive item : result.getRecords()
            ) {
                List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    item.setAuditXuhao(list2.get(0).getPatentRanknum());
                }
            }
            return result;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaBAuditfive> findDcaBAuditfiveList(QueryRequest request, DcaBAuditfive dcaBAuditfive) {
        try {
            Page<DcaBAuditfive> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBAuditfive(page, dcaBAuditfive);
        } catch (Exception e) {
            log.error("获取近五年总体评价情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBAuditfive(DcaBAuditfive dcaBAuditfive) {
        dcaBAuditfive.setId(UUID.randomUUID().toString());
        dcaBAuditfive.setCreateTime(new Date());
        dcaBAuditfive.setIsDeletemark(1);
        this.save(dcaBAuditfive);
    }

    @Override
    @Transactional
    public void updateDcaBAuditfive(DcaBAuditfive dcaBAuditfive) {
        dcaBAuditfive.setModifyTime(new Date());
        this.baseMapper.updateDcaBAuditfive(dcaBAuditfive);
    }

    @Override
    @Transactional
    public void deleteDcaBAuditfives(String[] Ids) {
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