package com.trending.game.dao;

import org.hibernate.event.spi.PostCommitInsertEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

import com.trending.game.model.SattaPlayer;

public class ListenerComponent implements PostCommitInsertEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void onPostInsert(PostInsertEvent event) {

	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPostInsertCommitFailed(PostInsertEvent event) {
		// TODO Auto-generated method stub

	}

	// /**
	// *
	// */
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// public boolean requiresPostCommitHanding(EntityPersister persister) {
	// // TODO Auto-generated method stub
	// System.out.println(persister.getPropertyValue("sattaPlayerName", 0));
	// return false;
	// }
	//
	// @Override
	// public void onPostInsert(PostInsertEvent event) {
	// System.out.println(event.getPersister().getPropertyNames()[0]);
	// System.out.println(event.getPersister().getPropertyNames()[1]);
	//
	// System.out.println(event.getPersister().getPropertyNames()[2]);
	// System.out.println(event.getPersister().getPropertyValue(,
	// event.getPersister().getPropertyNames()[2]));
	// }

}