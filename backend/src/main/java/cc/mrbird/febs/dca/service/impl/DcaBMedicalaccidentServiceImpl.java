package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBMedicalaccident;
import cc.mrbird.febs.dca.dao.DcaBMedicalaccidentMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBMedicalaccidentService;
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
 * 担任博导硕导 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBMedicalaccidentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBMedicalaccidentServiceImpl extends ServiceImpl<DcaBMedicalaccidentMapper, DcaBMedicalaccident> implements IDcaBMedicalaccidentService {


    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBMedicalaccident> findDcaBMedicalaccidents(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident) {
        try {
            LambdaQueryWrapper<DcaBMedicalaccident> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBMedicalaccident::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBMedicalaccident.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBMedicalaccident::getUserAccount, dcaBMedicalaccident.getUserAccount()).or()
                        .like(DcaBMedicalaccident::getUserAccountName, dcaBMedicalaccident.getUserAccount()));

            }
            if (dcaBMedicalaccident.getState() != null) {
                queryWrapper.eq(DcaBMedicalaccident::getState, dcaBMedicalaccident.getState());
            }
            /** if (dcaBMedicalaccident.getAuditState()!=null && (dcaBMedicalaccident.getAuditState()>=0)) {
             queryWrapper.eq(DcaBMedicalaccident::getAuditState, dcaBMedicalaccident.getAuditState());
             }*/

            if (StringUtils.isNotBlank(dcaBMedicalaccident.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBMedicalaccident.getAuditMan(), dcaBMedicalaccident.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBMedicalaccident::getUserAccount, userAccountsList);
            }
            if (StringUtils.isNotBlank(dcaBMedicalaccident.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBMedicalaccident.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBMedicalaccident::getCreateTime, dcaBMedicalaccident.getCreateTimeFrom())
                        .le(DcaBMedicalaccident::getCreateTime, dcaBMedicalaccident.getCreateTimeTo());
            }
            if (dcaBMedicalaccident.getAuditXuhaoS() != null && (dcaBMedicalaccident.getAuditXuhaoS() > 0)) {
                if (dcaBMedicalaccident.getAuditXuhaoE() == null || dcaBMedicalaccident.getAuditXuhaoE().equals(0)) {
                    dcaBMedicalaccident.setAuditXuhaoE(dcaBMedicalaccident.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_medicalaccident.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBMedicalaccident.getAuditXuhaoS() + " and" +
                        " " + dcaBMedicalaccident.getAuditXuhaoE() + ")");
            }


            Page<DcaBMedicalaccident> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBMedicalaccident> result = this.page(page, queryWrapper);
            for (DcaBMedicalaccident item : result.getRecords()
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
    public IPage<DcaBMedicalaccident> findDcaBMedicalaccidentList(QueryRequest request, DcaBMedicalaccident dcaBMedicalaccident) {
        try {
            Page<DcaBMedicalaccident> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBMedicalaccident(page, dcaBMedicalaccident);
        } catch (Exception e) {
            log.error("获取担任博导硕导失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBMedicalaccident(DcaBMedicalaccident dcaBMedicalaccident) {
        dcaBMedicalaccident.setId(UUID.randomUUID().toString());
        dcaBMedicalaccident.setCreateTime(new Date());
        dcaBMedicalaccident.setIsDeletemark(1);
        this.save(dcaBMedicalaccident);
    }

    @Override
    @Transactional
    public void updateDcaBMedicalaccident(DcaBMedicalaccident dcaBMedicalaccident) {
        dcaBMedicalaccident.setModifyTime(new Date());
        this.baseMapper.updateDcaBMedicalaccident(dcaBMedicalaccident);
    }

    @Override
    @Transactional
    public void deleteDcaBMedicalaccidents(String[] Ids) {
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