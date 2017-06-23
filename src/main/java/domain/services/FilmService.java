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
   private static Map<Film, Float> map = new HashMap<Film, Float>();
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
       f.setRating(0);
       db.add(f);
   }

   public void update(Film film){
       for(Film f : db){
           if(f.getId() == film.getId()){
               f.setTitle(film.getTitle());
               f.setActors(film.getActors());
               f.setYear(film.getYear());
               f.setInfo(film.getInfo());
           }
       }
   }

   public void addComm(Film film, Comment comment){
       comment.setId(film.getComments().size()+1);
       film.getComments().add(comment);
   }

   public void rate(Film film, float rate){

       for(Film key : map.keySet()){
           if(film==key){
               map.put(key, map.get(key)+1);
               film.setRating((film.getRating())+rate/map.get(key));
               return;
           }
       }
       map.put(film, (float)1);
       film.setRating(rate);
   }

   public void delete(Film film){
       db.remove(film);
   }

}
