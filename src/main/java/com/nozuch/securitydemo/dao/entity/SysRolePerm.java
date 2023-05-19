package com.nozuch.securitydemo.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_perm")
public class SysRolePerm {
    @TableId
    private Long id;
    private Long roleId;
    private Long permId;
}
