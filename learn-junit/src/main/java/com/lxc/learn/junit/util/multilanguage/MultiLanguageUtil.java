package com.lxc.learn.junit.util.multilanguage;

import com.baomidou.mybatisplus.plugins.Page;
import com.lxc.learn.junit.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/17 15:13
 */
@Slf4j
public class MultiLanguageUtil {

    private static ThreadLocal multiThreadLocal = new InheritableThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return MultiLanguageEnum.ZH_HK.getCode();
        }


    };

    //set线程不安全，但是同一个set可以多线程迭代（前提set 不允许被修改），因为每次产生新的迭代器new iterator
    private static Map<String,Set<Field>> cacheOriginFields = new ConcurrentHashMap<String,Set<Field>>(128);
    private static Map<String,Set<Field>> cacheComplextFields = new ConcurrentHashMap<String,Set<Field>>(64);


    protected static void setValue(Object value){
        multiThreadLocal.set(value);
    }

    protected static void remove(){
        multiThreadLocal.remove();
    }


    public static String getValue(){
        return (String) multiThreadLocal.get();
    }

    public static Integer getLanguageType(){
        return MultiLanguageEnum.valueByCode(getValue()).getLanguageType();
    }


    /**
     * 是否需要进行多语言属性值处理
     * @param object
     * @return
     * null 不用处理
     */
    private static Object checkReplace(Object object,String languageCode){
        if (languageCode != null){
            if (MultiLanguageEnum.ZH_HK.getCode().equals(languageCode)){
                return null;
            }
        }else {
            if (MultiLanguageEnum.ZH_HK.getCode().equals(MultiLanguageUtil.getValue())){
                return null;
            }
        }
        if (object == null){
            return null;
        }
        Object o = null;
 /*       if (object instanceof BaseResponse){
            o = ((BaseResponse) object).getDataInfo();
        }else if (object instanceof PageResponse){
            o = ((PageResponse) object).getRecords();
        }else*/ if (object instanceof Page){
            o = ((Page) object).getRecords();
        }else {
            o = object;
        }
        if (o instanceof List){
            if (((List)o).size() == 0){
                return null;
            }
        }
        return o;
    }


    /**
     * 前提：接口已经经过过滤器处理
     * 替换自身属性值,仅供切面内部调用，外部不允许调用
     * @param object
     */
    public static void replaceMultiLanguage(Object object){
        Object o = null;
        if ((o = checkReplace(object,null)) == null){
            return;
        }
        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> multilangField = getEnglishField(classObject.getClass().getName());
        //校验是否存在多语言字段
        if (multilangField.size() == 0){
            //MultiLanguageUtil.remove();
            return;
        }

        //多语言属性值处理
        if (o instanceof List){
            for (Object obj : (List)o){
                replaceObject(obj, multilangField,null,MultiLanguageUtil.getValue());
            }
        }else {
            replaceObject(o, multilangField,null,MultiLanguageUtil.getValue());
        }
    }

    private static Object replaceMultiLanguage(Object object, Object newObject){
        Object o = null;
        if ((o = checkReplace(object,null)) == null){
            return null;
        }
        //校验是否存在多语言字段
        boolean match = false;
        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> fields = ClassUtil.getField(classObject.getClass().getName(), true);
        for (Field field : fields){
            if (field.getName().endsWith(MultiLanguageEnum.ZH_CN.getFieldSuffix())
                    || field.getName().endsWith(MultiLanguageEnum.EN_US.getFieldSuffix())){
                match = true;
            }
        }
        if (!match){
            return null;
        }

        //多语言属性值处理
        if (o instanceof List){
            Class classz = ClassUtil.loadClass(newObject.getClass().getName());
            List list = new ArrayList(((List) o).size());
            try {
                for (int i=0;i<((List) o).size();i++){
                    list.add(classz.newInstance());
                }
            }catch (Exception e){
                log.error("创建对象错误：{}", e.getMessage(),e);
                return null;
            }

            for (int i=0;i<((List) o).size();i++){
                replaceObject(((List) o).get(i), fields,list.get(i),MultiLanguageUtil.getValue());
            }
            return list;
        }else {
            replaceObject(o, fields,newObject,MultiLanguageUtil.getValue());
            return newObject;
        }
    }


    private static void replaceObject(Object object, Set<Field> fields, Object newObject,String languageCode){
        String suffix = null;
        if (MultiLanguageEnum.EN_US.getCode().equals(languageCode)){
            suffix = MultiLanguageEnum.EN_US.getFieldSuffix();
        }else if (MultiLanguageEnum.ZH_CN.getCode().equals(languageCode)){
            suffix = MultiLanguageEnum.ZH_CN.getFieldSuffix();
        }else {
            return;
        }
        if (suffix == null){
            return;
        }
        Class classz = ClassUtil.loadClass(object.getClass().getName());
        Class newClass = null;
        if (newObject != null){
            newClass = ClassUtil.loadClass(newObject.getClass().getName());
        }

        for (Field f : fields){
            replaceRespField(object, newObject, suffix, classz, newClass, f);
        }
    }

    private static void replaceRespField(Object object, Object newObject, String suffix, Class classz, Class newClass, Field f) {
        Method getter;
        Method setter;
        try {
            if (newClass != null) {
                getter = newClass.getMethod("get" + f.getName().substring(0, 1).toUpperCase()
                        + f.getName().substring(1) + suffix);
                setter = newClass.getMethod("set" + f.getName().substring(0, 1).toUpperCase()
                        + f.getName().substring(1), f.getType());
                setter.invoke(newObject, getter.invoke(object));
            }else {
                getter = classz.getMethod("get" + f.getName().substring(0, 1).toUpperCase()
                        + f.getName().substring(1) + suffix);
                setter = classz.getMethod("set" + f.getName().substring(0, 1).toUpperCase()
                        + f.getName().substring(1), f.getType());
                setter.invoke(object, getter.invoke(object));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 替换自身属性值,作为工具类使用
     * @param object
     */
    public static void replaceMultiLanguage(Object object, String languageCode){
        if (StringUtils.isEmpty(languageCode)){
            return;
        }
        Object o = null;
        if ((o = checkReplace(object,languageCode)) == null){
            return;
        }

        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> zhHkFields = getEnglishField(classObject.getClass().getName());
        //校验是否存在多语言字段
        if (zhHkFields.size() == 0){
            return;
        }

        //多语言属性值处理
        if (o instanceof List){
            for (Object obj : (List)o){
                replaceObject(obj, zhHkFields,null,MultiLanguageUtil.getValue());
            }
        }else {
            replaceObject(o, zhHkFields,null,MultiLanguageUtil.getValue());
        }
    }



   /* private static Set<Field> getMultiFields(Set<Field> englishFields, Set<Field> fields){
        Set<Field> set = new HashSet<>();
        Iterator<Field> iterator = englishFields.iterator();
        while (iterator.hasNext()){
            Field englishField = iterator.next();
            Iterator<Field> sourceIterator = fields.iterator();
            while (sourceIterator.hasNext()){
                Field sourceField = sourceIterator.next();
                if (englishField.getName().length() > MultiLanguageEnum.EN_US.getFieldSuffix().length() &&
                        sourceField.getName().equals(
                        englishField.getName().substring(0,englishField.getName().length()- MultiLanguageEnum.EN_US.getFieldSuffix().length()))){
                    set.add(sourceField);
                }
            }
        }
        return set;
    }*/


    private static Set<Field> getEnglishField(String className){
        Set<Field> multilangField = cacheOriginFields.get(className);
        if(multilangField != null){
            return multilangField;
        }
        multilangField = new HashSet<>();
        Set<Field> fields = ClassUtil.getField(className, true);
        Set<String> fieldNames = new HashSet<>();
        for (Field field : fields){
            if (field.getName().endsWith(MultiLanguageEnum.EN_US.getFieldSuffix())){
                fieldNames.add(field.getName().substring(0,field.getName().length() - MultiLanguageEnum.EN_US.getFieldSuffix().length()));
            }
        }
        for (Field field : fields){
            if (fieldNames.contains(field.getName())){
                multilangField.add(field);
            }
        }
        cacheOriginFields.putIfAbsent(className,multilangField);
        return multilangField;
    }

    /**
     * 处理参数
     * @param objects
     */
    static void dispose(Object[] objects){
        String suffix = MultiLanguageUtil.getValue();
        if (MultiLanguageEnum.EN_US.getCode().equals(MultiLanguageUtil.getValue())){
            suffix = MultiLanguageEnum.EN_US.getFieldSuffix();
        }else if (MultiLanguageEnum.ZH_CN.getCode().equals(MultiLanguageUtil.getValue())){
            suffix = MultiLanguageEnum.ZH_CN.getFieldSuffix();
        }else {
            return;
        }
        if (suffix == null){
            return;
        }
        for (Object o : objects){
            if (o == null || o.getClass().isPrimitive()){
                continue;
            }
            Set<Field> englishFields = getEnglishField(o.getClass().getName());
            if (englishFields.size() > 0){
                for (Field field : englishFields){
                    setReqField(o, suffix , o.getClass(), field);
                }
            }
        }
    }


    private static void setReqField(Object object, String suffix, Class classz, Field f) {
        Method getter;
        Method setter;
        try {
            getter = classz.getMethod("get" + f.getName().substring(0, 1).toUpperCase()
                    + f.getName().substring(1));
            setter = classz.getMethod("set" + f.getName().substring(0, 1).toUpperCase()
                    + f.getName().substring(1) + suffix, f.getType());
            setter.invoke(object, getter.invoke(object));

            //清除原先字段值
            Method clearSetter = classz.getMethod("set" + f.getName().substring(0, 1).toUpperCase()
                    + f.getName().substring(1), f.getType());
            clearSetter.invoke(object, new Object[]{null});
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    /**
     *
     * 处理多层嵌套对象,語言類型從過濾器取值
     * @param object
     */
    public static void replaceNestMultiLanguage(Object object){
        replaceNestMultiLanguage(object,null);
    }

    /**
     *
     * 处理多层嵌套对象，可以自己設置語言類型
     * @param object
     */
    public static void replaceNestMultiLanguage(Object object,MultiLanguageEnum multiLanguageEnum){
        Object o = null;
        if ((o = checkReplace(object,multiLanguageEnum != null ? multiLanguageEnum.getCode() : null)) == null){
            return;
        }
        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> multilangField = getEnglishField(classObject.getClass().getName());
        Set<Field> complextFields = getComplextFields(classObject.getClass().getName());

        Iterator compextIterator = complextFields.iterator();
        Field field = null;
        while (compextIterator.hasNext()){
            field = (Field) compextIterator.next();
            field.setAccessible(true);
            Object obj = null;
            log.info("{},{}",field.getName(),classObject.getClass().getName());
            try {
                obj = field.get(classObject);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(),e);
            }
            if (obj != null){
                if (obj instanceof List){
                    List list = (List) obj;
                    for (Object ob : list){
                        replaceNestMultiLanguage(ob,multiLanguageEnum);
                    }
                }else {
                    replaceNestMultiLanguage(obj,multiLanguageEnum);
                }

            }
        }

        //校验是否存在多语言字段
        if (multilangField.size() == 0){
            return;
        }
        //多语言属性值处理
        if (o instanceof List){
            for (Object obj : (List)o){
                replaceObject(obj, multilangField,null,multiLanguageEnum != null ? multiLanguageEnum.getCode() :MultiLanguageUtil.getValue());
            }
        }else {
            replaceObject(o, multilangField,null,multiLanguageEnum != null ? multiLanguageEnum.getCode() :MultiLanguageUtil.getValue());
        }
    }


    private static Set<Field> getComplextFields(String className){
        Set<Field> set = cacheComplextFields.get(className);
        if (set != null){
            return set;
        }
        set = new HashSet<>();
        Set<Field> fields = ClassUtil.getField(className, true);
        Iterator<Field> iterator = fields.iterator();
        while (iterator.hasNext()){
            Field sourceField = iterator.next();
            if (!ClassUtil.isPrimitive(sourceField.getType())){
                set.add(sourceField);
            }
        }
        cacheComplextFields.putIfAbsent(className,set);
        return set;
    }
}
