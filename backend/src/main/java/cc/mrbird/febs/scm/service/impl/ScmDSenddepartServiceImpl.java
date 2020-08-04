package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmDSenddepart;
import cc.mrbird.febs.scm.dao.ScmDSenddepartMapper;
import cc.mrbird.febs.scm.service.IScmDSenddepartService;
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

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 * 物资送货部门 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-26
 */
@Slf4j
@Service("IScmDSenddepartService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDSenddepartServiceImpl extends ServiceImpl<ScmDSenddepartMapper, ScmDSenddepart> implements IScmDSenddepartService {


@Override
public IPage<ScmDSenddepart> findScmDSenddeparts(QueryRequest request, ScmDSenddepart scmDSenddepart){
        try{
        LambdaQueryWrapper<ScmDSenddepart> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(scmDSenddepart.getCode())) {
        queryWrapper.eq(ScmDSenddepart::getCode, scmDSenddepart.getCode());
        }
        Page<ScmDSenddepart> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmDSenddepart(ScmDSenddepart scmDSenddepart){
        scmDSenddepart.setId(UUID.randomUUID().toString());
        scmDSenddepart.setCreateTime(new Date());
        this.save(scmDSenddepart);
        }

@Override
@Transactional
public void updateScmDSenddepart(ScmDSenddepart scmDSenddepart){
        scmDSenddepart.setModifyTime(new Date());
        this.baseMapper.updateScmDSenddepart(scmDSenddepart);
        }

@Override
@Transactional
public void deleteScmDSenddeparts(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }