package domain.services;

import domain.Actor;
import domain.Film;
import domain.Comment;

import java.util.*;

/**
 * Created by zolto on 11.05.2017.
 */

public class FilmService {

    private static List<Film> db = new ArrayList<Film>();
    private static List<Actor> dba = new ArrayList<Actor>();
    private static Map<Film, Float> map = new HashMap<Film, Float>();
    private static int currentFilmID = 0;
    private static int currentActorID = 0;

    public List<Film> getAll(){
        return db;
    }

    public List<Actor> getAllActors(){
        return dba;
    }

    public Film get(int id){

        for(Film f: db){
            if (f.getId()==id){
                return f;
            }
        }
        return null;

    }

    public Actor getActor(int id){

        for(Actor a: dba){
            if (a.getId()==id){
                return a;
            }
        }
        return null;

    }

    public void add(Film film){

        film.setId(++currentFilmID);
        film.setRating(0);
        db.add(film);

    }

    public void update (Film film){

        for (Film f: db){
            if (f.getId()==film.getId()){
                if(film.getTitle()!=null)
                    f.setTitle(film.getTitle());
                if(film.getDirector()!=null)
                    f.setDirector(film.getDirector());
                if(film.getScreenplay()!=null)
                    f.setScreenplay(film.getScreenplay());
                if(film.getGenre()!=null)
                    f.setGenre(film.getGenre());
                if(film.getProduction()!=null)
                    f.setProduction(film.getProduction());
                if(film.getDescription()!=null)
                    f.setDescription(film.getDescription());
            }
        }
    }

    public void add(Film film, Comment comment){

        comment.setId(film.getComments().size()+1);
        comment.setDate(new Date().toString());
        film.getComments().add(comment);
    }


    public void rate(Film film, float rate){

        for (Film key : map.keySet()) {
            if(film==key){
                map.put(key, map.get(key)+1);
                film.setRating((film.getRating()+rate)/map.get(key));
                return;
            }
        }

        map.put(film, (float)1);
        film.setRating(rate);

    }




    public List<Actor> getActorsOfFilm(int filmId) {

        for(Film f: db){
            if(filmId==f.getId()){
                return f.getCast();
            }
        }
        return null;
    }


    public List<FilmOfActor> getFilmsOfActor(int actorId) {

        for(Actor a: dba){
            if(actorId==a.getId()){
                return a.getFilms();
            }
        }
        return null;
    }

    public void add(Actor actor){

        actor.setId(++currentActorID);
        dba.add(actor);

    }

    public void assign(Actor actor, Film film){

        Actor actorWithoutFilms = new Actor();
        FilmOfActor filmWithoutActors = new FilmOfActor();

        actorWithoutFilms.setName(actor.getName());
        actorWithoutFilms.setSurname(actor.getSurname());
        actorWithoutFilms.setId(actor.getId());

        filmWithoutActors.setTitle(film.getTitle());
        filmWithoutActors.setId(film.getId());

        actor.getFilms().add(filmWithoutActors);
        film.getCast().add(actorWithoutFilms);

    }

}
