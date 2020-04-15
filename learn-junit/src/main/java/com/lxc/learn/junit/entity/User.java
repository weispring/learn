package com.lxc.learn.junit.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
@TableName("user")
public class User extends Model<User> {
    private Integer id;

    private String name;

    @TableField("nick_name")
    private String nickname;

    private String email;

    private String phone;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)){
            return false;
        }
        if (this.name == null){
            if (((User) obj).getName() == null){
                return true;
            }else {
                return false;
            }
        }else {
            return this.name.equals(((User) obj).getName());
        }
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}