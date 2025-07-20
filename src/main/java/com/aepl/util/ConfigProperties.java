package com.aepl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigProperties {

	private static final Logger logger = LogManager.getLogger(ConfigProperties.class);

	private static Properties properties;
	private static String environment; 
	private static final String CONFIG_FILE_FORMAT = "src/main/resources/%s.config.properties";

	private ConfigProperties() {
	}

	public static synchronized void initialize(String env) {
		if (env == null || env.isEmpty()) {
			throw new IllegalArgumentException("Environment must not be null or empty.");
		}
		environment = env.toLowerCase();
		String propertiesFile = String.format(CONFIG_FILE_FORMAT, environment);
		properties = new Properties();

		try (FileInputStream fis = new FileInputStream(propertiesFile)) {
			logger.info("Loading properties file for environment: {}", environment);
			properties.load(fis);
			logger.info("Properties file [{}] loaded successfully.", propertiesFile);
		} catch (IOException e) {
			logger.error("Failed to load properties file: {}", propertiesFile, e);
			throw new RuntimeException("Failed to load properties file: " + propertiesFile, e);
		}
	}

	public static String getProperty(String key) {
		if (properties == null) {
			throw new IllegalStateException("ConfigProperties is not initialized. Call initialize(env) first.");
		}

		String value = properties.getProperty(key);

		if (value != null) {
			logger.debug("Property [{}] = [{}]", key, value);
		} else {
			logger.warn("Property [{}] not found in the properties file.", key);
		}
		return value;
	}

	public static synchronized void reloadProperties() {
		if (environment == null) {
			throw new IllegalStateException("ConfigProperties is not initialized. Call initialize(env) first.");
		}

		logger.info("Reloading properties file for environment: {}", environment);
		initialize(environment);
		logger.info("Properties file reloaded successfully.");
	}

	public static String getEnvironment() {
		return environment;
	}
}
