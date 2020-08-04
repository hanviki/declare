package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.VMsgFilevalidinfo;
import cc.mrbird.febs.scm.dao.VMsgFilevalidinfoMapper;
import cc.mrbird.febs.scm.service.IVMsgFilevalidinfoService;
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
 * @since 2020-04-06
 */
@Slf4j
@Service("IVMsgFilevalidinfoService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VMsgFilevalidinfoServiceImpl extends ServiceImpl<VMsgFilevalidinfoMapper, VMsgFilevalidinfo> implements IVMsgFilevalidinfoService {


@Override
public List<VMsgFilevalidinfo>  GetMsgFileValid(){
       return this.baseMapper.GetMsgFileValid();
}

        }