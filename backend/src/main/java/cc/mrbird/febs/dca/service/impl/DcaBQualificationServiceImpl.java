package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBQualification;
import cc.mrbird.febs.dca.dao.DcaBQualificationMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBQualificationService;
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
 * 资质情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-25
 */
@Slf4j
@Service("IDcaBQualificationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBQualificationServiceImpl extends ServiceImpl<DcaBQualificationMapper, DcaBQualification> implements IDcaBQualificationService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBQualification> findDcaBQualifications(QueryRequest request, DcaBQualification dcaBQualification) {
        try {
            LambdaQueryWrapper<DcaBQualification> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBQualification::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBQualification.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBQualification::getUserAccount, dcaBQualification.getUserAccount()).or()
                        .like(DcaBQualification::getUserAccountName, dcaBQualification.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBQualification.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBQualification.getAuditMan(), dcaBQualification.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBQualification::getUserAccount, userAccountsList);
            }
            if (dcaBQualification.getState() != null) {
                queryWrapper.eq(DcaBQualification::getState, dcaBQualification.getState());
            }
             if (dcaBQualification.getAuditState()!=null && (dcaBQualification.getAuditState()>=0)) {
             queryWrapper.eq(DcaBQualification::getAuditState, dcaBQualification.getAuditState());
             }
            if (StringUtils.isNotBlank(dcaBQualification.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBQualification.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBQualification::getCreateTime, dcaBQualification.getCreateTimeFrom())
                        .le(DcaBQualification::getCreateTime, dcaBQualification.getCreateTimeTo());
            }
            if (dcaBQualification.getAuditXuhaoS() != null && (dcaBQualification.getAuditXuhaoS() > 0)) {
                if (dcaBQualification.getAuditXuhaoE() == null || dcaBQualification.getAuditXuhaoE().equals(0)) {
                    dcaBQualification.setAuditXuhaoE(dcaBQualification.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_qualification.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBQualification.getAuditXuhaoS() + " and" +
                        " " + dcaBQualification.getAuditXuhaoE() + ")");
            }

            Page<DcaBQualification> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBQualification> result = this.page(page, queryWrapper);
            for (DcaBQualification item : result.getRecords()
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
    public IPage<DcaBQualification> findDcaBQualificationList(QueryRequest request, DcaBQualification dcaBQualification) {
        try {
            Page<DcaBQualification> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBQualification(page, dcaBQualification);
        } catch (Exception e) {
            log.error("获取资质情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBQualification(DcaBQualification dcaBQualification) {
        dcaBQualification.setId(UUID.randomUUID().toString());
        dcaBQualification.setCreateTime(new Date());
        dcaBQualification.setIsDeletemark(1);
        this.save(dcaBQualification);
    }

    @Override
    @Transactional
    public void updateDcaBQualification(DcaBQualification dcaBQualification) {
        dcaBQualification.setModifyTime(new Date());
        this.baseMapper.updateDcaBQualification(dcaBQualification);
    }

    @Override
    @Transactional
    public void deleteDcaBQualifications(String[] Ids) {
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