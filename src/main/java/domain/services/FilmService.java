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
   private static int currentId = 0;
   public List<Film> getAll(){
       return db;
   }

   public Film get (int id){
       for(Film f : db){
           if(f.getId() == id ){
               return f;
           }
       }
       return null;
   }

   public void add(Film f){
       f.setId(++currentId);
       db.add(f);
   }

   public void update(Film film){
       for(Film f : db){
           if(f.getId() == film.getId()){
               f.setTitle(film.getTitle());
               f.setActors(film.getActors());
           }
       }
   }

   public void delete(Film film){
       db.remove(film);
   }

}
