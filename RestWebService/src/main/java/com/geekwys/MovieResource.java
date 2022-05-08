package com.geekwys;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shem.mwangi
 * @date 7th May 2022
 */
@Path("/movies")
public class MovieResource {
    public static List<Movie> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer movieCount() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMovie(Movie newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT
    @Path("{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(
            @PathParam("id") Long movieId,
            @PathParam("title") String movieTitle) {
        movies = movies.stream().map(movie -> {
            if (movie.getId().equals(movieId)) {
                movie.setTitle(movieTitle);
            }
            return movie;
        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteMovie(
            @PathParam("id") Long movieId) {
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(movieId))
                .findFirst();
        boolean removed = false;
        if (movieToDelete.isPresent()) {
            removed = movies.remove(movieToDelete.get());
        }
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
