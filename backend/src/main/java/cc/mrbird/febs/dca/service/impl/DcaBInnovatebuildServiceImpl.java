package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBInnovatebuild;
import cc.mrbird.febs.dca.dao.DcaBInnovatebuildMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBInnovatebuildService;
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
 * 任现职以来承担的本科教学改革及建设项目(教师系列需填写) 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
@Slf4j
@Service("IDcaBInnovatebuildService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBInnovatebuildServiceImpl extends ServiceImpl<DcaBInnovatebuildMapper, DcaBInnovatebuild> implements IDcaBInnovatebuildService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBInnovatebuild> findDcaBInnovatebuilds(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild) {
        try {
            LambdaQueryWrapper<DcaBInnovatebuild> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBInnovatebuild::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBInnovatebuild.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBInnovatebuild::getUserAccount, dcaBInnovatebuild.getUserAccount()).or()
                        .like(DcaBInnovatebuild::getUserAccountName, dcaBInnovatebuild.getUserAccount()));

            }

            if (StringUtils.isNotBlank(dcaBInnovatebuild.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBInnovatebuild.getAuditMan(), dcaBInnovatebuild.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBInnovatebuild::getUserAccount, userAccountsList);
            }

            if (dcaBInnovatebuild.getState() != null) {
                queryWrapper.eq(DcaBInnovatebuild::getState, dcaBInnovatebuild.getState());
            }
            if (dcaBInnovatebuild.getAuditState() != null && (dcaBInnovatebuild.getAuditState() >= 0)) {
                queryWrapper.eq(DcaBInnovatebuild::getAuditState, dcaBInnovatebuild.getAuditState());
            }
            if (StringUtils.isNotBlank(dcaBInnovatebuild.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBInnovatebuild.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBInnovatebuild::getCreateTime, dcaBInnovatebuild.getCreateTimeFrom())
                        .le(DcaBInnovatebuild::getCreateTime, dcaBInnovatebuild.getCreateTimeTo());
            }
            if (dcaBInnovatebuild.getAuditXuhaoS() != null && (dcaBInnovatebuild.getAuditXuhaoS() > 0)) {
                if (dcaBInnovatebuild.getAuditXuhaoE() == null || dcaBInnovatebuild.getAuditXuhaoE().equals(0)) {
                    dcaBInnovatebuild.setAuditXuhaoE(dcaBInnovatebuild.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_innovatebuild.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBInnovatebuild.getAuditXuhaoS() + " and" +
                        " " + dcaBInnovatebuild.getAuditXuhaoE() + ")");
            }

            Page<DcaBInnovatebuild> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBInnovatebuild> result = this.page(page, queryWrapper);
            for (DcaBInnovatebuild item : result.getRecords()
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
    public IPage<DcaBInnovatebuild> findDcaBInnovatebuildList(QueryRequest request, DcaBInnovatebuild dcaBInnovatebuild) {
        try {
            Page<DcaBInnovatebuild> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBInnovatebuild(page, dcaBInnovatebuild);
        } catch (Exception e) {
            log.error("获取任现职以来承担的本科教学改革及建设项目(教师系列需填写)失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild) {
        dcaBInnovatebuild.setId(UUID.randomUUID().toString());
        dcaBInnovatebuild.setCreateTime(new Date());
        dcaBInnovatebuild.setIsDeletemark(1);
        this.save(dcaBInnovatebuild);
    }

    @Override
    @Transactional
    public void updateDcaBInnovatebuild(DcaBInnovatebuild dcaBInnovatebuild) {
        dcaBInnovatebuild.setModifyTime(new Date());
        this.baseMapper.updateDcaBInnovatebuild(dcaBInnovatebuild);
    }

    @Override
    @Transactional
    public void deleteDcaBInnovatebuilds(String[] Ids) {
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