package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.dca.entity.DcaDMudules;
import cc.mrbird.febs.dca.dao.DcaDMudulesMapper;
import cc.mrbird.febs.dca.service.IDcaDMudulesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * 模块表名称 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-08-10
 */
@Slf4j
@Service("IDcaDMudulesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaDMudulesServiceImpl extends ServiceImpl<DcaDMudulesMapper, DcaDMudules> implements IDcaDMudulesService {


    @Override
    public IPage<DcaDMudules> findDcaDMuduless(QueryRequest request, DcaDMudules dcaDMudules) {
        try {
            LambdaQueryWrapper<DcaDMudules> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaDMudules::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaDMudules.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaDMudules.getCreateTimeTo())) {
                queryWrapper
                        .ge(DcaDMudules::getCreateTime, dcaDMudules.getCreateTimeFrom())
                        .le(DcaDMudules::getCreateTime, dcaDMudules.getCreateTimeTo());
            }
            if (StringUtils.isNotBlank(dcaDMudules.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaDMudules.getModifyTimeTo())) {
                queryWrapper
                        .ge(DcaDMudules::getModifyTime, dcaDMudules.getModifyTimeFrom())
                        .le(DcaDMudules::getModifyTime, dcaDMudules.getModifyTimeTo());
            }

            Page<DcaDMudules> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<DcaDMudules> findDcaDMudulesList(QueryRequest request, DcaDMudules dcaDMudules) {
        try {
            Page<DcaDMudules> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);//true 是属性  false是数据库字段可两个
            return this.baseMapper.findDcaDMudules(page, dcaDMudules);
        } catch (Exception e) {
            log.error("获取模块表名称失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createDcaDMudules(DcaDMudules dcaDMudules) {
        dcaDMudules.setCreateTime(new Date());
        dcaDMudules.setIsDeletemark(1);
        this.save(dcaDMudules);
    }

    @Override
    @Transactional
    public void updateDcaDMudules(DcaDMudules dcaDMudules) {
        dcaDMudules.setModifyTime(new Date());
        this.baseMapper.updateDcaDMudules(dcaDMudules);
    }

    @Override
    @Transactional
    public void deleteDcaDMuduless(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }
    @Override
    public
    Map<String, Object> findDeptsByUserId(Long userId){
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<DcaDMudules> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.apply("dca_d_mudules.id in (select mudule_id from dca_user_moudules where userId ="+userId+")");
            queryWrapper.eq(DcaDMudules::getIsDeletemark,1);
            List<DcaDMudules> depts = this.baseMapper.selectList(queryWrapper);
            ;
            List<Tree<DcaDMudules>> trees = new ArrayList<>();
            List<String> ids =new ArrayList<>();
            buildTrees(trees, depts,ids);
            Tree<DcaDMudules> deptTree = TreeUtil.build(trees);
            result.put("ids", ids);
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
    public Map<String, Object> findDepts(String codes) {
        Map<String, Object> result = new HashMap<>();
        try {
            LambdaQueryWrapper<DcaDMudules> queryWrapper=new LambdaQueryWrapper<>();
queryWrapper.eq(DcaDMudules::getIsDeletemark,1);
if(StringUtils.isNotBlank(codes)) {
    String[] arrIds = codes.split(",");
    queryWrapper.notIn(DcaDMudules::getId,arrIds);
}
            List<DcaDMudules> depts = this.baseMapper.selectList(queryWrapper);

            List<Tree<DcaDMudules>> trees = new ArrayList<>();
            List<String> ids =new ArrayList<>();
            buildTrees(trees, depts,ids);
            Tree<DcaDMudules> deptTree = TreeUtil.build(trees);
            result.put("ids", ids);
            result.put("rows", deptTree);
            result.put("total", depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    private void buildTrees(List<Tree<DcaDMudules>> trees, List<DcaDMudules> depts,List<String> ids) {
        depts.forEach(dept -> {
            ids.add(dept.getId().toString());
            Tree<DcaDMudules> tree = new Tree<>();
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