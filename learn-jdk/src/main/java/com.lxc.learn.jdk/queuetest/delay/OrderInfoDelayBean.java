package com.lxc.learn.jdk.queuetest.delay;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/20 9:02
 */
@Slf4j
@Getter
@Setter
public class OrderInfoDelayBean implements Delayed {

    //必须实现Delayed接口，重写compareTo方法。该方法提供一个和getDelay方法一致的排序。即从队列里取过期元素的顺序即按照此顺序。

    private static final long serialVersionUID = 6255640460985141483L;

    private long dueTime;

    private String serialNumber;

    private Date lastPrintDate;

    @Override
    public int compareTo(Delayed o) {
        final OrderInfoDelayBean other = (OrderInfoDelayBean) o;
        if (this.dueTime<other.dueTime) {
            return -1;
        } else if (this.dueTime > other.dueTime) {
            return 1;
        }
        return 0;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(dueTime - new Date().getTime(), TimeUnit.NANOSECONDS);
    }

    public long getDueTime() {
        return dueTime;
    }

    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final OrderInfoDelayBean other = (OrderInfoDelayBean) obj;
        if (!this.getSerialNumber().equals(other.getSerialNumber())) {
            return false;
        }
        return true;
    }
}
