package cc.mrbird.febs.scm.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.properties.FebsProperties;
import cc.mrbird.febs.common.utils.FtpUtil;
import cc.mrbird.febs.scm.service.IComFileService;
import cc.mrbird.febs.scm.entity.ComFile;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author viki
 * @since 2019-11-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("comFile")

public class ComFileController extends BaseController{

    private String message;
    @Autowired
    public IComFileService iComFileService;
    @Autowired
    private FebsProperties febsProperties;

    /**
     * 分页查询数据
     *
     * @param bootStrapTable  分页信息
     * @param comFile 查询条件
     * @return
     */
    @GetMapping
    @RequiresPermissions("comFile:view")
    public Map<String, Object> List(QueryRequest request, ComFile comFile){
        return getDataTable(this.iComFileService.findComFiles(request, comFile));
    }

    /**
     * 跳转添加页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    @RequiresPermissions("comFile:add")
    public void addComFile(@Valid ComFile comFile)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            comFile.setCreateUserId(currentUser.getUserId());
            this.iComFileService.createComFile(comFile);
        }catch(Exception e){
            message="新增/按钮失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    /**
     * 跳转修改页面
     * @param request
     * @param id  实体ID
     * @return
     */
    @Log("修改")
    @PutMapping
    @RequiresPermissions("comFile:update")
    public void updateComFile(@Valid ComFile comFile)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            comFile.setModifyUserId(currentUser.getUserId());
            this.iComFileService.updateComFile(comFile);
        }catch(Exception e){
            message="修改成功" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }


    @Log("删除")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("comFile:delete")
    public void deleteComFiles(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
            String[]arr_ids=ids.split(StringPool.COMMA);
            this.iComFileService.deleteComFiles(arr_ids);
        }catch(Exception e){
            message="删除成功" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
    @PostMapping("excel")
    @RequiresPermissions("comFile:export")
    public void export(QueryRequest request, ComFile comFile, HttpServletResponse response) throws FebsException {
        try {
            List<ComFile> comFiles = this.iComFileService.findComFiles(request, comFile).getRecords();
            ExcelKit.$Export(ComFile.class, response).downXlsx(comFiles, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public ComFile detail(@NotBlank(message = "{required}") @PathVariable String id) {
        ComFile comFile=this.iComFileService.getById(id);
        return comFile;
    }


    @PostMapping("upload")
    public FebsResponse Upload(@RequestParam("file") MultipartFile file) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("空文件");
        }
        String fileName2 = file.getOriginalFilename();  // 文件名
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // 后缀名
        String filePath = febsProperties.getUploadPath(); // 上传后的路径
        String fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FebsException(e.getMessage());
        }
        String Id=UUID.randomUUID().toString();
        ComFile cf=new ComFile();
        cf.setId(Id);
        cf.setCreateTime(new Date());
        cf.setClientName(fileName2);//客户端的名称
        cf.setServerName(fileName);
        iComFileService.createComFile(cf);
        return new FebsResponse().data(Id) ;
    }

    @PostMapping("uploadCheck")
    public FebsResponse UploadCheck(@RequestParam("file") MultipartFile file) throws FebsException {
        if (file.isEmpty()) {
            throw new FebsException("空文件");
        }
        String fileName2 = file.getOriginalFilename();  // 文件名
        String suffixName = fileName2.substring(fileName2.lastIndexOf("."));  // 后缀名
        String filePath = febsProperties.getUploadPath(); // 上传后的路径Check
        String fileName = UUID.randomUUID() + suffixName; // 新文件名

        try {
            log.info("开始上传ftp");
            FtpUtil ftpUtil=new FtpUtil();
            ftpUtil.uploadFile("SPL/PRD/",fileName,file.getInputStream());
            log.info("上传ftp陈宫了");
        } catch (IOException e) {
            throw new FebsException(e.getMessage());
        }
        String Id=UUID.randomUUID().toString();
        ComFile cf=new ComFile();
        cf.setId(Id);
        cf.setCreateTime(new Date());
        cf.setClientName(fileName2);//客户端的名称
        cf.setServerName(fileName);
        iComFileService.createComFile(cf);
        return new FebsResponse().data(Id) ;
    }

    @GetMapping("checkFile/{fileName}")
    public  void downloadFtpFile(@PathVariable String fileName,HttpServletResponse response) {
        try {
            InputStream inputStream ;
            FtpUtil ftpUtil=new FtpUtil();
            inputStream= ftpUtil.getInputStreamByName("SPL/PRD/",fileName);
            BufferedInputStream br = new BufferedInputStream(inputStream);
            System.out.println(br.available());
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
           // response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream out = response.getOutputStream();

            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            log.error("made");
            br.close();
            out.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println("文件读取错误。"+e.getMessage());
        }
    }
}