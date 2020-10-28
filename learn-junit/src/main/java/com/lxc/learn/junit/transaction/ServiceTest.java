package com.lxc.learn.junit.transaction;

import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/12 20:12
 */
@Slf4j
@Service
@Transactional
public class ServiceTest {

    /**
     * REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
     * SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行。
     * MANDATORY：支持当前事务，如果当前没有事务，就抛出异常。
     * REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起。
     * NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
     * NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。
     * NESTED：支持当前事务，如果当前事务存在，则执行一个嵌套事务，如果当前没有事务，就新建一个事务。


     */
    @Autowired
    private ServiceA serviceA;
    @Autowired
    private ServiceB serviceB;

    public void savetest(){
        serviceA.saveRequired();
        serviceB.saveRequired();
    }


    /**
     *   PROPAGATION_REQUIRES_NEW 启动一个新的, 不依赖于环境的 "内部" 事务.
     * 这个事务将被完全 commited 或 rolled back 而不依赖于外部事务, 它拥有自己的隔离范围, 自己的锁, 等等.
     * 当内部事务开始执行时, 外部事务将被挂起, 内务事务结束时, 外部事务将继续执行. 
     * 2. 内部事物回滚不影响外部事物提交
     * 3. 外部事物回滚，内部事物不受影响
     * 4. 内部异常，会导致外部回滚
     * 两个事务互不影响
     */
    public void saveRequiredAndRequiredNew(){
        serviceA.saveRequired();
        try {
            serviceB.saveRequiresNews();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        throw new RuntimeException("saveRequiredAndRequiredNew");
    }

    /**
     * 第二个事物不受第一个事物的影响，autoCommit
     *
     * 4. 内部异常，会导致外部回滚
     */
    public void saveRequiredAndNotSupportted(){
        serviceA.saveRequired();
        serviceB.saveNotSupportted();
    }


    /**
        另一方面, PROPAGATION_NESTED 开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正的子事务. 潜套事务开始执行时,
        它将取得一个 savepoint. 如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分,
        只有外部事务结束后它才会被提交. 
        由此可见, PROPAGATION_REQUIRES_NEW 和 PROPAGATION_NESTED 的最大区别在于,
        PROPAGATION_REQUIRES_NEW 完全是一个新的事务,
        而 PROPAGATION_NESTED 则是外部事务的子事务, 如果外部事务 commit, 潜套事务也会被 commit,
        这个规则同样适用于 roll back. 
     2. 内部事物回滚不影响外部事物提交
     3. 外部事物回滚，会导致两个事物都回滚
     4. 内部异常，会导致外部回滚
     */
    public void saveRequiredAndNested(){
        serviceA.saveRequired();
        try {
            serviceB.saveNested();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        throw new RuntimeException("saveRequiredAndNested");
    }


    /** Spring中REQUIRES_NEW带来的事务问题
     1、 method_A（T1事务）中查询了某条记录A（A.name = 1）；然后调用method_B(T2事务，并且是REQUIRES_NEW  )
     2、在 method_B中修改了记录A并提交保存（A.name = 2）;（method_B方法结束）
     3、然后回到 method_A  中再次查询这条记录A，发现得到的A.name = 1；但是此时数据库中的A.name = 2 。
     */
    @Autowired
    private UserMapper userMapper;

    public void testRequiredNew(){
        User user = userMapper.selectById(3);
        log.info("user.name:{}", user.getName());
        serviceA.updateUser();
        user = userMapper.selectById(3);
        log.info("user.name:{}", user.getName());
    }

}
