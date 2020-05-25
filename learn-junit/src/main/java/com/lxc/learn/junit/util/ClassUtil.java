package com.lxc.learn.junit.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/17 16:15
 */
@Slf4j
public class ClassUtil {
    public static ClassLoader overridenClassLoader;
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    public ClassUtil() {
    }

    public static ClassLoader getContextClassLoader() {
        return overridenClassLoader != null ? overridenClassLoader : Thread.currentThread().getContextClassLoader();
    }

    public static final Set<Field> getField(String className, boolean extendsField) {
        Class classz = loadClass(className);
        Field[] fields = classz.getFields();
        Set<Field> set = new HashSet();
        Field[] fieldz;
        int var7;
        if (fields != null) {
            fieldz = fields;
            int var6 = fields.length;

            for(var7 = 0; var7 < var6; ++var7) {
                Field f = fieldz[var7];
                set.add(f);
            }
        }

        if (extendsField) {
            fieldz = classz.getDeclaredFields();
            if (fieldz != null) {
                Field[] var10 = fieldz;
                var7 = fieldz.length;

                for(int var11 = 0; var11 < var7; ++var11) {
                    Field f = var10[var11];
                    set.add(f);
                }
            }
        }

        return set;
    }

    public static final String[] getPublicField(String className, boolean extendsField) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet();
        Field[] fields = classz.getDeclaredFields();
        Field[] fieldz;
        int var7;
        if (fields != null) {
            fieldz = fields;
            int var6 = fields.length;

            for(var7 = 0; var7 < var6; ++var7) {
                Field f = fieldz[var7];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("public")) {
                    set.add(f.getName());
                }
            }
        }

        if (extendsField) {
            fieldz = classz.getFields();
            if (fieldz != null) {
                Field[] var10 = fieldz;
                var7 = fieldz.length;

                for(int var11 = 0; var11 < var7; ++var11) {
                    Field f = var10[var11];
                    set.add(f.getName());
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final String[] getProtectedField(String className) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            Field[] var4 = fields;
            int var5 = fields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field f = var4[var6];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("protected")) {
                    set.add(f.getName());
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final String[] getPrivateField(String className) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            Field[] var4 = fields;
            int var5 = fields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field f = var4[var6];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f.getName());
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final String[] getPublicMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }

        Set<String> set = new HashSet();
        if (methods != null) {
            Method[] var5 = methods;
            int var6 = methods.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method f = var5[var7];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("public")) {
                    set.add(f.getName());
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final String[] getProtectedMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }

        Set<String> set = new HashSet();
        if (methods != null) {
            Method[] var5 = methods;
            int var6 = methods.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method f = var5[var7];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("protected")) {
                    set.add(f.getName());
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final String[] getPrivateMethod(String className) {
        Class classz = loadClass(className);
        Method[] methods = classz.getDeclaredMethods();
        Set<String> set = new HashSet();
        if (methods != null) {
            Method[] var4 = methods;
            int var5 = methods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method f = var4[var6];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f.getName());
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final List<Method> getPrivateMethods(String className) {
        Class classz = loadClass(className);
        Method[] methods = classz.getDeclaredMethods();
        List<Method> set = new ArrayList<>();
        if (methods != null) {
            Method[] var4 = methods;
            int var5 = methods.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method f = var4[var6];
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f);
                }
            }
        }
        return set;
    }


    public static final String[] getMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }

        Set<String> set = new HashSet();
        if (methods != null) {
            Method[] var5 = methods;
            int var6 = methods.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Method f = var5[var7];
                set.add(f.getName());
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }

    public static final void setter(Object obj, String att, Object value, Class<?> type) throws InvocationTargetException, IllegalAccessException {
        try {
            String name = att.substring(0, 1).toUpperCase() + att.substring(1);
            Method met = obj.getClass().getMethod("set" + name, type);
            met.invoke(obj, value);
        } catch (NoSuchMethodException var6) {
            log.error(var6.getMessage(), var6);
        }

    }


    public static final List<String> getClassNameByJar(String jarPath) {
        ArrayList myClassName = new ArrayList();

        try {
            JarFile jarFile = new JarFile(jarPath);
            Throwable var3 = null;

            try {
                Enumeration entrys = jarFile.entries();

                while(entrys.hasMoreElements()) {
                    JarEntry jarEntry = (JarEntry)entrys.nextElement();
                    String entryName = jarEntry.getName();
                    if (entryName.endsWith(".class")) {
                        entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                        myClassName.add(entryName);
                    }
                }
            } catch (Throwable var15) {
                var3 = var15;
                throw var15;
            } finally {
                if (jarFile != null) {
                    if (var3 != null) {
                        try {
                            jarFile.close();
                        } catch (Throwable var14) {
                            var3.addSuppressed(var14);
                        }
                    } else {
                        jarFile.close();
                    }
                }

            }
        } catch (IOException var17) {
            log.error(var17.getMessage(), var17);
        }

        return myClassName;
    }

    public static final Class loadClass(String className) {
        Class theClass = null;

        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException var3) {
            log.error(var3.getMessage(), var3);
        }
        return theClass;
    }



    public static final String getSuperClass(String className) {
        Class classz = loadClass(className);
        Class superclass = classz.getSuperclass();
        return superclass.getName();
    }

    public static final String[] getSuperClassChian(String className) {
        Class classz = loadClass(className);
        List<String> list = new ArrayList();
        Class superclass = classz.getSuperclass();
        String superName = superclass.getName();
        if (!"java.lang.Object".equals(superName)) {
            list.add(superName);
            list.addAll(Arrays.asList(getSuperClassChian(superName)));
        } else {
            list.add(superName);
        }

        return (String[])list.toArray(new String[list.size()]);
    }

    public static final String[] getInterfaces(String className, boolean extendsInterfaces) {
        Class classz = loadClass(className);
        List<String> list = new ArrayList();
        Class[] interfaces = classz.getInterfaces();
        int var7;
        if (interfaces != null) {
            Class[] var5 = interfaces;
            int var6 = interfaces.length;

            for(var7 = 0; var7 < var6; ++var7) {
                Class inter = var5[var7];
                list.add(inter.getName());
            }
        }

        if (extendsInterfaces) {
            String[] superClass = getSuperClassChian(className);
            String[] var11 = superClass;
            var7 = superClass.length;

            for(int var12 = 0; var12 < var7; ++var12) {
                String c = var11[var12];
                list.addAll(Arrays.asList(getInterfaces(c, false)));
            }
        }

        return (String[])list.toArray(new String[list.size()]);
    }

    private static ArrayList<Class> getAllClass(String packagename) {
        ArrayList<Class> list = new ArrayList();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagename.replace('.', '/');

        try {
            ArrayList<File> fileList = new ArrayList();
            Enumeration enumeration = classLoader.getResources("../bin/" + path);

            while(enumeration.hasMoreElements()) {
                URL url = (URL)enumeration.nextElement();
                fileList.add(new File(url.getFile()));
            }

            for(int i = 0; i < fileList.size(); ++i) {
                list.addAll(findClass((File)fileList.get(i), packagename));
            }
        } catch (IOException var7) {
            log.error(var7.getMessage() ,var7);
        }
        return list;
    }

    private static ArrayList<Class> findClass(File file, String packagename) {
        ArrayList<Class> list = new ArrayList();
        if (!file.exists()) {
            return list;
        } else {
            File[] files = file.listFiles();
            File[] var4 = files;
            int var5 = files.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File file2 = var4[var6];
                if (file2.isDirectory()) {
                    if (!file2.getName().contains(".")) {
                        ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
                        list.addAll(arrayList);
                    }
                } else if (file2.getName().endsWith(".class")) {
                    try {
                        list.add(Class.forName(packagename + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
                    } catch (ClassNotFoundException var9) {
                        log.error(var9.getMessage(), var9);
                    }
                }
            }

            return list;
        }
    }


    /**
     * 如是8种基本类型的包装类，则返回true
     */
    public static boolean isPrimitive(Class cla){
        String claName = cla.getName();
        if (claName.equals(String.class.getName())
                || claName.equals(Integer.class.getName())
                || claName.equals(Long.class.getName())
                || claName.equals(Short.class.getName())
                || claName.equals(Double.class.getName())
                || claName.equals(Byte.class.getName())
                || claName.equals(Float.class.getName())
                || claName.equals(Boolean.class.getName())){
            return true;
        }
        return false;
    }
}
