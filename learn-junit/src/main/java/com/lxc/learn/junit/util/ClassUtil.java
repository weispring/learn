package com.lxc.learn.junit.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/1
 */
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ClassUtil {
    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    public ClassUtil() {
    }


    /**
     * 获取指定包下，类(RestController)中包含指定注解的值的集合
     * @param pkg
     * @param recursive
     * @return
     */
    public static Map<String, String> getPermissions(String pkg, Boolean recursive) {
        if (null == recursive) {
            recursive = true;
        }

        List<Class<? extends Object>> list = null;
        list = getClassList(pkg, recursive, RestController.class);
        Map<String, Class<? extends Object>> beansWithAnnotation = new HashMap<>();

        for(int i = 0; i < list.size(); ++i) {
            log.debug(i + ":{}", list.get(i));
            beansWithAnnotation.put(i + ":", list.get(i));
        }

        return traverse(beansWithAnnotation);
    }

    public static Map<String, String> traverse(Map<String, Class<? extends Object>> map) {
        Class<? extends Object> clazz = null;
        Map<String, String> pers = new HashMap();
        Map<String, String> permissionList = new HashMap();
        Iterator var4 = map.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, Class<? extends Object>> entry = (Entry)var4.next();
            clazz = (Class)entry.getValue();
            Method[] methods = clazz.getDeclaredMethods();
            RequestMapping superMapping = (RequestMapping)clazz.getDeclaredAnnotation(RequestMapping.class);
            Method[] var8 = methods;
            int var9 = methods.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Method method = var8[var10];
                RequestMapping permissions = (RequestMapping)method.getDeclaredAnnotation(RequestMapping.class);
                RequestMapping rp = (RequestMapping)method.getDeclaredAnnotation(RequestMapping.class);
                if (null != permissions && null != rp) {
                    log.debug("permission is " + permissions.value()[0]);
                    String path = rp.value()[0];
                    if (null != superMapping) {
                        path = superMapping.value()[0] + rp.value()[0];
                    }
                    // 类+方法 ： 方法
                    permissionList.put(path, permissions.value()[0]);
                }
            }
        }

        var4 = permissionList.keySet().iterator();

        while(var4.hasNext()) {
            String path = (String)var4.next();
            String permission = (String)permissionList.get(path);
            log.debug("{}==>{}", path, permission);
            pers.put(path, permission);
        }

        return pers;
    }

    public static List<Class<? extends Object>> getClassList(String pkgName, boolean isRecursive, Class<? extends Annotation> annotation) {
        List<Class<? extends Object>> classList = new ArrayList();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            String strFile = pkgName.replaceAll("\\.", "/");
            Enumeration urls = loader.getResources(strFile);

            while(urls.hasMoreElements()) {
                URL url = (URL)urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    String pkgPath = url.getPath();
                    log.debug("protocol:" + protocol + " path:" + pkgPath);
                    if ("file".equals(protocol)) {
                        findClassName(classList, pkgName, (String)pkgPath, isRecursive, annotation);
                    } else if ("jar".equals(protocol)) {
                        findClassName(classList, pkgName, (URL)url, isRecursive, annotation);
                    }
                }
            }
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return classList;
    }

    public static void findClassName(List<Class<? extends Object>> clazzList, String pkgName, String pkgPath, boolean isRecursive, Class<? extends Annotation> annotation) {
        if (clazzList != null) {
            File[] files = filterClassFiles(pkgPath);
            log.debug("files:" + (files == null ? "null" : "length=" + files.length));
            if (files != null) {
                File[] var6 = files;
                int var7 = files.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    File f = var6[var8];
                    String fileName = f.getName();
                    String subPkgName;
                    if (f.isFile()) {
                        subPkgName = getClassName(pkgName, fileName);
                        addClassName(clazzList, subPkgName, annotation);
                    } else if (isRecursive) {
                        subPkgName = pkgName + "." + fileName;
                        String subPkgPath = pkgPath + "/" + fileName;
                        findClassName(clazzList, subPkgName, subPkgPath, true, annotation);
                    }
                }
            }

        }
    }

    public static void findClassName(List<Class<? extends Object>> clazzList, String pkgName, URL url, boolean isRecursive, Class<? extends Annotation> annotation) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();
        log.debug("jarFile:" + jarFile.getName());
        Enumeration jarEntries = jarFile.entries();

        while(jarEntries.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry)jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();
            String clazzName = jarEntryName.replace("/", ".");
            int endIndex = clazzName.lastIndexOf(".");
            String prefix = null;
            if (endIndex > 0) {
                String prefix_name = clazzName.substring(0, endIndex);
                endIndex = prefix_name.lastIndexOf(".");
                if (endIndex > 0) {
                    prefix = prefix_name.substring(0, endIndex);
                }
            }

            if (prefix != null && jarEntryName.endsWith(".class")) {
                if (prefix.equals(pkgName)) {
                    log.debug("jar entryName:" + jarEntryName);
                    addClassName(clazzList, clazzName, annotation);
                } else if (isRecursive && prefix.startsWith(pkgName)) {
                    log.debug("jar entryName:" + jarEntryName + " isRecursive:" + isRecursive);
                    addClassName(clazzList, clazzName, annotation);
                }
            }
        }

    }

    private static File[] filterClassFiles(String pkgPath) {
        return pkgPath == null ? null : (new File(pkgPath)).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });
    }

    private static String getClassName(String pkgName, String fileName) {
        int endIndex = fileName.lastIndexOf(".");
        String clazz = null;
        if (endIndex >= 0) {
            clazz = fileName.substring(0, endIndex);
        }

        String clazzName = null;
        if (clazz != null) {
            clazzName = pkgName + "." + clazz;
        }

        return clazzName;
    }

    private static void addClassName(List<Class<? extends Object>> clazzList, String clazzName, Class<? extends Annotation> annotation) {
        if (clazzList != null && clazzName != null) {
            Class clazz = null;

            try {
                log.debug("clazzName is {}", clazzName);
                clazzName = clazzName.replaceAll("\\.class", "");
                log.debug("clazzName is {}", clazzName);
                clazz = Class.forName(clazzName);
            } catch (ClassNotFoundException var5) {
                log.error("error : {}", var5);
            }

            if (clazz != null) {
                if (annotation == null) {
                    clazzList.add(clazz);
                    log.debug("add:{}", clazz);
                } else if (clazz.isAnnotationPresent(annotation)) {
                    clazzList.add(clazz);
                    log.debug("add annotation:{}", clazz);
                }
            }
        }

    }
}

