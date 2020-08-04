package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBUserandarea;
import cc.mrbird.febs.scm.dao.ScmBUserandareaMapper;
import cc.mrbird.febs.scm.service.IScmBUserandareaService;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Date;
/**
 * <p>
 * 用户表和院区表配置表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-13
 */
@Slf4j
@Service("IScmBUserandareaService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBUserandareaServiceImpl extends ServiceImpl<ScmBUserandareaMapper, ScmBUserandarea> implements IScmBUserandareaService {


@Override
public IPage<ScmBUserandarea> findScmBUserandareas(QueryRequest request, ScmBUserandarea scmBUserandarea){
        try{
        LambdaQueryWrapper<ScmBUserandarea> queryWrapper=new LambdaQueryWrapper<>();

        Page<ScmBUserandarea> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmBUserandarea(ScmBUserandarea scmBUserandarea){
        scmBUserandarea.setId(UUID.randomUUID().toString());
        scmBUserandarea.setCreateTime(new Date());
        this.save(scmBUserandarea);
        }

@Override
@Transactional
public void updateScmBUserandarea(ScmBUserandarea scmBUserandarea){
        scmBUserandarea.setModifyTime(new Date());
        this.baseMapper.updateScmBUserandarea(scmBUserandarea);
        }

@Override
@Transactional
public void deleteScmBUserandareas(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        @Override
        public List<ScmBUserandarea> getAreaByUserId(String userId) {
                return baseMapper.selectList(new LambdaQueryWrapper<ScmBUserandarea>().eq(ScmBUserandarea::getUserID, userId));
        }
        }