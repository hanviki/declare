package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.dca.entity.DcaDXl;
import cc.mrbird.febs.dca.dao.DcaDXlMapper;
import cc.mrbird.febs.dca.service.IDcaDXlService;
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
 * 系列名称 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-29
 */
@Slf4j
@Service("IDcaDXlService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDXlServiceImpl extends ServiceImpl<DcaDXlMapper, DcaDXl> implements IDcaDXlService {


@Override
public IPage<DcaDXl> findDcaDXls(QueryRequest request, DcaDXl dcaDXl){
        try{
        LambdaQueryWrapper<DcaDXl> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaDXl::getIsDeletemark, 1);//1是未删 0是已删

                                if (dcaDXl.getState()!=null) {
                                queryWrapper.eq(DcaDXl::getState, dcaDXl.getState());
                                }
                                if (StringUtils.isNotBlank(dcaDXl.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaDXl.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaDXl::getCreateTime, dcaDXl.getCreateTimeFrom())
                                .le(DcaDXl::getCreateTime, dcaDXl.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaDXl.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaDXl.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaDXl::getModifyTime, dcaDXl.getModifyTimeFrom())
                                .le(DcaDXl::getModifyTime, dcaDXl.getModifyTimeTo());
                                }

        Page<DcaDXl> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaDXl> findDcaDXlList (QueryRequest request, DcaDXl dcaDXl){
        try{
        Page<DcaDXl> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaDXl(page,dcaDXl);
        }catch(Exception e){
        log.error("获取系列名称失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaDXl(DcaDXl dcaDXl){
                dcaDXl.setCreateTime(new Date());
        dcaDXl.setIsDeletemark(1);
        this.save(dcaDXl);
        }

@Override
@Transactional
public void updateDcaDXl(DcaDXl dcaDXl){
        dcaDXl.setModifyTime(new Date());
        this.baseMapper.updateDcaDXl(dcaDXl);
        }

@Override
@Transactional
public void deleteDcaDXls(String[]Ids){
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
    @Override
    public Map<String, Object> findDeptsByUserId(Long userId){
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<DcaDXl> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.apply("dca_d_mudules.id in (select mudule_id from dca_user_moudules where userId ="+userId+")");
            queryWrapper.eq(DcaDXl::getIsDeletemark,1);
            List<DcaDXl> depts = this.baseMapper.selectList(queryWrapper);
            ;
            List<Tree<DcaDXl>> trees = new ArrayList<>();
            buildTrees(trees, depts);
            Tree<DcaDXl> deptTree = TreeUtil.build(trees);

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
            LambdaQueryWrapper<DcaDXl> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaDXl::getIsDeletemark,1);
            List<DcaDXl> depts = this.baseMapper.selectList(queryWrapper);
            ;
            List<Tree<DcaDXl>> trees = new ArrayList<>();
            buildTrees(trees, depts);
            Tree<DcaDXl> deptTree = TreeUtil.build(trees);

            result.put("rows", deptTree);
            result.put("total", depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    private void buildTrees(List<Tree<DcaDXl>> trees, List<DcaDXl> depts) {
        depts.forEach(dept -> {
            Tree<DcaDXl> tree = new Tree<>();
            tree.setId(dept.getId().toString());
            tree.setKey(tree.getId());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getMuduleName());
            tree.setCreateTime(dept.getCreateTime());
            tree.setModifyTime(dept.getModifyTime());
            tree.setOrder(Double.valueOf(dept.getId().toString()));
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
        }