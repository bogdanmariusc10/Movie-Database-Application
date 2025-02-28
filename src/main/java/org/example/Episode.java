package org.example;

public class Episode
{
    public String episodeTitle;
    public int episodeDuration;

    public Episode(String episodeTitle, int episodeDuration)
    {
        this.episodeTitle = episodeTitle;
        this.episodeDuration = extractDuration(String.valueOf(episodeDuration));
    }

    private int extractDuration(String durationString) {
        String numericPart = durationString.replaceAll("[^\\d]", "");

        try {
            return Integer.parseInt(numericPart);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //GETTERS
    public String getEpisodeTitle()
    {
        return episodeTitle;
    }

    public int getEpisodeDuration()
    {
        return episodeDuration;
    }

    //SETTERS
    public void setEpisodeTitle(String episodeTitle)
    {
        this.episodeTitle = episodeTitle;
    }

    public void setEpisodeDuration(int episodeDuration)
    {
        this.episodeDuration = episodeDuration;
    }
}
