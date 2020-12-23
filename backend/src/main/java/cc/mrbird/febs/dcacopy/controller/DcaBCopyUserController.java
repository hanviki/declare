package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.PDFDemo;
import cc.mrbird.febs.dca.entity.CustomApplyFirst;
import cc.mrbird.febs.dcacopy.service.IDcaBCopyUserService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyUser;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopyUser")

public class DcaBCopyUserController extends BaseController {

    private String message;
    @Autowired
    public IDcaBCopyUserService iDcaBCopyUserService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBCopyUser/DcaBCopyUser','dca/DcaBCopyUser/DcaBCopyUser','dcaBCopyUser:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBCopyUser:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBCopyUser:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBCopyUser:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request      分页信息
     * @param dcaBCopyUser 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("dcaBCopyUser:view")
    public Map<String, Object> List(QueryRequest request, DcaBCopyUser dcaBCopyUser) {
        return getDataTable(this.iDcaBCopyUserService.findDcaBCopyUsers(request, dcaBCopyUser));
    }

    /**
     * 添加
     *
     * @param dcaBCopyUser
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("dcaBCopyUser:add")
    public void addDcaBCopyUser(@Valid DcaBCopyUser dcaBCopyUser) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBCopyUser.setCreateUserId(currentUser.getUserId());
            this.iDcaBCopyUserService.createDcaBCopyUser(dcaBCopyUser);
        } catch (Exception e) {
            message = "新增/按钮失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaBCopyUser
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("dcaBCopyUser:update")
    public void updateDcaBCopyUser(@Valid DcaBCopyUser dcaBCopyUser) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBCopyUser.setModifyUserId(currentUser.getUserId());
            this.iDcaBCopyUserService.updateDcaBCopyUser(dcaBCopyUser);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("dcaBCopyUser:delete")
    public void deleteDcaBCopyUsers(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iDcaBCopyUserService.deleteDcaBCopyUsers(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    public void export(QueryRequest request, DcaBCopyUser dcaBCopyUser, HttpServletResponse response) throws FebsException {
        try {
            PDFDemo pdfDemo = new PDFDemo();
            String fileName = dcaBCopyUser.getUserAccount() + ".pdf";
            String filePath = "D://scm//uploadPdf//" + UUID.randomUUID().toString() + ".pdf";
            String filePath2 = "D://scm//uploadPdf//" + UUID.randomUUID().toString() + ".pdf";
            ArrayList<String> mergeAddPdfList = new ArrayList<>();
            CustomApplyFirst customApplyFirst = this.iDcaBCopyUserService.getPrintPdf(dcaBCopyUser.getUserAccount(), dcaBCopyUser.getDcaYear(),dcaBCopyUser.getNpPositionName());
            List<String> npNameList=new ArrayList<>();
            npNameList.add("教授");
            npNameList.add("副教授");
            npNameList.add("研究员");
            npNameList.add("副研究员");

            LambdaQueryWrapper<DcaBCopyUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DcaBCopyUser::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBCopyUser.getUserAccount())) {
                queryWrapper.eq(DcaBCopyUser::getUserAccount, dcaBCopyUser.getUserAccount());
            }
            if (StringUtils.isNotBlank(dcaBCopyUser.getDcaYear())) {
                queryWrapper.eq(DcaBCopyUser::getDcaYear, dcaBCopyUser.getDcaYear());
            }
            DcaBCopyUser dbcuser=this.iDcaBCopyUserService.getOne(queryWrapper);
            List<String> xl=new ArrayList<>();
            xl.add("在编");
            xl.add("选留");
            List<String> dj=new ArrayList<>();
            dj.add("中级");
            dj.add("初级");
            if(npNameList.contains(dcaBCopyUser.getNpPositionName())) {
                pdfDemo.writePdf1(customApplyFirst, filePath2, filePath, mergeAddPdfList, dcaBCopyUser.getDcaYear());
            }
            else if (dcaBCopyUser.getSexName().equals("正高")||dcaBCopyUser.getSexName().equals("副高")){
                pdfDemo.writePdf(customApplyFirst, filePath2, filePath, mergeAddPdfList, dcaBCopyUser.getDcaYear());
            }
            else if(dj.contains(dcaBCopyUser.getSexName())&&xl.contains(dbcuser.getYuangongzu())){
                pdfDemo.writePdf_zu2(customApplyFirst, filePath2, filePath, mergeAddPdfList, dcaBCopyUser.getDcaYear());
            }
            else if(dj.contains(dcaBCopyUser.getSexName())){
                pdfDemo.writePdf_zu1(customApplyFirst, filePath2, filePath, mergeAddPdfList, dcaBCopyUser.getDcaYear());
            }
            else{
                pdfDemo.writePdf_23(customApplyFirst, filePath2, filePath, mergeAddPdfList, dcaBCopyUser.getDcaYear());
            }

            File file = new File(filePath);
            OutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            try {
                InputStream bis = new BufferedInputStream(new FileInputStream(file));
                byte[] b = new byte[bis.available() + 1000];
                int i = 0;

                while ((i = bis.read(b)) != -1) {
                    out.write(b, 0, i);
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("attach")
    public void export2(QueryRequest request, DcaBCopyUser dcaBCopyUser, HttpServletResponse response) throws FebsException {
        try {
            PDFDemo pdfDemo = new PDFDemo();
            String fileName = dcaBCopyUser.getUserAccount() + ".pdf";
            String filePath = "D://scm//uploadPdf//" + UUID.randomUUID().toString() +".pdf";
            String filePath2 = "D://scm//uploadPdf//" + UUID.randomUUID().toString() + ".pdf";
            ArrayList<String> mergeAddPdfList = new ArrayList<>();
            CustomApplyFirst customApplyFirst = this.iDcaBCopyUserService.getPrintPdf(dcaBCopyUser.getUserAccount(), dcaBCopyUser.getDcaYear(),dcaBCopyUser.getNpPositionName());

            pdfDemo.attachPdf(customApplyFirst, filePath2, filePath,  dcaBCopyUser.getDcaYear());

            File file = new File(filePath);
            OutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            log.info("开始读取");
            try {
                InputStream bis = new BufferedInputStream(new FileInputStream(file));
               /* byte[] b = new byte[bis.available() + 1000];
                int i = 0;

                while ((i = bis.read(b)) != -1) {
                    out.write(b, 0, i);
                }*/

                byte [] b = new byte[1024];
                long k = 0;
                while(k < file.length()){
                    int j = bis.read(b,0,1024);
                    k += j;
                    out.write(b,0,j);
                    //out.flush();
                }
                bis.close();
                log.info("读取完成");
                out.flush();
                out.close();
            } catch (IOException e) {
                log.error("读取pdf失败");
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @GetMapping("/{id}")
    public DcaBCopyUser detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaBCopyUser dcaBCopyUser = this.iDcaBCopyUserService.getById(id);
        return dcaBCopyUser;
    }
}