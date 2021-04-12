package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.dao.DcaBReportMapper;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cn.hutool.core.collection.CollUtil;
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

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-13
 */
@Slf4j
@Service("IDcaBReportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBReportServiceImpl extends ServiceImpl<DcaBReportMapper, DcaBReport> implements IDcaBReportService {


@Override
public IPage<DcaBReport> findDcaBReports(QueryRequest request, DcaBReport dcaBReport){
        try{
        LambdaQueryWrapper<DcaBReport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark, 1);//1是未删 0是已删


                if (StringUtils.isNotBlank(dcaBReport.getUserAccount())) {
                        queryWrapper.and(wrap->  wrap.eq(DcaBReport::getUserAccount, dcaBReport.getUserAccount()).or()
                                .like(DcaBReport::getUserAccountName, dcaBReport.getUserAccount()));

                }
               if (dcaBReport.getState()!=null) {
                        queryWrapper.eq(DcaBReport::getState, dcaBReport.getState());
                }
               if(StringUtils.isNotBlank(dcaBReport.getIsSingel())){
                       queryWrapper.in(DcaBReport::getState, CollUtil.toList(1,2));
               }
                if (StringUtils.isNotBlank(dcaBReport.getYear())) {
                        queryWrapper.eq(DcaBReport::getYear, dcaBReport.getYear().trim());
                }
            if (StringUtils.isNotBlank(dcaBReport.getKs())) {
                String[] listKs=dcaBReport.getKs().split(",");
                queryWrapper.in(DcaBReport::getGwdj,listKs);
            }

                Page<DcaBReport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBReport> findDcaBReportList (QueryRequest request, DcaBReport dcaBReport){
        try{
        Page<DcaBReport> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBReport(page,dcaBReport);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBReport(DcaBReport dcaBReport){
                dcaBReport.setId(UUID.randomUUID().toString());
        dcaBReport.setCreateTime(new Date());
        dcaBReport.setIsDeletemark(1);
        this.save(dcaBReport);
        }

@Override
    @Transactional
    public void updateDcaBReport(DcaBReport dcaBReport){
        dcaBReport.setModifyTime(new Date());
        this.baseMapper.updateDcaBReport(dcaBReport);
        if(dcaBReport.getState().equals(2)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userAccount", dcaBReport.getUserAccount());
            map.put("dcaYear", dcaBReport.getYear());
            this.baseMapper.insertCopy(map);
        }
    }
    @Override
    @Transactional
    public void updateShuangBaoDcaBReport(DcaBReport dcaBReport){

        LambdaQueryWrapper<DcaBReport> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBReport::getUserAccount, dcaBReport.getUserAccount());
        queryWrapper.eq(DcaBReport::getYear, dcaBReport.getYear());
        List<DcaBReport> list= this.list(queryWrapper);
        if(list.size()>0) {
            List<DcaBReport> listShuangbao = list.stream().filter(p ->p.getClshjg()!=null&& p.getClshjg().equals("拟退")).collect(Collectors.toList());
            if (listShuangbao.size() > 0) {
                this.baseMapper.UpdateShuangBao(listShuangbao.get(0).getUserAccount(), listShuangbao.get(0).getYear());
            }
        }
}
@Override
@Transactional
public void deleteDcaBReports(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        if(list.size()>0) {
            this.baseMapper.mutiUpdate(list);
        }
        }
    @Override
    @Transactional
    public  List<DcaBReport> getReportTest(){
       return  this.baseMapper.getReportTest();
    }

        }