package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.scm.entity.ScmDArea;
import cc.mrbird.febs.scm.dao.ScmDAreaMapper;
import cc.mrbird.febs.scm.service.IScmDAreaService;
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


import java.time.ZoneId;
import java.util.*;
import java.util.Date;
/**
 * <p>
 * 药房院区表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */
@Slf4j
@Service("IScmDAreaService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDAreaServiceImpl extends ServiceImpl<ScmDAreaMapper, ScmDArea> implements IScmDAreaService {


        @Override
        public Map<String, Object> findScmDAreas(QueryRequest request, ScmDArea scmDArea) {
                Map<String, Object> result = new HashMap<>();
                try {
                        List<ScmDArea> scmDAreas = findScmDAreas(scmDArea, request);
                        List<Tree<ScmDArea>> trees = new ArrayList<>();
                        buildTrees(trees, scmDAreas);
                        Tree<ScmDArea> deptTree = TreeUtil.build(trees);

                        result.put("rows", deptTree);
                        result.put("total", scmDAreas.size());
                } catch (Exception e) {
                        log.error("获取院区列表失败", e);
                        result.put("rows", null);
                        result.put("total", 0);
                }
                return result;
        }
        @Override
        public List<ScmDArea> findScmDAreas(ScmDArea scmDArea, QueryRequest request) {
                QueryWrapper<ScmDArea> queryWrapper = new QueryWrapper<>();

                if (StringUtils.isNotBlank(scmDArea.getName()))
                        queryWrapper.lambda().eq(ScmDArea::getName, scmDArea.getName());

                SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", FebsConstant.ORDER_ASC, true);
                return this.baseMapper.selectList(queryWrapper);
        }


        private void buildTrees(List<Tree<ScmDArea>> trees, List<ScmDArea> scmDAreas) {
                scmDAreas.forEach(scmDArea -> {
                        Tree<ScmDArea> tree = new Tree<>();
                        tree.setId(scmDArea.getId());
                        tree.setKey(tree.getId());
                        tree.setParentId(scmDArea.getParentId());
                        tree.setText(scmDArea.getName());
                        tree.setCreateTime( scmDArea.getCreateTime());
                      //  tree.setModifyTime(Date.from( scmDArea.getModifyTime().atZone( ZoneId.systemDefault()).toInstant()));
                        tree.setOrder(scmDArea.getOrderNum());
                        tree.setTitle(tree.getText());
                        tree.setValue(tree.getId());
                        trees.add(tree);
                });
        }
        @Override
        @Transactional
        public void createScmDArea(ScmDArea scmDArea){
                String parentId = scmDArea.getParentId();
                if (parentId == null)
                        scmDArea.setParentId("");
                scmDArea.setId(scmDArea.getCode());
                scmDArea.setCreateTime(new Date());
                this.save(scmDArea);
        }

        @Override
        @Transactional
        public void updateScmDArea(ScmDArea scmDArea){
                scmDArea.setModifyTime(new Date());
                this.baseMapper.updateScmDArea(scmDArea);
        }

        @Override
        @Transactional
        public void deleteScmDAreas(String[]Ids){
                List<String> list=Arrays.asList(Ids);
                this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
       public List<ScmDArea> getAreasByUserId(Long id)
        {
                return  this.baseMapper.GetAreaByUserId(id);
        }
        @Override
        @Transactional
        public List<ScmDArea> getAreasAll()
        {
                return  this.baseMapper.GetAreaAll();
        }

}