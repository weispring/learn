package com.lxc.learn.junit.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class User {
    private Integer id;

    private String name;

    private String nickname;

    private String email;

    private String phone;

    private Date createTime;

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
}