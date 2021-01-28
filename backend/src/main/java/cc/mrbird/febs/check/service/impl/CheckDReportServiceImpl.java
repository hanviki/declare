package cc.mrbird.febs.check.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.check.entity.CheckDReport;
import cc.mrbird.febs.check.dao.CheckDReportMapper;
import cc.mrbird.febs.check.service.ICheckDReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-27
 */
@Slf4j
@Service("ICheckDReportService")
@DS("slave")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckDReportServiceImpl extends ServiceImpl<CheckDReportMapper, CheckDReport> implements ICheckDReportService {


@Override
@DS("slave")
public IPage<CheckDReport> findCheckDReports(QueryRequest request, CheckDReport checkDReport){
        try{
        LambdaQueryWrapper<CheckDReport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CheckDReport::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(checkDReport.getDcaYear())) {
                                queryWrapper.like(CheckDReport::getDcaYear, checkDReport.getDcaYear());
                                }

        Page<CheckDReport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<CheckDReport> findCheckDReportList (QueryRequest request, CheckDReport checkDReport){
        try{
        Page<CheckDReport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findCheckDReport(page,checkDReport);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createCheckDReport(CheckDReport checkDReport){
                checkDReport.setCreateTime(new Date());
        checkDReport.setIsDeletemark(1);
        this.save(checkDReport);
        }

@Override
@Transactional
@DS("slave")
public void updateCheckDReport(CheckDReport checkDReport){
        checkDReport.setModifyTime(new Date());
        this.baseMapper.updateCheckDReport(checkDReport);
        }

@Override
@Transactional
@DS("slave")
public void deleteCheckDReports(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public List<CheckDReport> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<CheckDReport> queryWrapper=new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(userAccount) && StringUtils.isNotBlank(dcaYear)) {
                queryWrapper.apply("(LENGTH(check_d_report.lb) = 0\n" +
                        "OR EXISTS (\n" +
                        "\tSELECT\n" +
                        "\t\tid\n" +
                        "\tFROM\n" +
                        "\t\tcheck_b_user\n" +
                        "\tWHERE\n" +
                        "\t\tLOCATE(\n" +
                        "\t\t\tcheck_b_user.zjlb,\n" +
                        "\t\t\tcheck_d_report.lb\n" +
                        "\t\t) > 0\n" +
                        "\tAND check_b_user.dca_year = '"+dcaYear+"'\n" +
                        "\tAND check_b_user.user_account =  '"+userAccount+"'\n" +
                        "))");
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(CheckDReport::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }