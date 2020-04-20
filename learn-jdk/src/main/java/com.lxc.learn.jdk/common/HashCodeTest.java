package com.lxc.learn.jdk.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/20
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class HashCodeTest {

    private Long id;

    public static void main(String[] args) {
        HashCodeTest test1 = new HashCodeTest().setId(11L);
        HashCodeTest test2 = new HashCodeTest().setId(11L);

        //HashSet 实现依赖于HashMap 因此，判读是否重复用到了hashCode和equals
        Set<HashCodeTest> set = new HashSet<>();
        set.add(test1);
        set.add(test2);

        Map map = new HashMap();
        map.put(test1,null);
        map.put(test2,null);

        log.info("{},{}",set.size(),map.size());
    }

/*equals 相等，hashCode必定相等，前提是需要实现hashCode*/
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((HashCodeTest)obj).getId();
    }
}
