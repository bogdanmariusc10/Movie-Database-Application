package org.example;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Series extends Production
{
    private int seriesLaunchYear;
    private int seriesSeasonNumber;
    private Map<String, List<Episode>> seasons;

    public Series(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, double averageRating, String description, LocalDate seriesReleaseDate, Map<String, List<Episode>> seasons) {
        super();
        this.seriesLaunchYear = seriesReleaseDate.getYear();
        this.seriesSeasonNumber = seasons.size();
    }

    public Series()
    {

    }

    //GETTERS
    public int getSeriesLaunchYear()
    {
        return seriesLaunchYear;
    }

    public int getSeriesSeasonNumber()
    {
        return seriesSeasonNumber;
    }

    public Map<String, List<Episode>> getSeasons()
    {
        return seasons;
    }

    //SETTERS
    public void setSeriesLaunchYear(int seriesLaunchYear)
    {
        this.seriesLaunchYear = seriesLaunchYear;
    }

    public void setSeriesSeasonNumber(int seriesSeasonNumber)
    {
        this.seriesSeasonNumber = seriesSeasonNumber;
    }

    public void setSeasons(Map<String, List<Episode>> dictionary)
    {
        this.seasons = seasons;
    }

    @Override
    public int compareTo(@NotNull Production o) {
        return 0;
    }
}
