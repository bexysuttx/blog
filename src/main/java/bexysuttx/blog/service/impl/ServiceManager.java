package bexysuttx.blog.service.impl;

import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bexysuttx.blog.service.BusinessService;
import bexysuttx.blog.util.AppUtil;

public class ServiceManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);
	private static final String SERVICE_MANAGER = "SERVICE_MANAGER";
	final Properties applicationProperties = new Properties();
	final BasicDataSource basicDataSource;
	final BusinessService businessService;

	private ServiceManager(ServletContext context) {
		AppUtil.loadProperties(applicationProperties, "application.properties");
		basicDataSource = createBasicDataSource();
		businessService = new BusinessServiceImpl(this);
		LOGGER.info("ServiceManager instance created");
	}

	public static ServiceManager getInstance(ServletContext context) {
		ServiceManager instance = (ServiceManager) context.getAttribute(SERVICE_MANAGER);
		if (instance == null) {
			instance = new ServiceManager(context);
			context.setAttribute(SERVICE_MANAGER, instance);
		}
		return instance;
	}

	public void destroy() {
		try {
			basicDataSource.close();
		} catch (SQLException e) {
			LOGGER.error("Close datasource failed: " + e.getMessage(),e);
		}
		LOGGER.info("ServiceManager instance destroyed");
	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	private BasicDataSource createBasicDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDefaultAutoCommit(false);
		ds.setRollbackOnReturn(true);
		ds.setDriverClassName(getApplicationProperties("db.driver"));
		ds.setUrl(getApplicationProperties("db.url"));
		ds.setUsername(getApplicationProperties("db.username"));
		ds.setPassword(getApplicationProperties("db.password"));
		ds.setInitialSize(Integer.parseInt(getApplicationProperties("db.pool.initSize")));
		ds.setMaxTotal(Integer.parseInt(getApplicationProperties("db.pool.maxSize")));
		return ds;
	}

	public String getApplicationProperties(String property) {
		return applicationProperties.getProperty(property);
	}

}
