package com.lxc.learn.junit.util.multilanguage;

import com.baomidou.mybatisplus.plugins.Page;
import com.lxc.learn.junit.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

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
    private static Object checkReplace(Object object){
        if (MultiLanguageEnum.ZH_HK.getCode().equals(MultiLanguageUtil.getValue())){
            return null;
        }
        if (object == null){
            return null;
        }
        Object o = null;
     /*   if (object instanceof BaseResponse){
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
        if ((o = checkReplace(object)) == null){
            return;
        }
        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> fields = ClassUtil.getField(classObject.getClass().getName(), true);
        Set<Field> multilangField = getEnglishField(fields);
        //校验是否存在多语言字段
        if (multilangField.size() == 0){
            //MultiLanguageUtil.remove();
            return;
        }
        //去掉 部分不需要支持多语言的字段
        fields = getMultiFields(multilangField, fields);

        //多语言属性值处理
        if (o instanceof List){
            for (Object obj : (List)o){
                replaceObject(obj, fields,null,MultiLanguageUtil.getValue());
            }
        }else {
            replaceObject(o, fields,null,MultiLanguageUtil.getValue());
        }
    }

    private static Object replaceMultiLanguage(Object object, Object newObject){
        Object o = null;
        if ((o = checkReplace(object)) == null){
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
        if ((o = checkReplace(object)) == null){
            return;
        }

        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> fields = ClassUtil.getField(classObject.getClass().getName(), true);
        Set<Field> multilangField = getEnglishField(fields);
        //校验是否存在多语言字段
        if (multilangField.size() == 0){
            return;
        }

        //去掉 部分不需要支持多语言的字段
        fields = getMultiFields(multilangField, fields);

        //多语言属性值处理
        if (o instanceof List){
            for (Object obj : (List)o){
                replaceObject(obj, fields,null,MultiLanguageUtil.getValue());
            }
        }else {
            replaceObject(o, fields,null,MultiLanguageUtil.getValue());
        }
    }



    private static Set<Field> getMultiFields(Set<Field> englishFields, Set<Field> fields){
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
    }


    private static Set<Field> getEnglishField(Set<Field> fields){
        Set<Field> multilangField = new HashSet<>();
        for (Field field : fields){
            if (field.getName().endsWith(MultiLanguageEnum.EN_US.getFieldSuffix())){
                multilangField.add(field);
            }
        }
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
            Set<Field> fields = ClassUtil.getField(o.getClass().getName(), true);
            Set<Field> englishFields = getEnglishField(fields);
            if (englishFields.size() > 0){
                Set<Field> set = getMultiFields(englishFields, fields);
                for (Field field : set){
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
     * 处理多层嵌套对象
     * @param object
     */
    public static void replaceNestMultiLanguage(Object object){
        Object o = null;
        if ((o = checkReplace(object)) == null){
            return;
        }
        Object classObject = o;
        if (o instanceof List){
            classObject = ((List) o).get(0);
        }
        Set<Field> fields = ClassUtil.getField(classObject.getClass().getName(), true);
        Set<Field> multilangField = getEnglishField(fields);
        //校验是否存在多语言字段
        if (multilangField.size() == 0){
            //MultiLanguageUtil.remove();
            return;
        }
        Set<Field> complextFields = getComplextFields(fields);
        Iterator compextIterator = complextFields.iterator();
        Field field = null;
        while (compextIterator.hasNext()){
            field = (Field) compextIterator.next();
            field.setAccessible(true);
            Object obj = null;
            try {
                obj = field.get(object);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(),e);
            }
            if (obj != null){
                if (obj instanceof List){
                    List list = (List) obj;
                    for (Object ob : list){
                        replaceNestMultiLanguage(ob);
                    }
                }else {
                    replaceNestMultiLanguage(obj);
                }

            }
        }


        //去掉 部分不需要支持多语言的字段
        fields = getMultiFields(multilangField, fields);
        //多语言属性值处理
        if (o instanceof List){
            for (Object obj : (List)o){
                replaceObject(obj, fields,null,MultiLanguageUtil.getValue());
            }
        }else {
            replaceObject(o, fields,null,MultiLanguageUtil.getValue());
        }
    }


    private static Set<Field> getComplextFields(Set<Field> fields){
        Set<Field> set = new HashSet<>();
        Iterator<Field> iterator = fields.iterator();
        while (iterator.hasNext()){
            Field sourceField = iterator.next();
            if (!ClassUtil.isPrimitive(sourceField.getType())){
                set.add(sourceField);
            }
        }
        return set;
    }

}
