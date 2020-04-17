package com.lxc.learn.junit.req;

import com.lxc.learn.common.util.core.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/17
 */
@Slf4j
@Getter
@Setter
public class OrderCreateReq extends BaseDto{

    //private static final long serialVersionUID = 1L;

    private Long userId;

    private List<Sku> skus;

    @Data
    public static class Sku extends BaseDto{

        private Long productId;

        private Long skuId;

        private Long count;

        private Long price;

        private String productName;

    }

}
