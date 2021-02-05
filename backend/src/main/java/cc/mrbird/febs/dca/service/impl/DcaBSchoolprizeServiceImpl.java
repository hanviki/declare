package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBSchoolprize;
import cc.mrbird.febs.dca.dao.DcaBSchoolprizeMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBSchoolprizeService;
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
 * 校教学质量奖、校教学成果奖 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
@Slf4j
@Service("IDcaBSchoolprizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBSchoolprizeServiceImpl extends ServiceImpl<DcaBSchoolprizeMapper, DcaBSchoolprize> implements IDcaBSchoolprizeService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBSchoolprize> findDcaBSchoolprizes(QueryRequest request, DcaBSchoolprize dcaBSchoolprize) {
        try {
            LambdaQueryWrapper<DcaBSchoolprize> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBSchoolprize::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBSchoolprize.getUserAccount())) {
                queryWrapper.like(DcaBSchoolprize::getUserAccount, dcaBSchoolprize.getUserAccount());
            }

            if (StringUtils.isNotBlank(dcaBSchoolprize.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBSchoolprize.getAuditMan(), dcaBSchoolprize.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBSchoolprize::getUserAccount, userAccountsList);
            }

            if (dcaBSchoolprize.getState() != null) {
                queryWrapper.eq(DcaBSchoolprize::getState, dcaBSchoolprize.getState());
            }
            if (StringUtils.isNotBlank(dcaBSchoolprize.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBSchoolprize.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBSchoolprize::getCreateTime, dcaBSchoolprize.getCreateTimeFrom())
                        .le(DcaBSchoolprize::getCreateTime, dcaBSchoolprize.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBSchoolprize.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBSchoolprize.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBSchoolprize::getModifyTime, dcaBSchoolprize.getModifyTimeFrom())
                        .le(DcaBSchoolprize::getModifyTime, dcaBSchoolprize.getModifyTimeTo());
            }
            if (dcaBSchoolprize.getAuditXuhaoS() != null && (dcaBSchoolprize.getAuditXuhaoS() > 0)) {
                if (dcaBSchoolprize.getAuditXuhaoE() == null || dcaBSchoolprize.getAuditXuhaoE().equals(0)) {
                    dcaBSchoolprize.setAuditXuhaoE(dcaBSchoolprize.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_schoolprize.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBSchoolprize.getAuditXuhaoS() + " and" +
                        " " + dcaBSchoolprize.getAuditXuhaoE() + ")");
            }

            Page<DcaBSchoolprize> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBSchoolprize> result = this.page(page, queryWrapper);
            for (DcaBSchoolprize item : result.getRecords()
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
    public IPage<DcaBSchoolprize> findDcaBSchoolprizeList(QueryRequest request, DcaBSchoolprize dcaBSchoolprize) {
        try {
            Page<DcaBSchoolprize> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBSchoolprize(page, dcaBSchoolprize);
        } catch (Exception e) {
            log.error("获取校教学质量奖、校教学成果奖失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize) {
        dcaBSchoolprize.setId(UUID.randomUUID().toString());
        dcaBSchoolprize.setCreateTime(new Date());
        dcaBSchoolprize.setIsDeletemark(1);
        this.save(dcaBSchoolprize);
    }

    @Override
    @Transactional
    public void updateDcaBSchoolprize(DcaBSchoolprize dcaBSchoolprize) {
        dcaBSchoolprize.setModifyTime(new Date());
        this.baseMapper.updateDcaBSchoolprize(dcaBSchoolprize);
    }

    @Override
    @Transactional
    public void deleteDcaBSchoolprizes(String[] Ids) {
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