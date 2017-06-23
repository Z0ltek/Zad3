package rest;


import com.sun.org.apache.regexp.internal.RE;
import domain.Actor;
import domain.Comment;
import domain.Film;
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

    // pobieranie wszystkich filow
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll(){
        return Fdb.getAll();
    }

    //dodawanie filmu
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Film film){
        Fdb.add(film);
        return Response.ok(film.getId()).build();
    }




    //wyswietlanie filmu o danym ID
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


    //edycja filmu
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Film film){
        Film result = Fdb.get(id);
        if(result == null) {
            return Response.status(404).build();
        }
        film.setId(id);
        Fdb.update(film);
        return Response.ok().build();
    }


    //usuwanie filmu o danym id
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        Film result = Fdb.get(id);
        if(result == null)
            return Response.status(404).build();
        Fdb.delete(result);
        return Response.ok().build();
    }


    //wyswietlanie komentarzy do danego filmu
    @GET
    @Path("/{movieId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments(@PathParam("movieId") int movieId){
        Film result = Fdb.get(movieId);
        if(result == null) {
            return null;
        }
        if(result.getComments() == null) {
            result.setComments(new ArrayList<Comment>());
        }
        return result.getComments();
    }

    //dodawanie komentarza do danego filmu
    @POST
    @Path("/{id}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComm(@PathParam("id") int movieId, Comment comments){
        Film result = Fdb.get(movieId);
        if(result == null) {
            return Response.status(404).build();
        }
        if(result.getComments() == null)
            result.setComments(new ArrayList<Comment>());
        Fdb.addComm(result, comments);
        return Response.ok().build();
    }


    //usuwanie komentarza pod danym filmem
    @DELETE
    @Path("/{id}/comments/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeComments(@PathParam("id") int movieId, @PathParam("commentId") int commentId){
        Film result = Fdb.get(movieId);
        if(result == null) {
            return Response.status(404).build();
        }
        for(Comment comment : result.getComments()){
            if(comment.getId() == commentId){
                result.getComments().remove(comment);
                return Response.ok().build();
            }
        }
        return Response.status(404).build();
    }


    //dodaje ocene do danego filmu
    @POST
    @Path("/{id}/rate/{rate}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rate(@PathParam("id") int movieId,@PathParam("rate") int rating){
        Film result = Fdb.get(movieId);
        if(result == null) {
            return Response.status(404).build();
        }
        Fdb.rate(result, (float)rating);
        return Response.ok().build();
    }


    //Dodaje aktora
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

    //Wyswietka aktora
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
