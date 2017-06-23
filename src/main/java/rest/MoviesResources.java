package rest;


import domain.Actor;
import domain.Comment;
import domain.Film;
import domain.Rating;
import domain.services.ActorService;
import domain.services.FilmService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zolto on 12.05.2017.
 */
@Path("/movie")
public class MoviesResources {

    private int commId = 0;
    private ActorService Adb = new ActorService();
    private FilmService Fdb = new FilmService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll(){
        return Fdb.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Film film){
        Fdb.add(film);
        return Response.ok(film.getId()).build();
    }





    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        Film result = Fdb.get(id);
        if(result == null){
            return Response.status(404).build();
        }

        return Response.ok(result).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Film film){
        Film result = Fdb.get(id);
        if(result == null)
            return Response.status(404).build();
        film.setId(id);
        for(Actor a: Adb.getAll()){
            for(Film x : a.getFilms()){
                if(x.getTitle().equalsIgnoreCase(result.getTitle())){
                    x.setTitle(film.getTitle());
                }
            }
        }
        Fdb.update(film);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        Film result = Fdb.get(id);
        if(result == null)
            return Response.status(404).build();
        Fdb.delete(result);
        return Response.ok().build();
    }

    @GET
    @Path("/{movieId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments(@PathParam("movieId") int movieId){
        Film result = Fdb.get(movieId);
        if(result == null)
            return null;
        if(result.getComments() == null)
            result.setComments(new ArrayList<Comment>());
        return result.getComments();
    }

    @POST
    @Path("/{id}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComments(@PathParam("id") int movieId, Comment comments){
        Film result = Fdb.get(movieId);
        if(result == null)
            return Response.status(404).build();
        if(result.getComments() == null)
            result.setComments(new ArrayList<Comment>());
        for(Comment com : result.getComments())
        {
            commId++;
        }
        comments.setId(++commId);
        result.getComments().add(comments);
        return Response.ok().build();
    }
    @DELETE
    @Path("/{id}/comments/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeComments(@PathParam("id") int movieId, @PathParam("commentId") int commentId){
        Film result = Fdb.get(movieId);
        commentId--;
        boolean choose = false;
        if(result == null)
            return Response.status(404).build();

        result.getComments().remove(commentId);

        return Response.ok().build();
    }

    //---

    @GET
    @Path("/{movieId}/grades")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rating> getGrade(@PathParam("movieId") int movieId){
        Film result = Fdb.get(movieId);
        double x= 0;
        int i = 0;
        if(result == null)
            return null;
        if(result.getRatings() == null)
            result.setRatings(new ArrayList<Rating>());
        for(Rating rating : result.getRatings())
        {
            x += rating.getRating();
            i++;
        }
        x = x/i;
        Rating r = new Rating();
        r.setRating(x);
        result.setRatings(new ArrayList<Rating>());
        result.getRatings().add(r);
        return result.getRatings();
    }

    @POST
    @Path("/{id}/grades")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGrade(@PathParam("id") int movieId, Rating rating){
        Film result = Fdb.get(movieId);
        if(result == null)
            return Response.status(404).build();
        if(result.getRatings() == null)
            result.setRatings(new ArrayList<Rating>());
        result.getRatings().add(rating);
        return Response.ok().build();
    }

    //Actors =================================

    @POST
    @Path("/{id}/actors")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addActor(@PathParam("id") int movieId, Actor actor){
        Film result = Fdb.get(movieId);
        boolean choose = false;
        if(result == null)
            return Response.status(404).build();
        if(result.getActors() == null)
            result.setActors(new ArrayList<Actor>());
        result.getActors().add(actor);
        for(Actor a : Adb.getAll()){
            if(actor.getName().equalsIgnoreCase(a.getName()) && actor.getSurname().equalsIgnoreCase(a.getSurname())){
                choose = true;
                break;
            }
        }
        Film movie = new Film();
        movie.setId(result.getId());
        movie.setTitle(result.getTitle());
        if(!choose){
            actor.setFilms(new ArrayList<Film>());
            actor.getFilms().add(movie);
            Adb.add(actor);
        }
        else {
            for(Actor a2 : Adb.getAll()){
                if(actor.getName().equalsIgnoreCase(a2.getName()) && actor.getSurname().equalsIgnoreCase(a2.getSurname())){
                    a2.getFilms().add(movie);
                }
            }
        }
        return Response.ok().build();
    }


    @GET
    @Path("/{id}/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actor> getActors(@PathParam("id") int id){
        Film result = Fdb.get(id);
        if(result == null)
            return null;
        if(result.getActors() == null)
            result.setActors(new ArrayList<Actor>());
        return result.getActors();
    }

}
