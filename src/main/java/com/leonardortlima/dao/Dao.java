package com.leonardortlima.dao;

import com.leonardortlima.exception.DaoException;
import java.util.List;
import java.util.Optional;

/**
 * @author leonardortlima
 * @since 2017-06-13
 */
public interface Dao<T> {

  Long insert(T model) throws DaoException;

  void update(T model) throws DaoException;

  void delete(T model) throws DaoException;

  List<T> list() throws DaoException;

  Optional<T> findById(Long id) throws DaoException;
}
