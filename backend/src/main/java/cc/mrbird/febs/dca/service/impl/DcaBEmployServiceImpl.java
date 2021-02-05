package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBEmploy;
import cc.mrbird.febs.dca.dao.DcaBEmployMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBEmployService;
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
 * 任职培养 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBEmployService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBEmployServiceImpl extends ServiceImpl<DcaBEmployMapper, DcaBEmploy> implements IDcaBEmployService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBEmploy> findDcaBEmploys(QueryRequest request, DcaBEmploy dcaBEmploy) {
        try {
            LambdaQueryWrapper<DcaBEmploy> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBEmploy::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBEmploy.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBEmploy::getUserAccount, dcaBEmploy.getUserAccount()).or().like(DcaBEmploy::getUserAccountName, dcaBEmploy.getUserAccount()));
            }
            if (StringUtils.isNotBlank(dcaBEmploy.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBEmploy.getAuditMan(), dcaBEmploy.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBEmploy::getUserAccount, userAccountsList);
            }
            if (dcaBEmploy.getState() != null) {
                queryWrapper.eq(DcaBEmploy::getState, dcaBEmploy.getState());
            }
            if (StringUtils.isNotBlank(dcaBEmploy.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBEmploy.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBEmploy::getCreateTime, dcaBEmploy.getCreateTimeFrom())
                        .le(DcaBEmploy::getCreateTime, dcaBEmploy.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBEmploy.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBEmploy.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBEmploy::getModifyTime, dcaBEmploy.getModifyTimeFrom())
                        .le(DcaBEmploy::getModifyTime, dcaBEmploy.getModifyTimeTo());
            }
            if (dcaBEmploy.getAuditXuhaoS() != null && (dcaBEmploy.getAuditXuhaoS() > 0)) {
                if (dcaBEmploy.getAuditXuhaoE() == null || dcaBEmploy.getAuditXuhaoE().equals(0)) {
                    dcaBEmploy.setAuditXuhaoE(dcaBEmploy.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_employ.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBEmploy.getAuditXuhaoS() + " and " + dcaBEmploy.getAuditXuhaoE() + ")");
            }

            Page<DcaBEmploy> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBEmploy> result = this.page(page, queryWrapper);
            for (DcaBEmploy item : result.getRecords()
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
    public IPage<DcaBEmploy> findDcaBEmployList(QueryRequest request, DcaBEmploy dcaBEmploy) {
        try {
            Page<DcaBEmploy> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBEmploy(page, dcaBEmploy);
        } catch (Exception e) {
            log.error("获取任职培养失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBEmploy(DcaBEmploy dcaBEmploy) {
        dcaBEmploy.setId(UUID.randomUUID().toString());
        dcaBEmploy.setCreateTime(new Date());
        dcaBEmploy.setIsDeletemark(1);
        this.save(dcaBEmploy);
    }

    @Override
    @Transactional
    public void updateDcaBEmploy(DcaBEmploy dcaBEmploy) {
        dcaBEmploy.setModifyTime(new Date());
        this.baseMapper.updateDcaBEmploy(dcaBEmploy);
    }

    @Override
    @Transactional
    public void deleteDcaBEmploys(String[] Ids) {
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