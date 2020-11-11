package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.dca.entity.DcaDAuditinfo;
import cc.mrbird.febs.dca.dao.DcaDAuditinfoMapper;
import cc.mrbird.febs.dca.service.IDcaDAuditinfoService;
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
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-23
 */
@Slf4j
@Service("IDcaDAuditinfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDAuditinfoServiceImpl extends ServiceImpl<DcaDAuditinfoMapper, DcaDAuditinfo> implements IDcaDAuditinfoService {


@Override
public IPage<DcaDAuditinfo> findDcaDAuditinfos(QueryRequest request, DcaDAuditinfo dcaDAuditinfo){
        try{
        LambdaQueryWrapper<DcaDAuditinfo> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDAuditinfo::getIsDeletemark, 1);//1是未删 0是已删

                                if (dcaDAuditinfo.getState()!=null) {
                                queryWrapper.eq(DcaDAuditinfo::getState, dcaDAuditinfo.getState());
                                }
                                if (StringUtils.isNotBlank(dcaDAuditinfo.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaDAuditinfo.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaDAuditinfo::getCreateTime, dcaDAuditinfo.getCreateTimeFrom())
                                .le(DcaDAuditinfo::getCreateTime, dcaDAuditinfo.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaDAuditinfo.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaDAuditinfo.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaDAuditinfo::getModifyTime, dcaDAuditinfo.getModifyTimeFrom())
                                .le(DcaDAuditinfo::getModifyTime, dcaDAuditinfo.getModifyTimeTo());
                                }

        Page<DcaDAuditinfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDAuditinfo> findDcaDAuditinfoList (QueryRequest request, DcaDAuditinfo dcaDAuditinfo){
        try{
        Page<DcaDAuditinfo> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDAuditinfo(page,dcaDAuditinfo);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }

    @Override
    public Map<String, Object> findDeptsByUserId(Long userId){
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<DcaDAuditinfo> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.apply("dca_d_auditinfo.id in (select audit_id from dca_user_audit where userId ="+userId+")");
            queryWrapper.eq(DcaDAuditinfo::getIsDeletemark,1);
            List<DcaDAuditinfo> depts = this.baseMapper.selectList(queryWrapper);
            ;
            List<Tree<DcaDAuditinfo>> trees = new ArrayList<>();
            buildTrees(trees, depts);
            Tree<DcaDAuditinfo> deptTree = TreeUtil.build(trees);

            result.put("rows", deptTree);
            result.put("total", depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public Map<String, Object> findDepts() {
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<DcaDAuditinfo> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaDAuditinfo::getIsDeletemark,1);
            List<DcaDAuditinfo> depts = this.baseMapper.selectList(queryWrapper);
            ;
            List<Tree<DcaDAuditinfo>> trees = new ArrayList<>();
            buildTrees(trees, depts);
            Tree<DcaDAuditinfo> deptTree = TreeUtil.build(trees);

            result.put("rows", deptTree);
            result.put("total", depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    private void buildTrees(List<Tree<DcaDAuditinfo>> trees, List<DcaDAuditinfo> depts) {
        depts.forEach(dept -> {
            Tree<DcaDAuditinfo> tree = new Tree<>();
            tree.setId(dept.getId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getFieldTitle());
            tree.setCreateTime(dept.getCreateTime());
            tree.setModifyTime(dept.getModifyTime());
            tree.setOrder(Double.valueOf(dept.getId().toString()));
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }

@Override
@Transactional
public void createDcaDAuditinfo(DcaDAuditinfo dcaDAuditinfo){
                dcaDAuditinfo.setCreateTime(new Date());
        dcaDAuditinfo.setIsDeletemark(1);
        this.save(dcaDAuditinfo);
        }

@Override
@Transactional
public void updateDcaDAuditinfo(DcaDAuditinfo dcaDAuditinfo){
        dcaDAuditinfo.setModifyTime(new Date());
        this.baseMapper.updateDcaDAuditinfo(dcaDAuditinfo);
        }

@Override
@Transactional
public void deleteDcaDAuditinfos(String[]Ids){
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