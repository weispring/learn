package com.lxc.learn.jdk.basedatatype;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/22 18:01
 */
@Slf4j
public class ComparableTest {

    @Data
    public static class User implements Comparable{

        private String name;

        private Integer score;

        private String no;

        @Override
        public int compareTo(Object o) {
            User u = (User) o;
            if (score < ((User) o).getScore()){
                return -1;
            }else if (score == ((User) o).getScore()){
                return 0;
            }else {
                return 1;
            }
        }
    }

    @Test
    public void sort(){
        List<User> list = new ArrayList<>();
        for (int i=0;i<12;i++){
            User user = new User();
            user.setScore(new Random().nextInt(100));
            list.add(user);
        }
        User[] users = new User[list.size()];
        //TODO Arrays.sort() 采用了2种排序算法 -- 基本类型数据使用快速排序法，对象数组使用归并排序。
        //markwarn
        Arrays.sort(list.toArray(users), new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getScore() < o2.getScore()){
                    return -1;
                }else if (o1.getScore() == o2.getScore()){
                    return 0;
                }else {
                    return 1;
                }
            }
        });
        log.info("after sort :{}", users);

    }


}
