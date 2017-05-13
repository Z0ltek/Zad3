package rest;

import domain.Actor;
import domain.Comment;
import domain.Film;
import domain.services.FilmOfActor;
import domain.services.FilmService;


import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zolto on 12.05.2017.
 */
@Path("/films")
public class MoviesResources {


    private FilmService db = new FilmService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll(){

        return db.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Film film){

        db.add(film);
        return Response.ok(film.getId()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get (@PathParam("id") int id){

        Film result = db.get(id);
        if(result==null){
            return Response.status(404).build();
        }
        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Film film){
        Film result = db.get(id);
        if(result==null){
            return Response.status(404).build();
        }
        film.setId(id);
        db.update(film);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments(@PathParam("id") int id){
        Film result = db.get(id);
        if(result==null){
            return null;
        }
        if(result.getComments()==null){
            result.setComments(new ArrayList<Comment>());
        }
        return result.getComments();

    }

    @POST
    @Path("/{id}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@PathParam("id") int id, Comment comment){
        Film result = db.get(id);
        if(result==null){
            return Response.status(404).build();
        }
        if(result.getComments()==null){
            result.setComments(new ArrayList <Comment>());
        }
        db.add(result, comment);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{filmId}/comments/{commentId}")
    public Response delete(@PathParam("filmId") int filmId, @PathParam("commentId") int commentId){

        Film result = db.get(filmId);
        if(result==null){
            return Response.status(404).build();
        }
        for(Comment c: result.getComments()){
            if(c.getId()==commentId)
                result.getComments().remove(c);
            return Response.ok().build();
        }
        return Response.status(404).build();
    }

    @POST
    @Path("/{id}/rate/{rating}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rate(@PathParam("id") int id, @PathParam("rating") int rating){
        Film result = db.get(id);
        if(result==null){
            return Response.status(404).build();
        }
        db.rate(result, (float)rating);
        return Response.ok().build();
    }


    @GET
    @Path("/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActors(){

        List<Actor> list = db.getAllActors();
        GenericEntity<List<Actor>> entity = new GenericEntity<List<Actor>>(list) {};
        return Response.ok(entity).build();

    }

    @GET
    @Path("/{filmId}/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorsOfFilm (@PathParam("filmId") int filmId){

        List <Actor> result = db.getActorsOfFilm(filmId);
        if(result==null){
            return Response.status(404).build();
        }
        GenericEntity<List<Actor>> entity = new GenericEntity<List<Actor>>(result) {};
        return Response.ok(entity).build();

    }


    @GET
    @Path("/actors/{actorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmsOfActor (@PathParam("actorId") int actorId){

        List <FilmOfActor> result = db.getFilmsOfActor(actorId);
        if(result==null){
            return Response.status(404).build();
        }
        GenericEntity<List<FilmOfActor>> entity = new GenericEntity<List<FilmOfActor>>(result) {};
        return Response.ok(entity).build();
    }

    @POST
    @Path("/actors")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Actor actor){

        db.add(actor);
        return Response.ok(actor.getId()).build();
    }

    @POST
    @Path("/{filmId}/actors/{actorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assign(@PathParam("actorId") int actorId, @PathParam("filmId") int filmId){


        Actor actor = db.getActor(actorId);
        Film film = db.get(filmId);

        if(actor==null || film==null){
            return Response.status(404).build();
        }

        if(actor.getFilms()==null){
            actor.setFilms(new ArrayList<FilmOfActor>());
        }

        if(film.getCast()==null){
            film.setCast(new ArrayList<Actor>());
        }

        db.assign(actor, film);;
        return Response.ok().build();
    }

}
