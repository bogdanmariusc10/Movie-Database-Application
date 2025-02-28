package org.example;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String actorName;
    private List<Pair<String, String>> performances;
    private String biography;

    public Actor(String actorName) {
        this.actorName = actorName;
        this.performances = new ArrayList<>();
        this.biography = "";
    }

    public Actor(String name, List<Pair<String, String>> performances, String biography) {
        this.actorName = name;
        this.performances = performances;
        this.biography = biography;
    }

    public void addMovie(String movieName, String movieType) {
        Pair<String, String> moviePair = new Pair<>(movieName, movieType);
        performances.add(moviePair);
    }

    public String getActorName() {
        return actorName;
    }

    public List<Pair<String, String>> getPerformances() {
        return performances;
    }

    public String getBiography()
    {
        return biography;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setPerformances(List<Pair<String, String>> performances) {
        this.performances = performances;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void display()
    {
        System.out.println("Name: " + getActorName());
        System.out.println("Performances: " + getPerformances());
        System.out.println("Biography: " + getBiography());
    }
}

class Pair<T, U>
{
    private T name;
    private U type;

    public Pair(T name, U type)
    {
        this.name = name;
        this.type = type;
    }

    //GETTERS
    public T getName() {
        return name;
    }

    public U getType() {
        return type;
    }

    //SETTERS
    public void setName(T name) {
        this.name = name;
    }

    public void setType(U type)
    {
        this.type = type;
    }
}
