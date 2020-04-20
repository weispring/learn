package com.lxc.learn.jdk.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: lixianchun
 * @Date: 2019/11/16 11:18
 * @Description:
 */
public class RegexTest1 {


    /**
     * 重要方法
     * 分组
     * 忽略大小写
     *
     * @param args
     */
    public static void main(String[] args) {
        // 模式  忽略大小写
        Pattern pattern = Pattern.compile("Java", Pattern.CASE_INSENSITIVE);

    }

    /**
     * split(CharSequence input， int limit)，当limit值大于所能返回的字符串的最多个数或者为负数，
     * 返回的字符串个数将不受限制，但结尾可能包含空串，而当limit=0时与split(CharSequence input)等价，
     * 但结尾的空串会被丢弃。
     */
    @Test
    public void testSplit() {
        Pattern pattern = Pattern.compile("Java");
        String test = "123Java456Java789Java";
        String[] result = pattern.split(test, 9);
        for (String s : result)
            System.out.println(s + "-");
    }

    @Test
    public void testSplit1() {
        Pattern pattern = Pattern.compile("[\\w|\\s]{1,10}");
        System.out.println("");
        String test = "1111 1 ";
        System.out.println(pattern.matcher(test).matches());
    }




    /**
     * Pattern类也自带一个静态匹配方法matches(String regex, CharSequence input)，
     * 但只能进行全字符串匹配并且只能返回是否匹配上的boolean值
     */
    @Test
    public void testMatcher() {
        String test1 = "Java";
        String test2 = "Java123456";

        System.out.println(Pattern.matches("Java", test1));//返回true
        System.out.println(Pattern.matches("Java", test2));//返回false
    }


    /**
     * Matcher类提供了三个返回boolean值得匹配方法：
     * matches()，lookingAt()，find()，find(int start)，其中matches()用于全字符串匹配，
     * lookingAt从字符串最开头开始匹配满足的子串，
     * find可以对任意位置字符串匹配,其中start为起始查找索引值。
     */
    @Test
    public void testMatcherClass() {
        Pattern pattern = Pattern.compile("Java");
        String test1 = "Java";
        String test2 = "Java1234";
        String test3 = "1234Java";
        Matcher matcher = pattern.matcher(test1);
        System.out.println(matcher.matches());//返回true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.matches());//返回false

        matcher = pattern.matcher(test2);
        System.out.println(matcher.lookingAt());//返回true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.lookingAt());//返回false

        matcher = pattern.matcher(test1);
        System.out.println(matcher.find());//返回true
        matcher = pattern.matcher(test2);
        System.out.println(matcher.find());//返回true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(2));//返回true
        matcher = pattern.matcher(test3);
        System.out.println(matcher.find(5));//返回false
    }


    /**
     * 组的概念：组是用括号划分的正则表达式，可以根据组的编号来引用这个组。
     * 组号为0表示整个表达式，组号为1表示被第一对括号括起的组，依次类推，
     * 例如A(B(C))D，组0是ABCD，组1是BC，组2是C。
     * <p>
     * Matcher类提供了start()，end()，group()分别用于返回字符串的起始索引，结束索引，
     * 以及匹配到到的字符串。
     */
    @Test
    public void testGroup() {
        Pattern pattern = Pattern.compile("Java");
        String test = "123Java456";

        Matcher matcher = pattern.matcher(test);
        matcher.find();
        System.out.println(matcher.start());//返回3
        System.out.println(matcher.end());//返回7
        System.out.println(matcher.group());//返回Java
    }

    /**
     * Matcher类提供了start(int gropu)，end(int group)，group(int i)，groupCount()用于分组操作
     */
    @Test
    public void test001() {
        Pattern pattern = Pattern.compile("(Java)(Python)");
        String test = "123JavaPython456";
        Matcher matcher = pattern.matcher(test);
        matcher.find();
        System.out.println(matcher.groupCount());//返回2

        System.out.println(matcher.group(1));//返回第一组匹配到的字符串"Java"，注意起始索引是1
        System.out.println(matcher.start(1));//返回3，第一组起始索引
        System.out.println(matcher.end(1));//返回7 第一组结束索引

        System.out.println(matcher.group(2));//返回第二组匹配到的字符串"Python"
        System.out.println(matcher.start(2));//返回7，第二组起始索引
        System.out.println(matcher.end(2));//返回13 第二组结束索引
    }


    /**
     * Matcher类还提供region(int start, int end)(不包括end)方法用于设定查找范围，
     * 并提供regionStrat()和regionEnd()用于返回起始和结束查找的索引
     */
    @Test
    public void test002() {
        Pattern pattern = Pattern.compile("Java");
        String test = "123JavaJava";
        Matcher matcher = pattern.matcher(test);
        matcher.region(7, 11);
        System.out.println(matcher.regionStart());//返回7
        System.out.println(matcher.regionEnd());//返回11
        matcher.find();
        System.out.println(matcher.group());//返回Java
    }

    /**
     *  Matcher类提供了两种用于重置当前匹配器的方法:reset()和reset(CharSequence input)
     */
    @Test
    public void test003(){
        Pattern pattern = Pattern.compile("Java");
        String test = "Java";
        Matcher matcher = pattern.matcher(test);

        matcher.find();
        System.out.println(matcher.group());//返回Java

        matcher.reset();//从起始位置重新匹配

        matcher.find();
        System.out.println(matcher.group());//返回Java

        matcher.reset("Python");
        System.out.println(matcher.find());//返回false
    }

    @Test
    public void test004(){
        Pattern pattern = Pattern.compile("我是(.*?),来自(.*?)。");
        String test = "我是李显春,来自中国。";
        Matcher m = pattern.matcher(test);

        StringBuffer sb = new StringBuffer();

        if (m.matches()){
            int times = m.groupCount();
            for (int i=1;i<=times;i++){
                System.out.println("find:"+m.group(i));
                //m.appendReplacement(sb,"00");
            }

        }
        StringBuffer tail = new StringBuffer();

        m.appendTail(tail);
        System.out.print(":sb"+sb);
        System.out.print(":tail:"+tail);
    }


    @Test
    public void getFirstChar() {
        Pattern pattern = Pattern.compile("[A-Z|a-z]?");
        String test = "ABB将案件多发";
        Matcher result = pattern.matcher(test);
        result.find();
        System.out.println(result.group());
    }

}