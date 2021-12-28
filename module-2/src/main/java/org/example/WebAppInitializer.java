package org.example;

import org.apache.log4j.Logger;
import org.example.app.services.config.AppContextConfig;
import org.example.config.WebContextConfig;
import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebAppInitializer implements WebApplicationInitializer {

	private final Logger LOG = Logger.getLogger(WebAppInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) {
		LOG.info("Start onStartup");

		LOG.info("Start loading app config");
		/* App config loading and listener definition */
//		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
//		appContext.setConfigLocation("classpath:app-config.xml");

		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(AppContextConfig.class);
		servletContext.addListener(new ContextLoaderListener(appContext));

		LOG.info("Start loading web config");
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.register(WebContextConfig.class);

		/* Define dispatcherServlet	*/
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		/* Register dispatcherServlet */
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1); // need to be loaded and started at first
		dispatcher.addMapping("/");


		ServletRegistration.Dynamic servlet = servletContext.addServlet("h2-console", new WebServlet());
		servlet.setLoadOnStartup(2);
		servlet.addMapping("/console/*");

		LOG.info("Finish loading config");
	}
}
