package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBAcademic;
import cc.mrbird.febs.dca.dao.DcaBAcademicMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBAcademicService;
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
 * 重要岗位任职及学术影响 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-12-15
 */
@Slf4j
@Service("IDcaBAcademicService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAcademicServiceImpl extends ServiceImpl<DcaBAcademicMapper, DcaBAcademic> implements IDcaBAcademicService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBAcademic> findDcaBAcademics(QueryRequest request, DcaBAcademic dcaBAcademic) {
        try {
            LambdaQueryWrapper<DcaBAcademic> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBAcademic::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBAcademic.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBAcademic::getUserAccount, dcaBAcademic.getUserAccount()).or()
                        .like(DcaBAcademic::getUserAccountName, dcaBAcademic.getUserAccount()));

            }
            if (dcaBAcademic.getState() != null) {
                queryWrapper.eq(DcaBAcademic::getState, dcaBAcademic.getState());
            }
            /** if (dcaBAcademic.getAuditState()!=null && (dcaBAcademic.getAuditState()>=0)) {
             queryWrapper.eq(DcaBAcademic::getAuditState, dcaBAcademic.getAuditState());
             }*/
            if (StringUtils.isNotBlank(dcaBAcademic.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBAcademic.getAuditMan(), dcaBAcademic.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBAcademic::getUserAccount, userAccountsList);
            }
            if (dcaBAcademic.getAuditXuhaoS() != null && (dcaBAcademic.getAuditXuhaoS() > 0)) {
                if (dcaBAcademic.getAuditXuhaoE() == null || dcaBAcademic.getAuditXuhaoE().equals(0)) {
                    dcaBAcademic.setAuditXuhaoE(dcaBAcademic.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_academic.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBAcademic.getAuditXuhaoS() + " and " + dcaBAcademic.getAuditXuhaoE() + ")");
            }
            if (StringUtils.isNotBlank(dcaBAcademic.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAcademic.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBAcademic::getCreateTime, dcaBAcademic.getCreateTimeFrom())
                        .le(DcaBAcademic::getCreateTime, dcaBAcademic.getCreateTimeTo());
            }

            Page<DcaBAcademic> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBAcademic> result = this.page(page, queryWrapper);
            for (DcaBAcademic item : result.getRecords()
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
    public IPage<DcaBAcademic> findDcaBAcademicList(QueryRequest request, DcaBAcademic dcaBAcademic) {
        try {
            Page<DcaBAcademic> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBAcademic(page, dcaBAcademic);
        } catch (Exception e) {
            log.error("获取重要岗位任职及学术影响失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBAcademic(DcaBAcademic dcaBAcademic) {
        dcaBAcademic.setId(UUID.randomUUID().toString());
        dcaBAcademic.setCreateTime(new Date());
        dcaBAcademic.setIsDeletemark(1);
        this.save(dcaBAcademic);
    }

    @Override
    @Transactional
    public void updateDcaBAcademic(DcaBAcademic dcaBAcademic) {
        dcaBAcademic.setModifyTime(new Date());
        this.baseMapper.updateDcaBAcademic(dcaBAcademic);
    }

    @Override
    @Transactional
    public void deleteDcaBAcademics(String[] Ids) {
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