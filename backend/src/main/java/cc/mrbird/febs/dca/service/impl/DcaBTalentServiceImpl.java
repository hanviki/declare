package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBTalent;
import cc.mrbird.febs.dca.dao.DcaBTalentMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBTalentService;
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
 * 任现职以来完成研究生教学人才培养情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBTalentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBTalentServiceImpl extends ServiceImpl<DcaBTalentMapper, DcaBTalent> implements IDcaBTalentService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBTalent> findDcaBTalents(QueryRequest request, DcaBTalent dcaBTalent) {
        try {
            LambdaQueryWrapper<DcaBTalent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBTalent::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBTalent.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBTalent::getUserAccount, dcaBTalent.getUserAccount()).or()
                        .like(DcaBTalent::getUserAccountName, dcaBTalent.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBTalent.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBTalent.getAuditMan(), dcaBTalent.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBTalent::getUserAccount, userAccountsList);
            }
            if (dcaBTalent.getState() != null) {
                queryWrapper.eq(DcaBTalent::getState, dcaBTalent.getState());
            }
            if (StringUtils.isNotBlank(dcaBTalent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBTalent.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBTalent::getCreateTime, dcaBTalent.getCreateTimeFrom())
                        .le(DcaBTalent::getCreateTime, dcaBTalent.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBTalent.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBTalent.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBTalent::getModifyTime, dcaBTalent.getModifyTimeFrom())
                        .le(DcaBTalent::getModifyTime, dcaBTalent.getModifyTimeTo());
            }
            if (dcaBTalent.getAuditXuhaoS() != null && (dcaBTalent.getAuditXuhaoS() > 0)) {
                if (dcaBTalent.getAuditXuhaoE() == null || dcaBTalent.getAuditXuhaoE().equals(0)) {
                    dcaBTalent.setAuditXuhaoE(dcaBTalent.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_talent.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBTalent.getAuditXuhaoS() + " and" +
                        " " + dcaBTalent.getAuditXuhaoE() + ")");
            }

            Page<DcaBTalent> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBTalent> result = this.page(page, queryWrapper);
            for (DcaBTalent item : result.getRecords()
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
    public IPage<DcaBTalent> findDcaBTalentList(QueryRequest request, DcaBTalent dcaBTalent) {
        try {
            Page<DcaBTalent> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBTalent(page, dcaBTalent);
        } catch (Exception e) {
            log.error("获取任现职以来完成研究生教学人才培养情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBTalent(DcaBTalent dcaBTalent) {
        dcaBTalent.setId(UUID.randomUUID().toString());
        dcaBTalent.setCreateTime(new Date());
        dcaBTalent.setIsDeletemark(1);
        this.save(dcaBTalent);
    }

    @Override
    @Transactional
    public void updateDcaBTalent(DcaBTalent dcaBTalent) {
        dcaBTalent.setModifyTime(new Date());
        this.baseMapper.updateDcaBTalent(dcaBTalent);
    }

    @Override
    @Transactional
    public void deleteDcaBTalents(String[] Ids) {
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