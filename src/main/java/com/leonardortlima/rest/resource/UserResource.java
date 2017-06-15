package com.leonardortlima.rest.resource;

import com.leonardortlima.dao.Dao;
import com.leonardortlima.exception.DaoException;
import com.leonardortlima.model.User;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {

  @Inject Dao<User> userDao;

  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response list() {
    try {
      return Response.ok(userDao.list()).build();
    } catch (DaoException e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response get(@PathParam("id") Long id) {
    User user = findUser(id);
    return Response.ok(user).build();
  }

  @POST
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response insert(User user) {
    try {
      Long id = userDao.insert(user);
      user.setId(id);
      return Response.ok(user).build();
    } catch (DaoException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @PUT
  @Path("{id}")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response update(@PathParam("id") Long id, User remoteUser) {
    User localUser = findUser(id);

    try {
      localUser.mergeWith(remoteUser);
      userDao.update(localUser);
      return Response.ok(localUser).build();
    } catch (DaoException e) {
      return Response.serverError().build();
    }
  }

  @DELETE
  @Path("{id}")
  public Response delete(@PathParam("id") Long id) {
    User user = findUser(id);

    try {
      userDao.delete(user);
      return Response.noContent().build();
    } catch (DaoException e) {
      return Response.serverError().build();
    }
  }

  private User findUser(Long id) {
    try {
      Optional<User> userOpt = userDao.findById(id);
      if (!userOpt.isPresent()) {
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
      } else {
        return userOpt.get();
      }
    } catch (DaoException e) {
      throw new WebApplicationException(Response.serverError().build());
    }
  }
}
