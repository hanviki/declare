package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.dao.ScmBSupplyplanMapper;
import cc.mrbird.febs.scm.dao.ScmDVendorMapper;
import cc.mrbird.febs.scm.entity.ScmBPurcharseorder;
import cc.mrbird.febs.scm.dao.ScmBPurcharseorderMapper;
import cc.mrbird.febs.scm.entity.ScmBSupplyplan;
import cc.mrbird.febs.scm.entity.ScmDVendor;
import cc.mrbird.febs.scm.entity.StatisticMenge;
import cc.mrbird.febs.scm.service.IScmBPurcharseorderService;
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

/**
 * <p>
 * SCM_B_PURCHARSEORDER 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-21
 */
@Slf4j
@Service("IScmBPurcharseorderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBPurcharseorderServiceImpl extends ServiceImpl<ScmBPurcharseorderMapper, ScmBPurcharseorder> implements IScmBPurcharseorderService {

    @Autowired
    ScmDVendorMapper scmDVendorMapper;
    @Autowired
    ScmBSupplyplanMapper scmBSupplyplanMapper;

    @Override
    public IPage<ScmBPurcharseorder> findScmBPurcharseorders(QueryRequest request, ScmBPurcharseorder scmBPurcharseorder) {
        try {
//            LambdaQueryWrapper<ScmBPurcharseorder> queryWrapper = new LambdaQueryWrapper<>();
//            if (StringUtils.isNotBlank(scmBPurcharseorder.getCode())) {
//                queryWrapper.eq(ScmBPurcharseorder::getCode, scmBPurcharseorder.getCode());
//            }

            Page<ScmBPurcharseorder> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
           // return this.page(page, queryWrapper);
            IPage<ScmBPurcharseorder> listPur=  this.baseMapper.findPurcharseorder(page,scmBPurcharseorder);
            for (ScmBPurcharseorder order: listPur.getRecords()
                 ) {
                StatisticMenge stMen=this.scmBSupplyplanMapper.getSupplanMengeByBaseID(order.getId());
                if(stMen!=null) {
                    order.setAllmenge(stMen.gMenge);
                    order.setSuremenge(stMen.doneMenge);
                }
            }
            return  listPur;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmBPurcharseorder(ScmBPurcharseorder scmBPurcharseorder) {
        scmBPurcharseorder.setId(UUID.randomUUID().toString());
        scmBPurcharseorder.setCreateTime(new Date());

        this.save(scmBPurcharseorder);
    }

    @Override
    @Transactional
    public void updateScmBPurcharseorder(ScmBPurcharseorder scmBPurcharseorder) {
        scmBPurcharseorder.setModifyTime(new Date());
        this.baseMapper.updateScmBPurcharseorder(scmBPurcharseorder);
    }

    @Override
    @Transactional
    public void deleteScmBPurcharseorders(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }
    @Override
    @Transactional
    public  ScmBPurcharseorder getOrderById(String id){
        return  this.baseMapper.findPurcharseorderById(id);
    }
}