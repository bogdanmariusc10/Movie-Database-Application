package org.example;

public class Rating extends Subject
{
    private String username;
    private int score;
    private String comments;

    public Rating(String username, int score, String comments)
    {
        this.username = username;
        this.score = validateScore(score);
        this.comments = comments;
    }
    private int validateScore(int score)
    {
        if (score < 1)
        {
            return 1;
        }
        else if (score > 10)
        {
            return 10;
        }
        else
        {
            return score;
        }
    }


    public String getUsername()
    {
        return username;
    }

    public int getScore()
    {
        return score;
    }

    public String getComments()
    {
        return comments;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void toString(Rating rating)
    {
        System.out.println("Username: " + rating.getUsername());
        System.out.println("Score: " + rating.getScore());
        System.out.println("Comments: " + rating.getComments());
    }
}
