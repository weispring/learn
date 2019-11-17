1. matcher.find() 一次还是多次，只与匹配内容和正则有关，和组无关。匹配一次，可能多组，匹配多次也可能多组。
2. descmatcher.find(); or descmatcher.matches();  必须执行一次，否则无法group();


匹配必有一个
(https?|ftp|file)
