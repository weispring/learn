package com.lxc.learn.jdk.proxy.javaagent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/6/19
 **/
public class TransformerByJavassist implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (!className.startsWith("com/lxc/learn")){
                return classfileBuffer;
            }
            ClassPool classPool = ClassPool.getDefault();
            CtClass cls = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            CtMethod[] ctMethods = cls.getDeclaredMethods();
            for (CtMethod ctMethod : ctMethods){
                ctMethod.addLocalVariable("start", CtClass.longType);
                ctMethod.insertBefore("start = System.currentTimeMillis();");
                ctMethod.insertAfter("{ System.out.println(\"" + ctMethod.getLongName() + "耗时:\" + (System.currentTimeMillis() - start )); }");
            }
            return cls.toBytecode();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
