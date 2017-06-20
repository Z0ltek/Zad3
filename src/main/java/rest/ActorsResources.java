package rest;

import domain.Actor;
import domain.Film;
import domain.services.ActorService;
import domain.services.FilmService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zolto on 18.06.2017.
 */
@Path("/actors")
public class ActorsResources {

    private ActorService Adb = new ActorService();
    private FilmService Fdb = new FilmService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actor> getAll(){
        return Adb.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Actor actor){
        Adb.add(actor);
        return Response.ok(actor.getId()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id){
        Actor result = Adb.get(id);
        if(result == null){
            return Response.status(404).build();
        }

        return Response.ok(result).build();
    }


    @GET
    @Path("/{actorId}/movies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getFilms(@PathParam("actorId") int ActorId){
        Actor result = Adb.get(ActorId);
        if(result == null)
            return null;
        if(result.getFilms() == null)
            result.setFilms(new ArrayList<Film>());
        return result.getFilms();
    }



    @POST
    @Path("/{id}/movies")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComments(@PathParam("id") int ActorId, Film film){
        Actor result = Adb.get(ActorId);
        boolean choose = false;
        if(result == null) {
            return Response.status(404).build();
        }
        if(result.getFilms() == null) {
            result.setFilms(new ArrayList<Film>());
        }
        result.getFilms().add(film);
        Adb.add(result);
        for(Film fi : Fdb.getAll())
        {
            if(film.getTitle().equalsIgnoreCase(fi.getTitle()))
            {
                choose = true;
                break;
            }

        }
        Actor actor = new Actor();
        actor.setId(result.getId());
        actor.setName(result.getName());
        actor.setSurname(result.getSurname());

        if(!choose){
            film.setActors(new ArrayList<Actor>());
            film.getActors().add(actor);
            Fdb.add(film);
        }
        else
        {
            for(Film m2 : Fdb.getAll()){
                if(film.getTitle().equalsIgnoreCase(m2.getTitle())){
                    m2.getActors().add(actor);
                }
            }
        }
        return Response.ok().build();
    }

}
