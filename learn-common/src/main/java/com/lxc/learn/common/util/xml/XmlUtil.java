package com.lxc.learn.common.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/23 15:30
 * @Description:
 */
@Slf4j
public class XmlUtil {

    public static void main(String[] args) {
        log.trace("--------------------");
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(new Class[]{Order.class,OrderItem.class});

        String xmlStr = xstream.toXML(new User().setName("lichun").setPhone("123456"));

        log.info("toxml :{}",xmlStr);
        User user = (User) xstream.fromXML(xmlStr);
        log.info("user:{}",user);

        List<OrderItem> items = new ArrayList<OrderItem>(2);
        OrderItem item = new OrderItem().setCount(1L).setOrderId(666L);
        items.add(item);
        Order order = new Order();
        order.setAmount("1000");
        order.setItems(items);

        String orderXml = xstream.toXML(order);
        log.info("order toxml :{}",orderXml);
        Order o = (Order) xstream.fromXML(orderXml);
        log.info("user:{}",o);


    }



    @Data
    @Accessors(chain = true)
    public static class User{

        private String name;

        private Long id;

        private String phone;

    }



    @Data
    @Accessors(chain = true)
    @XStreamAlias("order")
    public static class Order{

        private Long id;

        @XStreamAlias("money")
        private String amount;

        //@XStreamImplicit
        private List<OrderItem> items;

    }


    @Data
    @Accessors(chain = true)
    @XStreamAlias("orderItem")
    public static class OrderItem{

        private Long id;

        //@XStreamAsAttribute
        private Long count;

        @XStreamOmitField
        private Long orderId;

    }

}
