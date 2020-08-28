package com.lxc.learn.jdk.regex;

import org.junit.Test;

import java.util.Arrays;
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

    /** limit 最多能返回的数组个数
     * split(CharSequence input， int limit)，当limit值大于所能返回的字符串的最多个数或者为负数，
     * 返回的字符串个数将不受限制，但结尾可能包含空串，而当limit=0时与split(CharSequence input)等价，
     * 但结尾的空串会被丢弃。
     */
    @Test
    public void testSplit() {
        Pattern pattern = Pattern.compile("Java");
        String test = "12344Java456Java789Java";
        String[] result = pattern.split(test, 2);
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
        Pattern pattern = Pattern.compile("我是(.*)来自(.*)");
        String test = "我是李显春来自中国";
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


    /**
     * 特殊字段点.
     * . 可以用来匹配任何的单个字符，但是在绝大多数实现里面，不能匹配换行符；
     */
    @Test
    public void testDot(){
        Pattern pattern = Pattern.compile("\\.");
        String test = "11.";
        Matcher matcher = pattern.matcher(test);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
    }


    /**
     *  [ ] 定义一个字符集合；
     0-9、a-z 定义了一个字符区间，区间使用 ASCII 码来确定，字符区间在 [ ] 中使用。
     - 只有在 [ ] 之间才是元字符，在 [ ] 之外就是一个普通字符；
     ^ 在 [ ] 中是取非操作。

     匹配以 abc 为开头，并且最后一个字母不为数字的字符串：
     */

    @Test
    public void testSet(){
        RegexBase.test("abc[^0-9]", Arrays.asList("abc1","abc-"));
    }

    /**
     * 四、使用元字符
     匹配空白字符
     元字符	说明
     [\b]	回退（删除）一个字符
     \f	换页符
     \n	换行符
     \r	回车符
     \t	制表符
     \v	垂直制表符
     \r\n 是 Windows 中的文本行结束标签，在 Unix/Linux 则是 \n。

     \r\n\r\n 可以匹配 Windows 下的空白行，因为它匹配两个连续的行尾标签，而这正是两条记录之间的空白行；

     匹配特定的字符
     1. 数字元字符
     元字符	说明
     \d	数字字符，等价于 [0-9]
     \D	非数字字符，等价于 [^0-9]
     2. 字母数字元字符
     元字符	说明
     \w	大小写字母，下划线和数字，等价于 [a-zA-Z0-9_]
     \W	对 \w 取非
     3. 空白字符元字符
     元字符	说明
     \s	任何一个空白字符，等价于 [\f\n\r\t\v]
     \S	对 \s 取非
     \x 匹配十六进制字符，\0 匹配八进制，例如 \xA 对应值为 10 的 ASCII 字符 ，即 \n。

     五、重复匹配
     + 匹配 1 个或者多个字符
     ** * 匹配 0 个或者多个字符
     ? 匹配 0 个或者 1 个字符
     应用

     匹配邮箱地址。

     正则表达式

     [\w.]+@\w+\.\w+
     [\w.] 匹配的是字母数字或者 . ，在其后面加上 + ，表示匹配多次。在字符集合 [ ] 里，. 不是元字符；

     匹配结果

     abc.def@qq.com

     {n} 匹配 n 个字符
     {m,n} 匹配 m~n 个字符
     {m,} 至少匹配 m 个字符
     * 和 + 都是贪婪型元字符，会匹配尽可能多的内容。
     * 在量词后面后面加 ? 可以转换为懒惰型元字符，例如 *?、+? 和 {m,n}? 。

     *
     * 贪婪模式与 非贪婪模式
     */
    @Test
    public void testgreedy (){
        RegexBase.test("a.+c",Arrays.asList("abcabcabc"));
        RegexBase.test("a.+?c",Arrays.asList("abcabcabc"));

        RegexBase.test("\".*\"",Arrays.asList("a \"witch\" and her \"broom\" is one"));

        RegexBase.test("\".*?\"",Arrays.asList("a \"witch\" and her \"broom\" is one"));
    }


    /**
     * 匹配位置
     * \b 匹配这样的位置：它的前一个字符和后一个字符不全是(一个是,一个不是或不存在) \w。
     */
    @Test
    public void testPlace (){
        RegexBase.test("\\bi.*you",Arrays.asList("abcabcabc i love you,me too"));
    }

    /**
     * 使用 ( ) 定义一个子表达式。子表达式的内容可以当成一个独立元素，即可以将它看成一个字符，并且使用 * 等元字符。

     子表达式可以嵌套，但是嵌套层次过深会变得很难理解。

     */

    @Test
    public void testChildExpress(){
        RegexBase.test("((25[0-5]|(2[0-4]\\d)|(1\\d{2})|([1-9]\\d)|(\\d))\\.){3}(25[0-5]|(2[0-4]\\d)|(1\\d{2})|([1-9]\\d)|(\\d))",
                Arrays.asList("192.168.0.1","00.00.00.00","555.555.555.555"));

    }

    /**
     * 回溯引用
     回溯引用使用 \n 来引用某个子表达式，其中 n 代表的是子表达式的序号，从 1 开始。它和子表达式匹配的内容一致，比如子表达式匹配到 abc，那么回溯引用部分也需要匹配 abc 。

     大小写转换
     元字符	说明

     */
    @Test
    public void testHtmlH(){
        /**
         *\1 将回溯引用子表达式 (h[1-6]) 匹配的内容，也就是说必须和子表达式匹配的内容一致。

         */
        RegexBase.test("<(h[1-6])>\\w*?<\\/\\1>",Arrays.asList("<h1>x</h1>"," <h2>x</h2>","<h3>x</h1>"));
    }

    @Test
    public void testHtml2(){
        /**
         *d{3})(-)(\d{3})(-)(\d{4})
         替换正则表达式
         在第一个子表达式查找的结果加上 () ，然后加一个空格，在第三个和第五个字表达式查找的结果中间加上 - 进行分隔。
         ($1) $3-$5
         */

        String s = "313-555-1234".replaceFirst("(\\d{3})(-)(\\d{3})(-)(\\d{4})","($1) $3-$5");
        System.out.println(s);
    }


    @Test
    public void testHtml3(){
        /**
         * 将中间的字符替换为大写
         * todo error
         */
        String s = ("dear, i love you!".replaceFirst("(l.*e)","\\L$1\\E"));
        System.out.println(s);
        RegexBase.test("(l.*e)",Arrays.asList("dear, i love you!"));

        s = "abcd".replace("(\\w)(\\w{2})(\\w)","$1\\U$2\\E$3");
        System.out.println(s);
    }

    /**
     * 前后查找
     前后查找规定了匹配的内容首尾应该匹配的内容，但是又不包含首尾匹配的内容。

     向前查找使用 ?= 定义，它规定了尾部匹配的内容，这个匹配的内容在 ?= 之后定义。
     所谓向前查找，就是规定了一个匹配的内容，然后以这个内容为尾部向前面查找需要匹配的内容。
     */
    @Test
    public void testForward(){
        //查找出邮件地址 @ 字符前面的部分。
        RegexBase.test("[.|\\w]+(?=@)",Arrays.asList("li.xianchun@vpclub.cn"));

    }

    /**
     * 嵌入条件
     * 条件为某个子表达式是否匹配，如果匹配则需要继续匹配条件表达式后面的内容。
     */
  /*  @Test
    public void testCondition1(){
        //查找出邮件地址 @ 字符前面的部分。
        System.out.println("(\\()?abc(?(1)\\))".charAt(10));
        RegexBase.test("(\\()?abc(?(1)\\))",Arrays.asList("(abc)","abc","(abc"));

    }

    @Test
    public void testCondition2(){
        //查找出邮件地址 @ 字符前面的部分。
        RegexBase.test("\\d{5}(?(?=-)-\\d{4})",Arrays.asList("11111","22222-","33333-4444"));
    }*/
}