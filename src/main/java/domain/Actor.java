package domain;

import domain.services.FilmOfActor;

import java.util.List;

/**
 * Created by zolto on 11.05.2017.
 */

public class Actor {

    private int id;
    private String name;
    private String surname;
    private List<FilmOfActor> films;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<FilmOfActor> getFilms() {
        return films;
    }

    public void setFilms(List<FilmOfActor> films) {
        this.films = films;
    }
}
