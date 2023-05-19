package com.nozuch.securitydemo.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser implements Serializable {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String tel;
    private String email;
    private String salt;
    // 状态：0-禁用，1-启用
    private Boolean status;
    // 删除标志：0-未删除，1-已删除
    private Boolean delFlag;
}
