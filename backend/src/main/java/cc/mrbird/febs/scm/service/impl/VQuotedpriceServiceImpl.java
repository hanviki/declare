package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.VQuotedprice;
import cc.mrbird.febs.scm.dao.VQuotedpriceMapper;
import cc.mrbird.febs.scm.service.IVQuotedpriceService;
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
 * VIEW 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-01-07
 */
@Slf4j
@Service("IVQuotedpriceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VQuotedpriceServiceImpl extends ServiceImpl<VQuotedpriceMapper, VQuotedprice> implements IVQuotedpriceService {


    @Override
    public IPage<VQuotedprice> findVQuotedprices(QueryRequest request, VQuotedprice vQuotedprice) {
        try {
            LambdaQueryWrapper<VQuotedprice> queryWrapper = new LambdaQueryWrapper<>();
            if (vQuotedprice.getBaseId() != null) {
                queryWrapper.eq(VQuotedprice::getBaseId, vQuotedprice.getBaseId());
            }
            if (StringUtils.isNotBlank(vQuotedprice.getKeyword())) {
                queryWrapper.and(p -> p.eq(VQuotedprice::getGysaccount, vQuotedprice.getKeyword()).or().like(VQuotedprice::getGysname, vQuotedprice.getKeyword()));
            }
            if (StringUtils.isNotBlank(vQuotedprice.getProductName())) {
                queryWrapper.like(VQuotedprice::getProductName, vQuotedprice.getProductName());
            }
            if (StringUtils.isNotBlank(vQuotedprice.getHospitalName())) {
                queryWrapper.like(VQuotedprice::getHospitalName, vQuotedprice.getHospitalName());
            }
            queryWrapper.eq(VQuotedprice::getIsDeletemark, 1);//1是未删 0是已删
            Page<VQuotedprice> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createVQuotedprice(VQuotedprice vQuotedprice) {
        vQuotedprice.setId(UUID.randomUUID().toString());
        vQuotedprice.setCreateTime(new Date());
        vQuotedprice.setIsDeletemark(1);
        this.save(vQuotedprice);
    }

    @Override
    @Transactional
    public void updateVQuotedprice(VQuotedprice vQuotedprice) {
        vQuotedprice.setModifyTime(new Date());
        this.baseMapper.updateVQuotedprice(vQuotedprice);
    }

    @Override
    @Transactional
    public void deleteVQuotedprices(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }


}