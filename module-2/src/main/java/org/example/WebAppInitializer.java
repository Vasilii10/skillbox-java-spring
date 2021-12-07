package org.example;

import org.apache.log4j.Logger;
import org.example.config.WebContextConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.*;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebAppInitializer implements WebApplicationInitializer {

	private final Logger log = Logger.getLogger(WebAppInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) {
		log.info("Start onStartup");

		log.info("Start loading app config");
		/* App config loading and listener definition */
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("classpath:app-config.xml");
		servletContext.addListener(new ContextLoaderListener(appContext));

		log.info("Start loading web config");
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(WebContextConfig.class);

		/* Define dispatcherServlet	*/
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		/* Register dispatcherServlet */
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1); // need to be loaded and started at first
		dispatcher.addMapping("/");

		log.info("Finish loading config");
	}
}
