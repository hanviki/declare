package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBUndergraduate;
import cc.mrbird.febs.dca.dao.DcaBUndergraduateMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBUndergraduateService;
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
 * 本科教学情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBUndergraduateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUndergraduateServiceImpl extends ServiceImpl<DcaBUndergraduateMapper, DcaBUndergraduate> implements IDcaBUndergraduateService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBUndergraduate> findDcaBUndergraduates(QueryRequest request, DcaBUndergraduate dcaBUndergraduate) {
        try {
            LambdaQueryWrapper<DcaBUndergraduate> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUndergraduate::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUndergraduate.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUndergraduate::getUserAccount, dcaBUndergraduate.getUserAccount()).or()
                        .like(DcaBUndergraduate::getUserAccountName, dcaBUndergraduate.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUndergraduate.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBUndergraduate.getAuditMan(), dcaBUndergraduate.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBUndergraduate::getUserAccount, userAccountsList);
            }
            if (dcaBUndergraduate.getState() != null) {
                queryWrapper.eq(DcaBUndergraduate::getState, dcaBUndergraduate.getState());
            }
            if (StringUtils.isNotBlank(dcaBUndergraduate.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBUndergraduate.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBUndergraduate::getCreateTime, dcaBUndergraduate.getCreateTimeFrom())
                        .le(DcaBUndergraduate::getCreateTime, dcaBUndergraduate.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBUndergraduate.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBUndergraduate.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBUndergraduate::getModifyTime, dcaBUndergraduate.getModifyTimeFrom())
                        .le(DcaBUndergraduate::getModifyTime, dcaBUndergraduate.getModifyTimeTo());
            }
            if (dcaBUndergraduate.getAuditXuhaoS() != null && (dcaBUndergraduate.getAuditXuhaoS() > 0)) {
                if (dcaBUndergraduate.getAuditXuhaoE() == null || dcaBUndergraduate.getAuditXuhaoE().equals(0)) {
                    dcaBUndergraduate.setAuditXuhaoE(dcaBUndergraduate.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_undergraduate.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBUndergraduate.getAuditXuhaoS() + " and" +
                        " " + dcaBUndergraduate.getAuditXuhaoE() + ")");
            }

            Page<DcaBUndergraduate> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBUndergraduate> result = this.page(page, queryWrapper);
            for (DcaBUndergraduate item : result.getRecords()
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
    public IPage<DcaBUndergraduate> findDcaBUndergraduateList(QueryRequest request, DcaBUndergraduate dcaBUndergraduate) {
        try {
            Page<DcaBUndergraduate> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBUndergraduate(page, dcaBUndergraduate);
        } catch (Exception e) {
            log.error("获取本科教学情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate) {
        dcaBUndergraduate.setId(UUID.randomUUID().toString());
        dcaBUndergraduate.setCreateTime(new Date());
        dcaBUndergraduate.setIsDeletemark(1);
        this.save(dcaBUndergraduate);
    }

    @Override
    @Transactional
    public void updateDcaBUndergraduate(DcaBUndergraduate dcaBUndergraduate) {
        dcaBUndergraduate.setModifyTime(new Date());
        this.baseMapper.updateDcaBUndergraduate(dcaBUndergraduate);
    }

    @Override
    @Transactional
    public void deleteDcaBUndergraduates(String[] Ids) {
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