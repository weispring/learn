package com.lxc.learn.netty.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/19 22:31
 * @Description:
 */
@Slf4j
public class ByteBufferTest {

    /**
     * 如果写模式下，则不能读取到数据
     * 如果数据已经读了一次，则第二次不会读取到任何数据
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
        byteBuffer.put("lixianchun".getBytes());
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        log.info("{}",new String(bytes,"UTF-8"));
    }
}
