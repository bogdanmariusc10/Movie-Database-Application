package org.example;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public class Movie extends Production
{
    private int movieDuration;
    private int movieLaunchYear;

    public Movie(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, double averageRating, String description, int movieDuration, LocalDate movieReleaseDate) {
        super();
        this.movieDuration = movieDuration;
        this.movieLaunchYear = movieReleaseDate.getYear();
    }

    public Movie()
    {

    }

    //GETTERS
    public int getMovieDuration()
    {
        return movieDuration;
    }

    public int getMovieLaunchYear()
    {
        return movieLaunchYear;
    }

    //SETTERS
    public void setMovieDuration(int movieDuration)
    {
        this.movieDuration = movieDuration;
    }

    public void setMovieLaunchYear(int movieLaunchYear)
    {
        this.movieLaunchYear = movieLaunchYear;
    }

    @Override
    public int compareTo(@NotNull Production o) {
        return 0;
    }
}
