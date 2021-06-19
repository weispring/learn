package com.lxc.learn.jdk.proxy.javaagent;

import lang.String;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/19
 **/
public class TransformerByAsm implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, java.lang.String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }
}
