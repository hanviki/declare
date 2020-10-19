package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.service.IDcaBTitleapplyService;
import cc.mrbird.febs.dca.entity.DcaBTitleapply;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

 import com.itextpdf.text.Document;
  import com.itextpdf.text.DocumentException;
  import com.itextpdf.text.Paragraph;
  import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
  import java.io.FileOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2020-09-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBTitleapply")

public class DcaBTitleapplyController extends BaseController{

private String message;
@Autowired
public IDcaBTitleapplyService iDcaBTitleapplyService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBTitleapply 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBTitleapply:view")
public Map<String, Object> List(QueryRequest request, DcaBTitleapply dcaBTitleapply){
        return getDataTable(this.iDcaBTitleapplyService.findDcaBTitleapplys(request, dcaBTitleapply));
        }

/**
 * 添加
 * @param  dcaBTitleapply
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBTitleapply:add")
public void addDcaBTitleapply(@Valid DcaBTitleapply dcaBTitleapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBTitleapply.setCreateUserId(currentUser.getUserId());
        this.iDcaBTitleapplyService.createDcaBTitleapply(dcaBTitleapply);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBTitleapply
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBTitleapply:update")
public void updateDcaBTitleapply(@Valid DcaBTitleapply dcaBTitleapply)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBTitleapply.setModifyUserId(currentUser.getUserId());
        this.iDcaBTitleapplyService.updateDcaBTitleapply(dcaBTitleapply);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBTitleapply:delete")
public void deleteDcaBTitleapplys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBTitleapplyService.deleteDcaBTitleapplys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBTitleapply:export")
public void export(QueryRequest request, DcaBTitleapply dcaBTitleapply, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBTitleapply> dcaBTitleapplys = this.iDcaBTitleapplyService.findDcaBTitleapplys(request, dcaBTitleapply).getRecords();
        ExcelKit.$Export(DcaBTitleapply.class, response).downXlsx(dcaBTitleapplys, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBTitleapply detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBTitleapply dcaBTitleapply=this.iDcaBTitleapplyService.getById(id);
        return dcaBTitleapply;
        }

        @PostMapping("pdf")
    public  String generatePdf(String html){
            Map<String, String> hs= new HashedMap();
            hs.put("header.fontName", "宋体");
            boolean success = HtmlToPdf.create()
                    .object(HtmlToPdfObject.forHtml(html,hs))
                   .convert("D:\\pdf2098.pdf");
            return  "";

        }
        }