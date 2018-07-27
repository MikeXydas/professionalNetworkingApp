package service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;


import annotations.Secured;

import javax.ws.rs.core.UriBuilder;

import db.UserDB;
import model.UserBean;

@Path("/User")
public class UserEndpoint {
	
	@GET
	@Path("query")
	public Response getUser(@Context UriInfo info) {
		int id = Integer.parseInt(info.getQueryParameters().getFirst("id"));
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		return Response.status(200).entity("First name is: " + userd.getFirstName()).build();
	}
}
