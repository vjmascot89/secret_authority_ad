package com.trending.game.dao;

import javax.annotation.PostConstruct;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySpringComponent {

	@Autowired
	private HibernateEntityManagerFactory hibernateEntityManagerFactory;
    
	@PostConstruct
	public void registerEnversListeners() {
		EventListenerRegistry listenerRegistry = ((SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory()).getServiceRegistry().getService(EventListenerRegistry.class);
		listenerRegistry.setListeners(EventType.POST_INSERT, new ListenerComponent());
	}
	
}