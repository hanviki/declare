package cc.mrbird.febs.webService.DcaReport;

import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@Slf4j
@Component
@Service
@WebService(name = "dca", targetNamespace = "urn:DcaReport.webService.febs.mrbird.cc",
        endpointInterface = "cc.mrbird.febs.webService.DcaReport.IReportService"// 接口地址
)
public class ReportImpl implements IReportService {

    @Autowired
   private IDcaBReportService iDcaBReportService;

    @Override
    public List<DcaBReport> GetReport() {
        LambdaQueryWrapper<DcaBReport> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark,1);
        queryWrapper.eq(DcaBReport::getYear,"2020");
        queryWrapper.eq(DcaBReport::getState,2);
        return  this.iDcaBReportService.list(queryWrapper);
    }
}
