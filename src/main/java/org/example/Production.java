package org.example;

import java.util.List;

public abstract class Production implements Comparable<Production>
{
    private String productionTitle;
    private String type;
    private List<String> directorsName;
    private List<String> actorsName;
    private List<Genre> genreList;
    private List<Rating> ratingList;
    private String movieDescription;
    private Double movieAverageRating;

    public int compare(Object o)
    {
        return 0;
    }

    public void display()
    {
        System.out.println("Title: " + productionTitle);
        System.out.println("Type: " + type);
        System.out.println("Directors: " + directorsName);
        System.out.println("Actors: " + actorsName);
        System.out.println("Genres: " + genreList);
        System.out.println("Description: " + movieDescription);
        System.out.println("Average rating: " + movieAverageRating);
        System.out.println("Ratings: ");
        for (Rating rating : ratingList)
        {
            rating.toString(rating);
        }
    }

    //GETTERS
    public String getProductionTitle()
    {
        return productionTitle;
    }

    public List<String> getDirectorsName()
    {
        return directorsName;
    }

    public List<String> getActorsName()
    {
        return actorsName;
    }

    public List<Genre> getGenreList()
    {
        return genreList;
    }

    public List<Rating> getRatingList()
    {
        return ratingList;
    }

    public String getMovieDescription()
    {
        return movieDescription;
    }

    public Double getMovieAverageRating()
    {
        return movieAverageRating;
    }
    public String getProductionType()
    {
        return type;
    }

    //SETTERS
    public void setProductionTitle(String productionTitle)
    {
        this.productionTitle = productionTitle;
    }

    public void setDirectorsName(List<String> directorsName)
    {
        this.directorsName = directorsName;
    }

    public void setActorsName(List<String> actorsName)
    {
        this.actorsName = actorsName;
    }

    public void setGenreList(List<Genre> genreList)
    {
        this.genreList = genreList;
    }

    public void setRatingList(List<Rating> ratingList)
    {
        this.ratingList = ratingList;
    }

    public void setMovieDescription(String movieDescription)
    {
        this.movieDescription = movieDescription;
    }

    public void setMovieAverageRating(Double movieAverageRating)
    {
        this.movieAverageRating = movieAverageRating;
    }

    public void setProductionType(String type)
    {
        this.type = type;
    }
}
