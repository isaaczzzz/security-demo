package com.nozuch.securitydemo.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_perm")
public class SysPerm {
    @TableId
    private Long permId;
    private String permName;
    private String perm;
}
