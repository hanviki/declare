package cc.mrbird.febs.system.domain;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

@Data
@Excel("用户信息表")
public class user_extend {
    //账号
    @ExcelField(value = "账号")
    private String userName;

    //姓名
    @ExcelField(value = "姓名")
    private String xmname;

    //密码
    @ExcelField(value = "密码")
    private String password;

    //角色
    @ExcelField(value = "角色")
    private String roleName;

    //部门
    @ExcelField(value = "部门")
    private String deptName;
}
