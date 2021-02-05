package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBPatent;
import cc.mrbird.febs.dca.dao.DcaBPatentMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBPatentService;
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
 * 申请专利情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBPatentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBPatentServiceImpl extends ServiceImpl<DcaBPatentMapper, DcaBPatent> implements IDcaBPatentService {


    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBPatent> findDcaBPatents(QueryRequest request, DcaBPatent dcaBPatent) {
        try {
            LambdaQueryWrapper<DcaBPatent> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBPatent::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBPatent.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBPatent::getUserAccount, dcaBPatent.getUserAccount()).or()
                        .like(DcaBPatent::getUserAccountName, dcaBPatent.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBPatent.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBPatent.getAuditMan(), dcaBPatent.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBPatent::getUserAccount, userAccountsList);
            }
            if (dcaBPatent.getState() != null) {
                queryWrapper.eq(DcaBPatent::getState, dcaBPatent.getState());
            }
            if (StringUtils.isNotBlank(dcaBPatent.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBPatent.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBPatent::getCreateTime, dcaBPatent.getCreateTimeFrom())
                        .le(DcaBPatent::getCreateTime, dcaBPatent.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBPatent.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBPatent.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBPatent::getModifyTime, dcaBPatent.getModifyTimeFrom())
                        .le(DcaBPatent::getModifyTime, dcaBPatent.getModifyTimeTo());
            }
            if (dcaBPatent.getAuditXuhaoS() != null && (dcaBPatent.getAuditXuhaoS() > 0)) {
                if (dcaBPatent.getAuditXuhaoE() == null || dcaBPatent.getAuditXuhaoE().equals(0)) {
                    dcaBPatent.setAuditXuhaoE(dcaBPatent.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_patent.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBPatent.getAuditXuhaoS() + " and" +
                        " " + dcaBPatent.getAuditXuhaoE() + ")");
            }

            Page<DcaBPatent> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBPatent> result = this.page(page, queryWrapper);
            for (DcaBPatent item : result.getRecords()
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
    public IPage<DcaBPatent> findDcaBPatentList(QueryRequest request, DcaBPatent dcaBPatent) {
        try {
            Page<DcaBPatent> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBPatent(page, dcaBPatent);
        } catch (Exception e) {
            log.error("获取申请专利情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBPatent(DcaBPatent dcaBPatent) {
        dcaBPatent.setId(UUID.randomUUID().toString());
        dcaBPatent.setCreateTime(new Date());
        dcaBPatent.setIsDeletemark(1);
        this.save(dcaBPatent);
    }

    @Override
    @Transactional
    public void updateDcaBPatent(DcaBPatent dcaBPatent) {
        dcaBPatent.setModifyTime(new Date());
        this.baseMapper.updateDcaBPatent(dcaBPatent);
    }

    @Override
    @Transactional
    public void deleteDcaBPatents(String[] Ids) {
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