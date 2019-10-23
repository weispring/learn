package com.lxc.learn.mq.mqdemo;

public class DemoConstant {

	public final class Order {
		/**
		 *  微服务 feign的名称
		 */
		public static final String SERVICE_FAIGN_NAME = "${vphonor.serviceId.order:vp-central-demo-service-order}";

		/**
		 * 服务的简称
		 */
		public static final String SERVICE_SHORT_NAME = "order";

	}

	public final class User {
		public static final String SERVICE_FAIGN_NAME = "${vphonor.serviceId.user:vp-central-demo-service-user}";
		public static final String SERVICE_SHORT_NAME = "user";
	}
}
