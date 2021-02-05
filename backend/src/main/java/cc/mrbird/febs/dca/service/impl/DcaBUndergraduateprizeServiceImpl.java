package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBUndergraduateprize;
import cc.mrbird.febs.dca.dao.DcaBUndergraduateprizeMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBUndergraduateprizeService;
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
 * 任现职以来本科教学工作获奖情况 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBUndergraduateprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUndergraduateprizeServiceImpl extends ServiceImpl<DcaBUndergraduateprizeMapper, DcaBUndergraduateprize> implements IDcaBUndergraduateprizeService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBUndergraduateprize> findDcaBUndergraduateprizes(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize) {
        try {
            LambdaQueryWrapper<DcaBUndergraduateprize> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBUndergraduateprize::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUndergraduateprize.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBUndergraduateprize::getUserAccount, dcaBUndergraduateprize.getUserAccount()).or()
                        .like(DcaBUndergraduateprize::getUserAccountName, dcaBUndergraduateprize.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBUndergraduateprize.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBUndergraduateprize.getAuditMan(), dcaBUndergraduateprize.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBUndergraduateprize::getUserAccount, userAccountsList);
            }
            if (dcaBUndergraduateprize.getState() != null) {
                queryWrapper.eq(DcaBUndergraduateprize::getState, dcaBUndergraduateprize.getState());
            }
            if (StringUtils.isNotBlank(dcaBUndergraduateprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBUndergraduateprize.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBUndergraduateprize::getCreateTime, dcaBUndergraduateprize.getCreateTimeFrom())
                        .le(DcaBUndergraduateprize::getCreateTime, dcaBUndergraduateprize.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBUndergraduateprize.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBUndergraduateprize.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBUndergraduateprize::getModifyTime, dcaBUndergraduateprize.getModifyTimeFrom())
                        .le(DcaBUndergraduateprize::getModifyTime, dcaBUndergraduateprize.getModifyTimeTo());
            }
            if (dcaBUndergraduateprize.getAuditXuhaoS() != null && (dcaBUndergraduateprize.getAuditXuhaoS() > 0)) {
                if (dcaBUndergraduateprize.getAuditXuhaoE() == null || dcaBUndergraduateprize.getAuditXuhaoE().equals(0)) {
                    dcaBUndergraduateprize.setAuditXuhaoE(dcaBUndergraduateprize.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_undergraduateprize.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBUndergraduateprize.getAuditXuhaoS() + " and" +
                        " " + dcaBUndergraduateprize.getAuditXuhaoE() + ")");
            }


            Page<DcaBUndergraduateprize> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBUndergraduateprize> result = this.page(page, queryWrapper);
            for (DcaBUndergraduateprize item : result.getRecords()
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
    public IPage<DcaBUndergraduateprize> findDcaBUndergraduateprizeList(QueryRequest request, DcaBUndergraduateprize dcaBUndergraduateprize) {
        try {
            Page<DcaBUndergraduateprize> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBUndergraduateprize(page, dcaBUndergraduateprize);
        } catch (Exception e) {
            log.error("获取任现职以来本科教学工作获奖情况失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBUndergraduateprize(DcaBUndergraduateprize dcaBUndergraduateprize) {
        dcaBUndergraduateprize.setId(UUID.randomUUID().toString());
        dcaBUndergraduateprize.setCreateTime(new Date());
        dcaBUndergraduateprize.setIsDeletemark(1);
        this.save(dcaBUndergraduateprize);
    }

    @Override
    @Transactional
    public void updateDcaBUndergraduateprize(DcaBUndergraduateprize dcaBUndergraduateprize) {
        dcaBUndergraduateprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBUndergraduateprize(dcaBUndergraduateprize);
    }

    @Override
    @Transactional
    public void deleteDcaBUndergraduateprizes(String[] Ids) {
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