package com.github.gongsir0630.shirodemo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/21 23:44
 * 你的指尖,拥有改变世界的力量
 * 描述: 业务用户信息
 */
@Data
@TableName("user")
public class User {
    /**
     * 主键,数据库字段为user_id -> userId == openId
     */
    @TableId(value = "user_id",type = IdType.INPUT)
    private String userId;
    private String name;
    private String photo;
    private String sex;
    private String grade;
    private String college;
    private String contact;
}
