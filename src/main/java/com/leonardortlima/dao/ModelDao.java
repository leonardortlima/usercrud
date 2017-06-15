package com.leonardortlima.dao;

import com.leonardortlima.exception.DaoException;
import com.leonardortlima.model.Model;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ModelDao<T extends Model> implements Dao<T> {

  private Class<T> persistentClass;

  public ModelDao(Class<T> persistentClass) {
    this.persistentClass = persistentClass;
  }

  public Long insert(T model) throws DaoException {
    model.setCreatedAt(OffsetDateTime.now());
    model.setUpdatedAt(OffsetDateTime.now());

    Session session = DatabaseUtil.INSTANCE.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      Long id = (Long) session.save(model);
      transaction.commit();
      return id;
    } catch (Exception e) {
      transaction.rollback();
      throw new DaoException(e.getMessage());
    }
  }

  public void update(T model) throws DaoException {
    model.setUpdatedAt(OffsetDateTime.now());

    Session session = DatabaseUtil.INSTANCE.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      session.merge(model);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      throw new DaoException(e.getMessage());
    }
  }

  public void delete(T model) throws DaoException {
    Session session = DatabaseUtil.INSTANCE.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      session.delete(model);
      transaction.commit();
    } catch (Exception e) {
      transaction.rollback();
      throw new DaoException(e.getMessage());
    }
  }

  public List<T> list() throws DaoException {
    Session session = DatabaseUtil.INSTANCE.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      CriteriaBuilder builder = session.getCriteriaBuilder();

      CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
      Root<T> root = criteria.from(persistentClass);
      criteria.select(root);

      List<T> result = session.createQuery(criteria).getResultList();
      transaction.commit();
      return result;
    } catch (Exception e) {
      transaction.rollback();
      throw new DaoException(e);
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  public Optional<T> findById(Long id) throws DaoException {

    Session session = DatabaseUtil.INSTANCE.openSession();
    Transaction transaction = session.beginTransaction();
    try {
      CriteriaBuilder builder = session.getCriteriaBuilder();

      CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
      Root<T> root = criteria.from(persistentClass);

      criteria.select(root);
      criteria.where(builder.equal(root.get("id"), id));

      Optional<T> model = session.createQuery(criteria).uniqueResultOptional();
      transaction.commit();
      return model;
    } catch (Exception e) {
      transaction.rollback();
      throw new DaoException(e);
    } finally {
      session.close();
    }
  }
}
