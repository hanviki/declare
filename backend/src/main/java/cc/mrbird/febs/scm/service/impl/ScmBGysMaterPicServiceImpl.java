package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.dao.ScmDMaterMapper;
import cc.mrbird.febs.scm.entity.ScmBGysMaterPic;
import cc.mrbird.febs.scm.dao.ScmBGysMaterPicMapper;
import cc.mrbird.febs.scm.entity.ScmDMater;
import cc.mrbird.febs.scm.service.IScmBGysMaterPicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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

/**
 * <p>
 * 资质文件记录表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
@Slf4j
@Service("IScmBGysMaterPicService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBGysMaterPicServiceImpl extends ServiceImpl<ScmBGysMaterPicMapper, ScmBGysMaterPic> implements IScmBGysMaterPicService {


    @Autowired
    private ScmDMaterMapper scmDMaterMapper;

    @Override
    public IPage<ScmBGysMaterPic> findScmBGysMaterPics(QueryRequest request, ScmBGysMaterPic scmBGysMaterPic,String keyword_mater,String keyword_gys) {
        try {
            LambdaQueryWrapper<ScmBGysMaterPic> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmBGysMaterPic.getId())) {
                queryWrapper.eq(ScmBGysMaterPic::getId, scmBGysMaterPic.getId());
            }
            if (StringUtils.isNotBlank(scmBGysMaterPic.getMaterId())) {
                queryWrapper.eq(ScmBGysMaterPic::getMaterId, scmBGysMaterPic.getMaterId());
            }
            if (StringUtils.isNotBlank(scmBGysMaterPic.getMatnr())) {
                queryWrapper.eq(ScmBGysMaterPic::getMatnr, scmBGysMaterPic.getMatnr());
            }
            if (scmBGysMaterPic.getState()!=null &&scmBGysMaterPic.getState()!=-1) {
                queryWrapper.eq(ScmBGysMaterPic::getState, scmBGysMaterPic.getState());
            }
            if (StringUtils.isNotBlank(scmBGysMaterPic.getTxz01())) {
                queryWrapper.like(ScmBGysMaterPic::getTxz01, scmBGysMaterPic.getTxz01());
            }
            if (StringUtils.isNotBlank(scmBGysMaterPic.getGysaccount())) {
                queryWrapper.eq(ScmBGysMaterPic::getGysaccount, scmBGysMaterPic.getGysaccount());
            }
            if (StringUtils.isNotBlank(scmBGysMaterPic.getName())) {
                queryWrapper.like(ScmBGysMaterPic::getName, scmBGysMaterPic.getName());
            }
            if (StringUtils.isNotBlank(scmBGysMaterPic.getCharge())) {
                queryWrapper.like(ScmBGysMaterPic::getCharge, scmBGysMaterPic.getCharge());
            }
            if(StringUtils.isNotBlank(keyword_mater))
            {
                queryWrapper.and(qw->qw.eq(ScmBGysMaterPic::getMatnr,keyword_mater).or().like(ScmBGysMaterPic::getTxz01,keyword_mater));
            }
            if(StringUtils.isNotBlank(keyword_gys))
            {
                queryWrapper.and(qw->qw.eq(ScmBGysMaterPic::getGysaccount,keyword_gys).or().like(ScmBGysMaterPic::getName,keyword_gys));
            }
            queryWrapper.eq(ScmBGysMaterPic::getIsDeletemark, 1);
            Page<ScmBGysMaterPic> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ScmBGysMaterPic> findScmBGysMaterPicsAudit(QueryRequest request, ScmBGysMaterPic scmBGysMaterPic,String keyword_mater,String keyword_gys,String userid)
    {
        try {
        Page<ScmBGysMaterPic> page = new Page<>();
        SortUtil.handlePageSort(request, page, false);
        return this.baseMapper.findVScmBGyspicUser(page,scmBGysMaterPic ,keyword_mater, keyword_gys, userid);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }
    @Override
    @Transactional
    public void createScmBGysMaterPic(ScmBGysMaterPic scmBGysMaterPic) throws FebsException {
        scmBGysMaterPic.setId(UUID.randomUUID().toString());
        scmBGysMaterPic.setCreateTime(new Date());
        String matnr = scmBGysMaterPic.getMaterId();
        LambdaQueryWrapper<ScmBGysMaterPic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScmBGysMaterPic::getMaterId, matnr);
        queryWrapper.eq(ScmBGysMaterPic::getGysaccount, scmBGysMaterPic.getGysaccount());
        queryWrapper.eq(ScmBGysMaterPic::getCharge, scmBGysMaterPic.getCharge());
        queryWrapper.eq(ScmBGysMaterPic::getIsDeletemark, 1);
        int count =this.baseMapper.selectCount(queryWrapper);
        if(count>0)
        {
            throw new FebsException("存在相同的药品和批次");

        }

        ScmDMater scmDMater = this.scmDMaterMapper.selectById(matnr.trim());
        scmBGysMaterPic.setState(0);
        scmBGysMaterPic.setMatnr(scmDMater.getMatnr());
        scmBGysMaterPic.setMseht(scmDMater.getMseht());
        scmBGysMaterPic.setProduceArea(scmDMater.getProduceArea());
        scmBGysMaterPic.setSpellCode(scmDMater.getSpellCode());
        scmBGysMaterPic.setSpec(scmDMater.getSpec());
        scmBGysMaterPic.setTxz01(scmDMater.getTxz01());
        this.save(scmBGysMaterPic);
    }

    @Override
    @Transactional
    public void auditScmBGysMaterPic(ScmBGysMaterPic scmBGysMaterPic) {
        scmBGysMaterPic.setModifyTime(new Date());
        this.baseMapper.updateScmBGysMaterPic(scmBGysMaterPic);
    }

    @Override
    @Transactional
    public void updateScmBGysMaterPic(ScmBGysMaterPic scmBGysMaterPic) throws FebsException {
        scmBGysMaterPic.setModifyTime(new Date());
        String matnr = scmBGysMaterPic.getMaterId();

        LambdaQueryWrapper<ScmBGysMaterPic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScmBGysMaterPic::getMaterId, matnr);

        queryWrapper.eq(ScmBGysMaterPic::getGysaccount, scmBGysMaterPic.getGysaccount());
        queryWrapper.eq(ScmBGysMaterPic::getCharge, scmBGysMaterPic.getCharge());
        queryWrapper.ne(ScmBGysMaterPic::getId, scmBGysMaterPic.getId());
        queryWrapper.eq(ScmBGysMaterPic::getIsDeletemark, 1);
        int count =this.baseMapper.selectCount(queryWrapper);
        if(count>0)
        {
            throw new FebsException("存在相同的药品和批次");
        }
        ScmDMater scmDMater = this.scmDMaterMapper.selectById(matnr);
        scmBGysMaterPic.setState(0);
        scmBGysMaterPic.setMatnr(scmDMater.getMatnr());
        scmBGysMaterPic.setMseht(scmDMater.getMseht());
        scmBGysMaterPic.setProduceArea(scmDMater.getProduceArea());
        scmBGysMaterPic.setSpellCode(scmDMater.getSpellCode());
        scmBGysMaterPic.setSpec(scmDMater.getSpec());
        scmBGysMaterPic.setTxz01(scmDMater.getTxz01());
        this.baseMapper.updateScmBGysMaterPic(scmBGysMaterPic);
    }

    @Override
    @Transactional
    public void deleteScmBGysMaterPics(String[] Ids,int state) throws FebsException  {
        List<String> list = Arrays.asList(Ids);
        for (String item :list
        ) {

            ScmBGysMaterPic scmBGysMaterPic = new ScmBGysMaterPic();
            scmBGysMaterPic.setId(item);
            scmBGysMaterPic.setState(state);
            if(state==0) {//0是删除 1是审核
                scmBGysMaterPic.setIsDeletemark(0);
                if(this.baseMapper.getCount(item)>0)
                {
                    throw new FebsException("此药品检验报告存在供应计划，不能删除！！！");
                }
            }
            this.baseMapper.updateScmBGysMaterPic(scmBGysMaterPic);
        }
    }
    @Override
    @Transactional
   public List<String> findChargeByBaseId(String base_Id,String account){
       return this.baseMapper.findChargeByBaseId(base_Id,account);
    }
    @Override
    @Transactional
    public Boolean IsDelete(String id)
    {
        Integer count=  this.baseMapper.getCount(id);
        if(count>0) return  true;
        return  false;
    }

}