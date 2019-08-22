package com.lxc.learn.common.util;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@Slf4j
public class RefectUtils {
    private static final Logger logger = LoggerFactory.getLogger(RefectUtils.class);

    public RefectUtils() {
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == String.class || type == Character.class || type == Boolean.class || type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class || type == Object.class;
    }

    private static String getGetName(Field field) {
        String fieldName = field.getName();
        return field.getType().isPrimitive() && field.getType() == Boolean.class ? "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) : "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private static String getSetName(Field field) {
        String fieldName = field.getName();
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static Class<?> getFieldClass(Object obj, String fieldName) {
        Class clazz = obj.getClass();

        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field != null) {
                return field.getType();
            }
        } catch (SecurityException | NoSuchFieldException var5) {
            ;
        }

        return null;
    }

    public static Class<?> getFieldClassAllSupper(Object object, String fieldName) {
        Field field = getFieldAllSupper(object, fieldName);
        return field != null ? field.getType() : null;
    }

    public static Map<String, Field> getFields(Object obj) {
        Class<?> clazz = obj.getClass();
        HashMap fieldMap = new HashMap();

        while(clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            clazz = clazz.getSuperclass();
            Field[] var4 = fields;
            int var5 = fields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                fieldMap.put(field.getName(), field);
            }
        }

        return fieldMap;
    }

    public static Map<String, Method> getGetMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        Map<String, Field> fieldMap = new HashMap();
        Map<String, String> fieldGetMethodMap = new HashMap();

        HashMap methodMap;
        for(methodMap = new HashMap(); clazz != null; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            Method[] methods = clazz.getDeclaredMethods();
            Field[] var7 = fields;
            int var8 = fields.length;

            int var9;
            for(var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                fieldMap.put(field.getName(), field);
                fieldGetMethodMap.put(getGetName(field), field.getName());
            }

            Method[] var13 = methods;
            var8 = methods.length;

            for(var9 = 0; var9 < var8; ++var9) {
                Method method = var13[var9];
                String filedName = (String)fieldGetMethodMap.get(method.getName());
                if (!StringUtils.isEmpty(filedName)) {
                    methodMap.put(filedName, method);
                }
            }
        }

        return methodMap;
    }

    public static Map<String, Method> getSetMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        Map<String, Field> fieldMap = new HashMap();
        Map<String, String> fieldSetMethodMap = new HashMap();

        HashMap methodMap;
        for(methodMap = new HashMap(); clazz != null; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            Method[] methods = clazz.getDeclaredMethods();
            Field[] var7 = fields;
            int var8 = fields.length;

            int var9;
            for(var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                fieldMap.put(field.getName(), field);
                fieldSetMethodMap.put(getSetName(field), field.getName());
            }

            Method[] var13 = methods;
            var8 = methods.length;

            for(var9 = 0; var9 < var8; ++var9) {
                Method method = var13[var9];
                String filedName = (String)fieldSetMethodMap.get(method.getName());
                if (!StringUtils.isEmpty(filedName)) {
                    methodMap.put(filedName, method);
                }
            }
        }

        return methodMap;
    }

    public static void copyObj(Object src, Object target, RefectUtils.FieldDecorator decorator) {
        if (target != null && src != null) {
            Map<String, Method> setMap = getSetMethods(target);
            Map<String, Field> fieldMap = getFields(target);
            Map<String, Method> getMap = getGetMethods(src);
            Iterator var6 = setMap.keySet().iterator();

            while(var6.hasNext()) {
                String fieldName = (String)var6.next();
                Method get = (Method)getMap.get(fieldName);

                try {
                    if (get != null) {
                        Object value = get.invoke(src);
                        Field field = (Field)fieldMap.get(fieldName);
                        if (value == null && field.getType() == String.class) {
                            ((Method)setMap.get(fieldName)).invoke(target, "");
                        } else {
                            if (decorator != null && value != null && value.getClass() != field.getType()) {
                                value = decorator.doInvoke(fieldName, value);
                            }

                            if (value != null && value.getClass() == field.getType()) {
                                ((Method)setMap.get(fieldName)).invoke(target, value);
                            }
                        }
                    }
                } catch (Exception var11) {
                    logger.warn("对象copy错误 fieldName={}", fieldName, var11);
                }
            }

        }
    }

    public static Field getFieldAllSupper(Object object, String fieldName) {
        Field field = null;
        Class clazz = object.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception var5) {
                clazz = clazz.getSuperclass();
            }
        }

        return null;
    }

    public static Method getDeclaredMethod(Object object, String methodName, Class... parameterTypes) {
        Method method = null;
        Class clazz = object.getClass();

        while(clazz != Object.class) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception var6) {
                clazz = clazz.getSuperclass();
            }
        }

        return null;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        method.setAccessible(true);

        try {
            if (null != method) {
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException var6) {
            log.error(var6.getMessage(),var6);
        } catch (IllegalAccessException var7) {
            log.error(var7.getMessage(),var7);
        } catch (InvocationTargetException var8) {
            log.error(var8.getMessage(),var8);
        }

        return null;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getFieldAllSupper(object, fieldName);
        field.setAccessible(true);

        try {
            field.set(object, value);
        } catch (IllegalArgumentException var5) {
            log.error(var5.getMessage(),var5);
        } catch (IllegalAccessException var6) {
            log.error(var6.getMessage(),var6);
        }

    }

    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getFieldAllSupper(object, fieldName);
        field.setAccessible(true);

        try {
            return field.get(object);
        } catch (Exception var4) {
            log.error(var4.getMessage());
            return null;
        }
    }

    public static String getPackage(Class<?> clazz) {
        Package pck = clazz.getPackage();
        return null != pck ? pck.getName() : "没有包！";
    }

    public static String getSuperClassName(Class<?> clazz) {
        Class<?> superClass = clazz.getSuperclass();
        return null != superClass ? superClass.getName() : "没有父类！";
    }

    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }

    public static List<String> getInterfaces(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        int len = interfaces.length;
        List<String> list = new ArrayList();

        for(int i = 0; i < len; ++i) {
            Class<?> itfc = interfaces[i];
            String interfaceName = itfc.getSimpleName();
            list.add(interfaceName);
        }

        return list;
    }

    public static List<StringBuilder> getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int len = fields.length;
        List<StringBuilder> list = new ArrayList();
        StringBuilder sb = null;

        for(int i = 0; i < len; ++i) {
            Field field = fields[i];
            sb = new StringBuilder();
            String modifier = Modifier.toString(field.getModifiers());
            sb.append(modifier + " ");
            Class<?> type = field.getType();
            String typeName = type.getSimpleName();
            sb.append(typeName + " ");
            String fieldName = field.getName();
            sb.append(fieldName + ";");
            list.add(sb);
        }

        return list;
    }

    public static List<StringBuilder> getPublicFields(Class<?> clazz) {
        Field[] fields = clazz.getFields();
        int len = fields.length;
        List<StringBuilder> list = new ArrayList();
        StringBuilder sb = null;

        for(int i = 0; i < len; ++i) {
            Field field = fields[i];
            sb = new StringBuilder();
            String modifier = Modifier.toString(field.getModifiers());
            sb.append(modifier + " ");
            Class<?> type = field.getType();
            String typeName = type.getSimpleName();
            sb.append(typeName + " ");
            String fieldName = field.getName();
            sb.append(fieldName + ";");
            list.add(sb);
        }

        return list;
    }

    public static List<StringBuilder> getConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        int len = constructors.length;
        List<StringBuilder> list = new ArrayList();
        StringBuilder sb = null;

        for(int i = 0; i < len; ++i) {
            Constructor<?> constructor = constructors[i];
            sb = new StringBuilder();
            String modifier = Modifier.toString(constructor.getModifiers());
            sb.append(modifier + " ");
            String constructorName = clazz.getSimpleName();
            sb.append(constructorName + " (");
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            int length = parameterTypes.length;

            for(int j = 0; j < length; ++j) {
                Class<?> parameterType = parameterTypes[j];
                String parameterTypeName = parameterType.getSimpleName();
                if (j < length - 1) {
                    sb.append(parameterTypeName + ", ");
                } else {
                    sb.append(parameterTypeName);
                }
            }

            sb.append(") {}");
            list.add(sb);
        }

        return list;
    }

    public static List<StringBuilder> getMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        int len = methods.length;
        List<StringBuilder> list = new ArrayList();
        StringBuilder sb = null;

        for(int i = 0; i < len; ++i) {
            Method method = methods[i];
            sb = new StringBuilder();
            String modifier = Modifier.toString(method.getModifiers());
            sb.append(modifier + " ");
            Class<?> returnClass = method.getReturnType();
            String returnType = returnClass.getSimpleName();
            sb.append(returnType + " ");
            String methodName = method.getName();
            sb.append(methodName + " (");
            Class<?>[] parameterTypes = method.getParameterTypes();
            int length = parameterTypes.length;

            for(int j = 0; j < length; ++j) {
                Class<?> parameterType = parameterTypes[j];
                String parameterTypeName = parameterType.getSimpleName();
                if (j < length - 1) {
                    sb.append(parameterTypeName + ", ");
                } else {
                    sb.append(parameterTypeName);
                }
            }

            sb.append(") {}");
            list.add(sb);
        }

        return list;
    }

    public static List<StringBuilder> getPublicMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        int len = methods.length;
        List<StringBuilder> list = new ArrayList();
        StringBuilder sb = null;

        for(int i = 0; i < len; ++i) {
            Method method = methods[i];
            sb = new StringBuilder();
            String modifier = Modifier.toString(method.getModifiers());
            sb.append(modifier + " ");
            Class<?> returnClass = method.getReturnType();
            String returnType = returnClass.getSimpleName();
            sb.append(returnType + " ");
            String methodName = method.getName();
            sb.append(methodName + " (");
            Class<?>[] parameterTypes = method.getParameterTypes();
            int length = parameterTypes.length;

            for(int j = 0; j < length; ++j) {
                Class<?> parameterType = parameterTypes[j];
                String parameterTypeName = parameterType.getSimpleName();
                if (j < length - 1) {
                    sb.append(parameterTypeName + ", ");
                } else {
                    sb.append(parameterTypeName);
                }
            }

            sb.append(") {}");
            list.add(sb);
        }

        return list;
    }

    public static List<String> getAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        int len = annotations.length;
        List<String> list = new ArrayList();

        for(int i = 0; i < len; ++i) {
            Annotation annotation = annotations[i];
            String annotationName = annotation.annotationType().getSimpleName();
            list.add(annotationName);
        }

        return list;
    }

    public static Class<?> getSuperClassGenericParameterizedType(Class<?> clazz) {
        Type genericSuperClass = clazz.getGenericSuperclass();
        Class<?> superClassGenericParameterizedType = null;
        if (genericSuperClass instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)genericSuperClass;
            Type superClazz = pt.getActualTypeArguments()[0];
            superClassGenericParameterizedType = (Class)superClazz;
        }

        return superClassGenericParameterizedType;
    }

    public static List<Class<?>> getInterfaceGenericParameterizedTypes(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        int len = genericInterfaces.length;
        List<Class<?>> list = new ArrayList();

        for(int i = 0; i < len; ++i) {
            Type genericInterface = genericInterfaces[i];
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)genericInterface;
                Type[] interfaceTypes = pt.getActualTypeArguments();
                int length = interfaceTypes.length;

                for(int j = 0; j < length; ++j) {
                    Type interfaceType = interfaceTypes[j];
                    Class<?> interfaceClass = (Class)interfaceType;
                    list.add(interfaceClass);
                }
            }
        }

        return list;
    }

    public static void printPackage(Class<?> clazz) {
        System.out.println(getPackage(clazz));
    }

    public static void printSuperClassName(Class<?> clazz) {
        System.out.println(getSuperClassName(clazz));
    }

    public static void printClassName(Class<?> clazz) {
        System.out.println(getClassName(clazz));
    }

    public static void printInterfaces(Class<?> clazz) {
        List<String> list = getInterfaces(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println((String)list.get(i));
            }
        } else {
            System.out.println("没有实现接口！");
        }

    }

    public static void printFields(Class<?> clazz) {
        List<StringBuilder> list = getFields(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println(list.get(i));
            }
        } else {
            System.out.println("没有属性！");
        }

    }

    public static void printPublicFields(Class<?> clazz) {
        List<StringBuilder> list = getPublicFields(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println(list.get(i));
            }
        } else {
            System.out.println("没有属性！");
        }

    }

    public static void printConstructors(Class<?> clazz) {
        List<StringBuilder> list = getConstructors(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println(list.get(i));
            }
        } else {
            System.out.println("没有构造方法！");
        }

    }

    public static void printMethods(Class<?> clazz) {
        List<StringBuilder> list = getMethods(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println(list.get(i));
            }
        } else {
            System.out.println("没有方法！");
        }

    }

    public static void printPublicMethods(Class<?> clazz) {
        List<StringBuilder> list = getPublicMethods(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println(list.get(i));
            }
        } else {
            System.out.println("没有方法！");
        }

    }

    public static void printAnnotations(Class<?> clazz) {
        List<String> list = getAnnotations(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println((String)list.get(i));
            }
        } else {
            System.out.println("没有注解！");
        }

    }

    public static void printSuperClassGenericParameterizedType(Class<?> clazz) {
        Class<?> superClassGenericParameterizedType = getSuperClassGenericParameterizedType(clazz);
        if (null != superClassGenericParameterizedType) {
            System.out.println(superClassGenericParameterizedType.getSimpleName());
        } else {
            System.out.println("父类没有泛型！");
        }

    }

    public static void printInterfaceGenericParameterizedTypes(Class<?> clazz) {
        List<Class<?>> list = getInterfaceGenericParameterizedTypes(clazz);
        int size = list.size();
        if (0 < size) {
            for(int i = 0; i < size; ++i) {
                System.out.println(((Class)list.get(i)).getSimpleName());
            }
        } else {
            System.out.println("没有泛型接口！");
        }

    }

    public static void printAll(Class<?> clazz) {
        System.out.print("【包名】  ");
        printPackage(clazz);
        System.out.print("【类名】  ");
        System.out.println(clazz.getSimpleName());
        System.out.println("\n【父类全类名】");
        printSuperClassName(clazz);
        System.out.println("【全类名】");
        printClassName(clazz);
        System.out.println("\n【所有已实现的接口】");
        printInterfaces(clazz);
        System.out.println("\n【属性】");
        printFields(clazz);
        System.out.println("\n【构造方法】");
        printConstructors(clazz);
        System.out.println("\n【方法】");
        printMethods(clazz);
        System.out.println("\n【公共的属性】");
        printPublicFields(clazz);
        System.out.println("\n【公共的方法】");
        printPublicMethods(clazz);
    }

    public static Object getNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    public static Constructor<?> getConstructor(Class<?> clazz, Class... parameterTypes) throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredConstructor(parameterTypes);
    }

    public static Object getNewInstance(Constructor<?> constructor, Object... initargs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        constructor.setAccessible(true);
        return constructor.newInstance(initargs);
    }

    public static void setField(Class<?> clazz, String name, Object obj, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static Method getMethod(Class<?> clazz, String name, Class... parameterTypes) throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredMethod(name, parameterTypes);
    }

    public static Object invokeMethod(Method method, Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    public interface FieldDecorator {
        Object doInvoke(String var1, Object var2);
    }
}

