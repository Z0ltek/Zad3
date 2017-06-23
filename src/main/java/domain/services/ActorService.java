package domain.services;

import domain.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zolto on 11.05.2017.
 */
public class ActorService {


    private static List<Actor> db = new ArrayList<Actor>();
    private static int currentId = 0;
    public List<Actor> getAll(){
        return db;
    }

    public Actor get(int id){
        for(Actor a : db){
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }


    public void add(Actor a){
        a.setId(++currentId);
        db.add(a);
    }
}
