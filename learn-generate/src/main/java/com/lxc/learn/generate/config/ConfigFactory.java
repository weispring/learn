package com.lxc.learn.generate.config;

import com.lxc.learn.generate.utils.ConfigFileUtils;

public class ConfigFactory {

	public static Config config = null;


	public static void init(String configFile) {
		config = ConfigFileUtils.getConfig(configFile);
	}



}
