package com.lxc.learn.junit.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bouncycastle.asn1.cms.PasswordRecipientInfo;

import java.io.Serializable;

@Getter
@Setter
@TableName("crecord")
@Accessors(chain = true)
public class Crecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long time;

    private String date;

}
