package com.xys.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xys.atcrowdfunding.util.Const;

/*
 * 监听application对象创建和销毁
 * 
 */

public class SystemUpInitListener implements ServletContextListener {

	Logger log=LoggerFactory.getLogger(SystemUpInitListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		ServletContext application = sce.getServletContext();
		String contextPath=application.getContextPath();
		log.debug("当前应用上下文路径：{}",contextPath);
		application.setAttribute(Const.PATH, contextPath);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		log.debug("当前应用application对象销毁");
	}

}
