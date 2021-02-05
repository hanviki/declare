package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBParttimejob;
import cc.mrbird.febs.dca.dao.DcaBParttimejobMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBParttimejobService;
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
 * 社会兼职表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBParttimejobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBParttimejobServiceImpl extends ServiceImpl<DcaBParttimejobMapper, DcaBParttimejob> implements IDcaBParttimejobService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
    public IPage<DcaBParttimejob> findDcaBParttimejobs(QueryRequest request, DcaBParttimejob dcaBParttimejob) {
        try {
            LambdaQueryWrapper<DcaBParttimejob> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBParttimejob::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBParttimejob.getUserAccount())) {
                queryWrapper.and(wrap -> wrap.eq(DcaBParttimejob::getUserAccount, dcaBParttimejob.getUserAccount()).or()
                        .like(DcaBParttimejob::getUserAccountName, dcaBParttimejob.getUserAccount()));

            }
            if (StringUtils.isNotBlank(dcaBParttimejob.getAuditManName())) {// 年度 和高级、中级、初级
                List<String> userAccountsList = this.iDcaBUserapplyService.getApplyAccount(dcaBParttimejob.getAuditMan(), dcaBParttimejob.getAuditManName());
                if (userAccountsList.size() == 0) {
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBParttimejob::getUserAccount, userAccountsList);
            }
            if (dcaBParttimejob.getState() != null) {
                queryWrapper.eq(DcaBParttimejob::getState, dcaBParttimejob.getState());
            }
            if (StringUtils.isNotBlank(dcaBParttimejob.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBParttimejob.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaBParttimejob::getCreateTime, dcaBParttimejob.getCreateTimeFrom())
                        .le(DcaBParttimejob::getCreateTime, dcaBParttimejob.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaBParttimejob.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBParttimejob.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaBParttimejob::getModifyTime, dcaBParttimejob.getModifyTimeFrom())
                        .le(DcaBParttimejob::getModifyTime, dcaBParttimejob.getModifyTimeTo());
            }
            if (dcaBParttimejob.getAuditXuhaoS() != null && (dcaBParttimejob.getAuditXuhaoS() > 0)) {
                if (dcaBParttimejob.getAuditXuhaoE() == null || dcaBParttimejob.getAuditXuhaoE().equals(0)) {
                    dcaBParttimejob.setAuditXuhaoE(dcaBParttimejob.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_parttimejob.user_account in " +
                        "(select user_account from dca_b_user where patent_ranknum" +
                        " between " + dcaBParttimejob.getAuditXuhaoS() + " and" +
                        " " + dcaBParttimejob.getAuditXuhaoE() + ")");
            }

            Page<DcaBParttimejob> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBParttimejob> result = this.page(page, queryWrapper);
            for (DcaBParttimejob item : result.getRecords()
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
    public IPage<DcaBParttimejob> findDcaBParttimejobList(QueryRequest request, DcaBParttimejob dcaBParttimejob) {
        try {
            Page<DcaBParttimejob> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaBParttimejob(page, dcaBParttimejob);
        } catch (Exception e) {
            log.error("获取社会兼职表失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaBParttimejob(DcaBParttimejob dcaBParttimejob) {
        dcaBParttimejob.setId(UUID.randomUUID().toString());
        dcaBParttimejob.setCreateTime(new Date());
        dcaBParttimejob.setIsDeletemark(1);
        this.save(dcaBParttimejob);
    }

    @Override
    @Transactional
    public void updateDcaBParttimejob(DcaBParttimejob dcaBParttimejob) {
        dcaBParttimejob.setModifyTime(new Date());
        this.baseMapper.updateDcaBParttimejob(dcaBParttimejob);
    }

    @Override
    @Transactional
    public void deleteDcaBParttimejobs(String[] Ids) {
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