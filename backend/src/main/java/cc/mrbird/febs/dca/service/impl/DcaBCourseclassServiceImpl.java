package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBCourseclass;
import cc.mrbird.febs.dca.dao.DcaBCourseclassMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBCourseclassService;
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
 * 精品课程 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-03
 */
@Slf4j
@Service("IDcaBCourseclassService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCourseclassServiceImpl extends ServiceImpl<DcaBCourseclassMapper, DcaBCourseclass> implements IDcaBCourseclassService {

    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
    @Autowired
    private DcaBSciencepublishMapper dcaBSciencepublishMapper;

    @Override
public IPage<DcaBCourseclass> findDcaBCourseclasss(QueryRequest request, DcaBCourseclass dcaBCourseclass){
        try{
        LambdaQueryWrapper<DcaBCourseclass> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCourseclass::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCourseclass.getUserAccount())) {
                                queryWrapper.like(DcaBCourseclass::getUserAccount, dcaBCourseclass.getUserAccount());
                                }
            if(StringUtils.isNotBlank(dcaBCourseclass.getAuditManName())){// 年度 和高级、中级、初级
                List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBCourseclass.getAuditMan(),dcaBCourseclass.getAuditManName());
                if(userAccountsList.size()==0){
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBCourseclass::getUserAccount,userAccountsList);
            }
                                if (dcaBCourseclass.getState()!=null) {
                                queryWrapper.eq(DcaBCourseclass::getState, dcaBCourseclass.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBCourseclass.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBCourseclass.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBCourseclass::getCreateTime, dcaBCourseclass.getCreateTimeFrom())
                                .le(DcaBCourseclass::getCreateTime, dcaBCourseclass.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBCourseclass.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBCourseclass.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBCourseclass::getModifyTime, dcaBCourseclass.getModifyTimeFrom())
                                .le(DcaBCourseclass::getModifyTime, dcaBCourseclass.getModifyTimeTo());
                                }
            if (dcaBCourseclass.getAuditXuhaoS() != null && (dcaBCourseclass.getAuditXuhaoS() > 0)) {
                if (dcaBCourseclass.getAuditXuhaoE() == null || dcaBCourseclass.getAuditXuhaoE().equals(0)) {
                    dcaBCourseclass.setAuditXuhaoE(dcaBCourseclass.getAuditXuhaoS());
                }
                queryWrapper.apply(" dca_b_courseclass.user_account in (select user_account from dca_b_user where patent_ranknum between " + dcaBCourseclass.getAuditXuhaoS() + " and " + dcaBCourseclass.getAuditXuhaoE() + ")");
            }

        Page<DcaBCourseclass> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
            List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
            IPage<DcaBCourseclass> result = this.page(page, queryWrapper);
            for (DcaBCourseclass item : result.getRecords()
            ) {
                List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                if (list2.size() > 0) {
                    item.setAuditXuhao(list2.get(0).getPatentRanknum());
                }
            }
            return  result;
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCourseclass> findDcaBCourseclassList (QueryRequest request, DcaBCourseclass dcaBCourseclass){
        try{
        Page<DcaBCourseclass> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCourseclass(page,dcaBCourseclass);
        }catch(Exception e){
        log.error("获取精品课程失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCourseclass(DcaBCourseclass dcaBCourseclass){
                dcaBCourseclass.setId(UUID.randomUUID().toString());
        dcaBCourseclass.setCreateTime(new Date());
        dcaBCourseclass.setIsDeletemark(1);
        this.save(dcaBCourseclass);
        }

@Override
@Transactional
public void updateDcaBCourseclass(DcaBCourseclass dcaBCourseclass){
        dcaBCourseclass.setModifyTime(new Date());
        this.baseMapper.updateDcaBCourseclass(dcaBCourseclass);
        }

@Override
@Transactional
public void deleteDcaBCourseclasss(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }