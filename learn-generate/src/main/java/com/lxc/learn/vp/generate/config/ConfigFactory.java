package com.lxc.learn.vp.generate.config;

import com.lxc.learn.vp.generate.utils.ConfigFileUtils;

public class ConfigFactory {

	public static Config config = null;


	public static void init(String configFile) {
		config = ConfigFileUtils.getConfig(configFile);
	}



}
