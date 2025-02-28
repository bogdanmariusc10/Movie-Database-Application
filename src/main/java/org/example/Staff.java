package org.example;

import java.time.LocalDate;
import java.util.*;

public abstract class Staff extends User implements StaffInterface
{
    private final List<Request> solveRequests;
    private static SortedSet<String> addedProductionsAndActors;

    public Staff()
    {
        super();
        solveRequests = new ArrayList<>();
        addedProductionsAndActors = new TreeSet<>();
    }

    public void addProduction()
    {
        Scanner S = new Scanner(System.in);

        System.out.println("Enter the title of the production: ");
        String title = S.nextLine();
        System.out.println("Enter the type of the production: ");
        String type = S.nextLine();
        System.out.println("Enter de directors of the production: ");
        List<String> directors = new ArrayList<>();
        String director = S.nextLine();
        while (!director.isEmpty())
        {
            directors.add(director);
            director = S.nextLine();
        }
        System.out.println("Enter de actors of the production: ");
        List<String> actors = new ArrayList<>();
        String actor = S.nextLine();
        while (!actor.isEmpty())
        {
            actors.add(actor);
            actor = S.nextLine();
        }
        System.out.println("Enter de genres of the production: ");
        List<Genre> genres = new ArrayList<>();
        String genre = S.nextLine();
        while (!genre.isEmpty())
        {
            if (Genre.valueOf(genre) != null)
            {
                genres.add(Genre.valueOf(genre));
                genre = S.nextLine();
            }
            else
            {
                System.out.println("Invalid genre");
                genre = S.nextLine();
            }
        }
        System.out.println("Enter the average rating of the production: ");
        String avgRating = S.nextLine();
        double averageRating = Double.parseDouble(avgRating);
        System.out.println("Enter the description of the production: ");
        String description = S.nextLine();

        List<Production> productions = IMDB.getInstance().getProductions();

        if (type.equalsIgnoreCase("MOVIE"))
        {
            System.out.println("Enter the duration of the movie (minutes): ");
            String duration = S.nextLine();
            int movieDuration = Integer.parseInt(duration);
            System.out.println("Enter the release date of the movie (yyyy-mm-dd): ");
            String releaseDate = S.nextLine();
            LocalDate movieReleaseDate = LocalDate.parse(releaseDate);

            Production newMovie = new Movie(title, type, directors, actors, genres, averageRating, description, movieDuration, movieReleaseDate);
            if (productions.contains(newMovie))
            {
                System.out.println("Production already exists in the system.");
            }
            else
            {
                productions.add(newMovie);
                System.out.println("Production added successfully!");
                SortedSet<String> newContribution = new TreeSet<>();
                newContribution.add(String.valueOf(newMovie));
                setContributions(newContribution);
                getAddedProductionsAndActors().add(String.valueOf(newMovie));
            }
        }
        else if (type.equalsIgnoreCase("SERIES")) {
            System.out.println("Enter the release date of the series (yyyy-mm-dd): ");
            String releaseDate = S.nextLine();
            LocalDate seriesReleaseDate = LocalDate.parse(releaseDate);
            System.out.println("Enter the number of seasons of the series: ");
            String seasonsNo = S.nextLine();
            int seasonsNumber = Integer.parseInt(seasonsNo);
            Map<String, List<Episode>> seasons = new HashMap<>();
            for (int no = 1; no <= seasonsNumber; no++) {
                System.out.println("Enter the number of episodes of season " + no + ": ");
                String episodesNo = S.nextLine();
                int episodesNumber = Integer.parseInt(episodesNo);
                List<Episode> episodes = new ArrayList<>();
                for (int epNo = 1; epNo <= episodesNumber; epNo++) {
                    System.out.println("Enter the title of episode " + epNo + ": ");
                    String episodeTitle = S.nextLine();
                    System.out.println("Enter the duration of episode " + epNo + " (minutes): ");
                    String episodeDur = S.nextLine();
                    int episodeDuration = Integer.parseInt(episodeDur);
                    Episode episode = new Episode(episodeTitle, episodeDuration);
                    episodes.add(episode);
                }
                seasons.put("Season " + no, episodes);
            }
            Production newSeries = new Series(title, type, directors, actors, genres, averageRating, description, seriesReleaseDate, seasons);
            if (productions.contains(newSeries)) {
                System.out.println("Production already exists in the system");
            } else {
                productions.add(newSeries);
                System.out.println("Production added successfully");
                SortedSet<String> newContribution = new TreeSet<>();
                newContribution.add(String.valueOf(newSeries));
                setContributions(newContribution);
                getAddedProductionsAndActors().add(String.valueOf(newSeries));
            }
        }
    }

    public void addActor()
    {
        Scanner S = new Scanner(System.in);

        List<Actor> actors = IMDB.getInstance().getActors();

        System.out.println("Enter the name of the actor: ");
        String name = S.nextLine();
        for (Actor actor : actors)
        {
            if (actor.getActorName().equals(name))
            {
                System.out.println("Actor already exists in the system!");
                break;
            }
            else
            {
                System.out.println("Enter the number of performances of the actor: ");
                List<Pair<String, String>> performances = new ArrayList<>();
                int performancesNumber = S.nextInt();
                S.nextLine();
                for (int i = 1; i <= performancesNumber; i++)
                {
                    System.out.println("Enter the name of the production: ");
                    String productionName = S.nextLine();
                    System.out.println("Enter the type of the production: ");
                    String productionType = S.nextLine();
                    Pair<String, String> productionPair = new Pair<>(productionName, productionType);
                    performances.add(productionPair);
                }
                System.out.println("Enter the biography of the actor: ");
                String biography = S.nextLine();
                Actor newActor = new Actor(name, performances, biography);
                actors.add(newActor);
                System.out.println("Actor added successfully!");
                SortedSet<String> newContribution = new TreeSet<>();
                newContribution.add(String.valueOf(newActor));
                setContributions(newContribution);
                getAddedProductionsAndActors().add(String.valueOf(newActor));
            }
        }
    }

    public void removeProduction()
    {
        Scanner S = new Scanner(System.in);
        List<Production> productions = IMDB.getInstance().getProductions();
        System.out.println("Enter the name of the production you want to remove: ");
        String productionName = S.nextLine();
        boolean found = false;
        for (Production production : productions)
        {
            if (production.getProductionTitle().equalsIgnoreCase(productionName))
            {
                found = true;
                productions.remove(production);
                System.out.println("Production removed successfully");
                SortedSet<String> newContribution = new TreeSet<>();
                newContribution.add(String.valueOf(production));
                setContributions(newContribution);
                getAddedProductionsAndActors().remove(String.valueOf(production));
                break;
            }
        }
        if (!found)
        {
            System.out.println("Production not found");
        }
    }

    public void removeActor()
    {
        Scanner S = new Scanner(System.in);
        List<Actor> actors = IMDB.getInstance().getActors();
        System.out.println("Enter the name of the actor you want to remove: ");
        String actorName = S.nextLine();
        boolean found = false;
        for (Actor actor : actors)
        {
            if (actor.getActorName().equals(actorName))
            {
                found = true;
                actors.remove(actor);
                System.out.println("Actor removed successfully");
                SortedSet<String> newContribution = new TreeSet<>();
                newContribution.add(String.valueOf(actor));
                setContributions(newContribution);
                getAddedProductionsAndActors().remove(String.valueOf(actor));
                break;
            }
        }
        if (!found)
        {
            System.out.println("Actor not found.");
        }
    }

    public void updateProduction()
    {
        Scanner S = new Scanner(System.in);
        List<Production> productions = IMDB.getInstance().getProductions();
        System.out.println("Enter the name of the production you want to update: ");
        String productionName = S.nextLine();
        boolean found = false;
        for (Production prod : productions) {
            if (prod.getProductionTitle().equals(productionName))
            {
                found = true;
                System.out.println("Enter the new title of the production: ");
                String title = S.nextLine();
                System.out.println("Enter the new type of the production: ");
                String type = S.nextLine();
                System.out.println("Enter the new directors of the production: ");
                List<String> directors = new ArrayList<>();
                String director = S.nextLine();
                while (!director.isEmpty())
                {
                    directors.add(director);
                    director = S.nextLine();
                }
                System.out.println("Enter the new actors of the production: ");
                List<String> actors = new ArrayList<>();
                String actor = S.nextLine();
                while (!actor.isEmpty())
                {
                    actors.add(actor);
                    actor = S.nextLine();
                }
                System.out.println("Enter the new genres of the production: ");
                List<Genre> genres = new ArrayList<>();
                String genre = S.nextLine();
                while (!genre.isEmpty())
                {
                    if (Genre.valueOf(genre) != null)
                    {
                        genres.add(Genre.valueOf(genre));
                        genre = S.nextLine();
                    }
                    else
                    {
                        System.out.println("Invalid genre");
                        genre = S.nextLine();
                    }
                }
                System.out.println("Enter the new average rating of the production: ");
                String avgRating = S.nextLine();
                double averageRating = Double.parseDouble(avgRating);
                System.out.println("Enter the new description of the production: ");
                String description = S.nextLine();

                if (type.equalsIgnoreCase("MOVIE")) {
                    System.out.println("Enter the new duration of the movie (minutes): ");
                    String duration = S.nextLine();
                    int movieDuration = Integer.parseInt(duration);
                    System.out.println("Enter the new release date of the movie (yyyy-mm-dd): ");
                    String releaseDate = S.nextLine();
                    LocalDate movieReleaseDate = LocalDate.parse(releaseDate);

                    Production newMovie = new Movie(title, type, directors, actors, genres, averageRating, description, movieDuration, movieReleaseDate);
                    if (productions.contains(newMovie)) {
                        System.out.println("Production already exists in the system");
                    } else {
                        productions.add(newMovie);
                        System.out.println("Production updated successfully");
                        SortedSet<String> newContribution = new TreeSet<>();
                        newContribution.add(String.valueOf(newMovie));
                        setContributions(newContribution);
                        getAddedProductionsAndActors().add(String.valueOf(newMovie));
                        break;
                    }
                } else if (type.equalsIgnoreCase("SERIES")) {
                    System.out.println("Enter the new release date of the series (yyyy-mm-dd): ");
                    String releaseDate = S.nextLine();
                    LocalDate seriesReleaseDate = LocalDate.parse(releaseDate);
                    System.out.println("Enter the new number of seasons of the series: ");
                    String seasonsNo = S.nextLine();
                    int seasonsNumber = Integer.parseInt(seasonsNo);
                    Map<String, List<Episode>> seasons = new HashMap<>();
                    for (int no = 1; no <= seasonsNumber; no++) {
                        System.out.println("Enter the number of episodes of season " + no + ": ");
                        String episodesNo = S.nextLine();
                        int episodesNumber = Integer.parseInt(episodesNo);
                        List<Episode> episodes = new ArrayList<>();
                        for (int epNo = 1; epNo <= episodesNumber; epNo++) {
                            System.out.println("Enter the title of episode " + epNo + ": ");
                            String episodeTitle = S.nextLine();
                            System.out.println("Enter the duration of episode " + epNo + " (minutes): ");
                            String episodeDur = S.nextLine();
                            int episodeDuration = Integer.parseInt(episodeDur);
                            Episode episode = new Episode(episodeTitle, episodeDuration);
                            episodes.add(episode);
                        }
                        seasons.put("Season " + no, episodes);
                    }
                    Production newSeries = new Series(title, type, directors, actors, genres, averageRating, description, seriesReleaseDate, seasons);
                    if (productions.contains(newSeries)) {
                        System.out.println("Production already exists in the system");
                    } else {
                        productions.add(newSeries);
                        System.out.println("Production updated successfully");
                        SortedSet<String> newContribution = new TreeSet<>();
                        newContribution.add(String.valueOf(newSeries));
                        setContributions(newContribution);
                        getAddedProductionsAndActors().add(String.valueOf(newSeries));
                        break;
                    }
                }
            }
        }
        if (!found)
        {
            System.out.println("Production not found.");
        }
    }

    public void updateActor()
    {
        Scanner S = new Scanner(System.in);
        List<Actor> actors = IMDB.getInstance().getActors();
        System.out.println("Enter the name of the actor you want to update: ");
        String actorName = S.nextLine();
        for (Actor act : actors) {
            if (act.getActorName().equalsIgnoreCase(actorName)) {
                System.out.println("Enter the new name of the actor: ");
                String name = S.nextLine();
                System.out.println("Enter the new number of performances of the actor: ");
                List<Pair<String, String>> performances = new ArrayList<>();
                int performancesNumber = S.nextInt();
                S.nextLine();
                for (int i = 1; i <= performancesNumber; i++)
                {
                    System.out.println("Enter the name of the production: ");
                    String productionName = S.nextLine();
                    System.out.println("Enter the type of the production: ");
                    String productionType = S.nextLine();
                    Pair<String, String> productionPair = new Pair<>(productionName, productionType);
                    performances.add(productionPair);
                }
                System.out.println("Enter the new biography of the actor: ");
                String biography = S.nextLine();
                Actor newActor = new Actor(name, performances, biography);
                actors.add(newActor);
                System.out.println("Actor updated successfully");
                SortedSet<String> newContribution = new TreeSet<>();
                newContribution.add(String.valueOf(newActor));
                setContributions(newContribution);
                getAddedProductionsAndActors().add(String.valueOf(newActor));
                break;
            }
            else
            {
                System.out.println("Actor not found");
                break;
            }
        }
    }

    public void solveRequests()
    {
        User userCLI = IMDB.getInstance().getUserCLI();
        Scanner S = new Scanner(System.in);
        List<Request> requests = RequestHolder.getRequests();
        System.out.println("Choose a request to solve: ");
        for (int i = 1; i <= requests.size(); i++)
        {
            System.out.println(i);
            requests.get(i - 1).toString(requests.get(i - 1));
        }
        int requestNumber = S.nextInt();
        S.nextLine();
        Request request = requests.get(requestNumber);
        System.out.println("1. Approve and solve\n2. Reject");
        int choice = S.nextInt();
        S.nextLine();
        switch(choice)
        {
            case 1 -> {
                request.approveRequest();
                String issue = request.getDescription();
                RequestTypes reqType = request.getRequestType();
                switch (reqType)
                {
                    case ACTOR_ISSUE, MOVIE_ISSUE -> {
                        if (issue.contains("add"))
                        {
                            userCLI.updateExperience(IMDB.Actions.ADD_NEW, userCLI.getExperience());
                        }
                        else
                        {
                            userCLI.updateExperience(IMDB.Actions.SUCCESSFUL_REQUEST, userCLI.getExperience());
                        }
                    }
                    case OTHERS -> userCLI.updateExperience(IMDB.Actions.SUCCESSFUL_REQUEST, userCLI.getExperience());
                }
                System.out.println(issue);
                System.out.println("Type 'DONE' when request is solved.");
                String done = S.nextLine();
                if (done.equals("DONE"))
                {
                    List<Request> requestsList = IMDB.getInstance().getRequests();
                    for (Request req : requestsList)
                    {
                        if (req.getDescription().equals(issue))
                        {
                            requests.remove(req);
                            RequestHolder.removeRequest(req);
                        }
                    }
                }
            }
            case 2 -> {
                request.rejectRequest();
                RequestHolder.removeRequest(request);
            }
        }
    }

    public List<Request> getSolveRequests()
    {
        return solveRequests;
    }
    public static SortedSet<String> getAddedProductionsAndActors()
    {
        return addedProductionsAndActors;
    }
}
