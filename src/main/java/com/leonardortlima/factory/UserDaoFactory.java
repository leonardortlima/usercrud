package com.leonardortlima.factory;

import com.leonardortlima.dao.Dao;
import com.leonardortlima.dao.ModelDao;
import com.leonardortlima.model.User;
import org.glassfish.hk2.api.Factory;

/**
 * @author leonardortlima
 * @since 2017-06-12
 */
public class UserDaoFactory implements Factory<Dao<User>> {

  @Override public Dao<User> provide() {
    return new ModelDao<>(User.class);
  }

  @Override public void dispose(Dao<User> tDao) {

  }
}
