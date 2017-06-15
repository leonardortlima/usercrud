package com.leonardortlima.rest.filter;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * @author leonardortlima
 * @since 2017-06-13
 */
public class AccessControlFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext)
      throws IOException {

    responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
    responseContext.getHeaders()
        .add("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS, HEAD");
    responseContext.getHeaders()
        .add("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization");
    responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
  }
}
