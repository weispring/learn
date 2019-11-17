1. matcher.find() 一次还是多次，只与匹配内容和正则有关，和组无关。匹配一次，可能多组，匹配多次也可能多组。
2. descmatcher.find(); or descmatcher.matches();  必须执行一次，否则无法group();
3. matches()和find()语义不同，matches全文匹配，find()任意位置匹配，且表达式包含多个分组时find只能匹配到第一个。
   因此具体使用哪一个，需要看的表达式是全文匹配还是局部匹配
4. 匹配必有一个(https?|ftp|file)
