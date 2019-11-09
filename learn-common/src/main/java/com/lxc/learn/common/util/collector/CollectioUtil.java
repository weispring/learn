package com.lxc.learn.common.util.collector;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Collection;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/9 12:06
 */
@Slf4j
@UtilityClass
public class CollectioUtil {

    public boolean isEmpty(Collection collection){
        return (collection == null || collection.isEmpty() || collection.size()== 0)?true:false;
    }

    public boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }
}
