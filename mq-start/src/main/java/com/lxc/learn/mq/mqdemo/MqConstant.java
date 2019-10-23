package com.lxc.learn.mq.mqdemo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqConstant {

	public static boolean SHOW_SINGLE_LOG = true;
	public static boolean SHOW_MULTI_LOG = true;

	public static void log(String content) {
		boolean useLog = false;
		if (useLog) {
			log.info(content);
		} else {
			System.err.println(content);
		}
	}
}
