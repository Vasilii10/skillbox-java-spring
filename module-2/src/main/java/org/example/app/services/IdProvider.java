package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.*;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

	private final Logger log = Logger.getLogger(IdProvider.class);

	public Integer provideId(Book book) {
		return this.hashCode() + book.hashCode();
	}

	/*Specific method for idProvider Bean*/
	private void destroyIdProvider() {
		log.info("destroyIdProvider");
	}

	/*Specific method for idProvider Bean*/
	private void initIdProvider() {
		log.info("initIdProvider");
	}

	/*Default method for all configuration*/
	private void defaultInit() {
		log.info("defaultInit " + getClass().getSimpleName());
	}

	/*Default method for all configuration*/
	private void defaultDestroy() {
		log.info("defaultDestroy " + getClass().getSimpleName());
	}

	/*Implements InitializingBean*/
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet");
	}

	/*Implements DisposableBean*/
	@Override
	public void destroy() throws Exception {
		log.info("destroy");
	}

	/*Override default methods from BeanPostProcessor*/
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("postProcessBeforeInitialization invoked by bean" + beanName);
		return null;
	}

	/*Override default methods from BeanPostProcessor*/
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("postProcessAfterInitialization invoked by bean" + beanName);
		return null;
	}

	@PostConstruct
	public void postConstructProvider() {
		log.info("postConstructProvider");
	}

	@PreDestroy
	public void preDestroyProvider() {
		log.info("preDestroyProvider");
	}

}
