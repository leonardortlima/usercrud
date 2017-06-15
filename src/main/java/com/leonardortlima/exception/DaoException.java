package com.leonardortlima.exception;

/**
 * @author leonardortlima
 * @since 2016-11-08
 */
public class DaoException extends Exception {

  public DaoException(String message) {
    super(message);
  }

  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }

  public DaoException(Throwable cause) {
    super(cause);
  }
}
