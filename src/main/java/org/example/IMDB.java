package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class IMDB
{
    public static IMDB instance;
    private final List<Regular> regulars;
    private final List<Contributor> contributors;
    private final List<Admin> admins;
    private final List<Actor> actors;
    private final List<Request> requests;
    private final List<Production> productions;
    private User userCLI;

    public IMDB()
    {
        regulars = new ArrayList<>();
        contributors = new ArrayList<>();
        admins = new ArrayList<>();
        actors = new ArrayList<>();
        requests = new ArrayList<>();
        productions = new ArrayList<>();
        userCLI = null;
    }

    public static synchronized IMDB getInstance()
    {
        if(instance == null)
        {
            instance = new IMDB();
        }
        return instance;
    }

    public void run()
    {
        //Load data from accounts.json
        String accountsFile = "C:\\Users\\Bogdan-Marius\\Desktop\\TEMA_POO\\src\\main\\resources\\input\\accounts.json";

        try
        {
            Object accountsObj = new JSONParser().parse(new FileReader(accountsFile));
            JSONArray jsonArrayAccounts = (JSONArray) accountsObj;

            for (Object accountObject : jsonArrayAccounts)
            {
                JSONObject user = (JSONObject) accountObject;
                String username = (String) user.get("username");
                String experience = (String) user.get("experience");

                JSONObject information = (JSONObject) user.get("information");
                JSONObject credentials = (JSONObject) information.get("credentials");
                String email = (String) credentials.get("email");
                String password = (String) credentials.get("password");
                String name = (String) information.get("name");
                String country = (String) information.get("country");
                Long ageLong = (Long) information.get("age");
                String age = ageLong != null ? String.valueOf(ageLong) : null;
                String genderStr = (String) information.get("gender");
                char gender = genderStr != null && !genderStr.isEmpty() ? genderStr.charAt(0) : '\0';
                String birthDateStr = (String) information.get("birthDate");
                LocalDate birthDate = birthDateStr != null ? LocalDate.parse(birthDateStr) : null;

                assert age != null;
                User.Information userInfo = new User.Builder(email, password)
                        .age(String.valueOf(Integer.parseInt(age)))
                        .country(country)
                        .name(name)
                        .gender(gender)
                        .dateOfBirth(birthDate)
                        .build();

                String accountTypeStr = (String) user.get("userType");
                AccountType accountType = AccountType.valueOf(accountTypeStr.toUpperCase());
                JSONArray productionsContribution = (JSONArray) user.get("productionsContribution");
                JSONArray actorsContribution = (JSONArray) user.get("actorsContribution");
                JSONArray favoriteProductions = (JSONArray) user.get("favoriteProductions");
                JSONArray favoriteActors = (JSONArray) user.get("favoriteActors");
                JSONArray notifications = (JSONArray) user.get("notifications");

                User newUser = UserFactory.createUser(accountType, username, String.valueOf(experience), userInfo);

                SortedSet<String> userFavorites = new TreeSet<>();

                if (favoriteProductions != null) {
                    for (Object favoriteProduction : favoriteProductions) {
                        if (favoriteProduction instanceof String) {
                            userFavorites.add((String) favoriteProduction);
                        }
                    }
                }

                if (favoriteActors != null) {
                    for (Object favoriteActor : favoriteActors) {
                        if (favoriteActor instanceof String) {
                            userFavorites.add((String) favoriteActor);
                        }
                    }
                }

                SortedSet<String> userContributions = new TreeSet<>();

                if (productionsContribution != null) {
                    for (Object productionContribution : productionsContribution) {
                        if (productionContribution instanceof String) {
                            userContributions.add((String) productionContribution);
                        }
                    }
                }

                if (actorsContribution != null) {
                    for (Object actorContribution : actorsContribution) {
                        if (actorContribution instanceof String) {
                            userContributions.add((String) actorContribution);
                        }
                    }
                }

                newUser.setExperience(experience);
                newUser.setUsername(username);
                newUser.setUserInfo(userInfo);
                newUser.setAccountType(accountType);
                newUser.setFavorites(userFavorites);
                newUser.setContributions(userContributions);
                newUser.setNotifications(notifications);


                if (newUser instanceof Regular) {
                    regulars.add((Regular) newUser);
                } else if (newUser instanceof Contributor) {
                    contributors.add((Contributor) newUser);
                } else if (newUser instanceof Admin) {
                    admins.add((Admin) newUser);
                }
            }
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }

        //Load data from actors.json
        String actorsFile = "C:\\Users\\Bogdan-Marius\\Desktop\\TEMA_POO\\src\\main\\resources\\input\\actors.json";

        try
        {
            Object actorsObj = new JSONParser().parse(new FileReader(actorsFile));
            JSONArray jsonArrayActors = (JSONArray) actorsObj;

            for (Object actorsObject : jsonArrayActors)
            {
                JSONObject actor = (JSONObject) actorsObject;
                String name = (String) actor.get("name");

                Actor newActor = new Actor(name);

                List<Pair<String, String>> performancesList = new ArrayList<>();
                JSONArray performances = (JSONArray) actor.get("performances");

                for (Object performanceObj : performances) {
                    JSONObject performance = (JSONObject) performanceObj;
                    String title = (String) performance.get("title");
                    String type = (String) performance.get("type");
                    performancesList.add(new Pair<>(title, type));
                }
                String biography = (String) actor.get("biography");

                newActor.setActorName(name);
                newActor.setPerformances(performancesList);
                newActor.setBiography(biography);

                actors.add(newActor);
            }
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }



        //Load data from production.json
        String productionFile = "C:\\Users\\Bogdan-Marius\\Desktop\\TEMA_POO\\src\\main\\resources\\input\\production.json";

        try {
            Object productionObj = new JSONParser().parse(new FileReader(productionFile));
            JSONArray jsonArrayProduction = (JSONArray) productionObj;

            for (Object productionsObject : jsonArrayProduction) {
                JSONObject production = (JSONObject) productionsObject;

                String title = (String) production.get("title");
                String type = (String) production.get("type");

                JSONArray directors = (JSONArray) production.get("directors");
                JSONArray actorsName = (JSONArray) production.get("actors");
                JSONArray genres = (JSONArray) production.get("genres");

                List<Rating> ratingsList = new ArrayList<>();
                JSONArray ratings = (JSONArray) production.get("ratings");

                for (Object ratingObj : ratings) {
                    JSONObject rating = (JSONObject) ratingObj;
                    String username = (String) rating.get("username");
                    int ratingValue = ((Long) rating.get("rating")).intValue();
                    String comment = (String) rating.get("comment");
                    Rating newRating = new Rating(username, ratingValue, comment);
                    ratingsList.add(newRating);
                }

                String plot = (String) production.get("plot");
                Double averageRating = (Double) production.get("averageRating");

                Production newProduction;

                if ("Movie".equalsIgnoreCase(type)) {
                    newProduction = new Movie();
                    String duration = (String) production.get("duration");
                    int movieDuration = Integer.parseInt(duration.replaceAll("[^0-9]", ""));
                    ((Movie) newProduction).setMovieDuration(movieDuration);

                    Long releaseYearLong = (Long) production.get("releaseYear");
                    int releaseYear = releaseYearLong != null ? Math.toIntExact(releaseYearLong) : 0;
                    ((Movie) newProduction).setMovieLaunchYear(releaseYear);

                } else if ("Series".equalsIgnoreCase(type)) {
                    newProduction = new Series();
                    JSONObject seasonsObject = (JSONObject) production.get("seasons");
                    Object numSeasonsObject = seasonsObject.get("numSeasons");
                    int seasonNumber = numSeasonsObject != null ? ((Long) numSeasonsObject).intValue() : 0;
                    ((Series) newProduction).setSeriesSeasonNumber(seasonNumber);

                    Map<String, List<Episode>> seasons = new HashMap<>();
                    JSONObject ses = (JSONObject) production.get("seasons");

                    for (Object seasonName : ses.keySet()) {
                        JSONArray episodes = (JSONArray) seasons.get(seasonName);
                        List<Episode> episodeList = new ArrayList<>();

                        if (episodes != null) { // Check if episodes is not null before iterating
                            for (Object episodeObj : episodes) {
                                JSONObject episode = (JSONObject) episodeObj;
                                String episodeTitle = (String) episode.get("episodeName");
                                String episodeDur = (String) episode.get("duration");
                                int episodeDuration = Integer.parseInt(episodeDur.replaceAll("[^0-9]", ""));
                                episodeList.add(new Episode(episodeTitle, episodeDuration));
                            }
                        }

                        seasons.put((String) seasonName, episodeList);
                    }

                    ((Series) newProduction).setSeasons(seasons);
                } else {
                    continue;
                }
                newProduction.setProductionTitle(title);
                newProduction.setProductionType(type);
                newProduction.setDirectorsName(directors);
                newProduction.setActorsName(actorsName);
                newProduction.setGenreList(genres);
                newProduction.setRatingList(ratingsList);
                newProduction.setMovieDescription(plot);
                newProduction.setMovieAverageRating(averageRating);

                productions.add(newProduction);

                List<String> actorsNameList = new ArrayList<>();
                for (Object actorsNameObj : actorsName)
                {
                    String name = actorsNameObj.toString();
                    actorsNameList.add(name);
                }
                for (String actName : actorsNameList)
                {
                    boolean actorExists = false;

                    for (Actor existingActor : actors)
                    {
                        if (existingActor.getActorName().equals(actName))
                        {
                            actorExists = true;
                            break;
                        }
                    }
                    if (!actorExists)
                    {
                        Actor newActor = new Actor(actName);
                        List<Pair<String, String>> performancesList = new ArrayList<>();
                        performancesList.add(new Pair<>(title, type));
                        newActor.setPerformances(performancesList);
                        actors.add(newActor);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        //Load data from requests.json
        String requestsFile = "C:\\Users\\Bogdan-Marius\\Desktop\\TEMA_POO\\src\\main\\resources\\input\\requests.json";

        try
        {
            Object requestsObj = new JSONParser().parse(new FileReader(requestsFile));
            JSONArray jsonArrayRequests = (JSONArray) requestsObj;

            for (Object requestsObject : jsonArrayRequests)
            {
                JSONObject request = (JSONObject) requestsObject;

                Object typeObj = request.get("type");
                String typeStr = (String) typeObj;
                RequestTypes type = RequestTypes.valueOf(typeStr.toUpperCase());
                Object createdDateObj = request.get("createdDate");
                String createdDateString = (String) createdDateObj;
                LocalDateTime createdDate = LocalDateTime.parse(createdDateString, DateTimeFormatter.ISO_DATE_TIME);
                String username = (String) request.get("username");
                String actorName = (String) request.get("actorName");
                String movieTitle = (String) request.get("movieTitle");
                String to = (String) request.get("to");
                String description = (String) request.get("description");

                Request newRequest = new Request(type, createdDate, username, actorName != null ? actorName : movieTitle, description, to);

                newRequest.setRequestType(type);
                newRequest.setCreationDate(createdDate);
                newRequest.setRequesterUsername(username);
                newRequest.setTitleOrName(actorName != null ? actorName : movieTitle);
                newRequest.setResolverUsername(to);
                newRequest.setDescription(description);

                requests.add(newRequest);
            }
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }

        //Authenticate user
        boolean validCredentials = false;
        while (!validCredentials) {
            System.out.println("Welcome back! Enter your credentials:");
            Scanner scanner = new Scanner(System.in);
            System.out.print("email: ");
            String email = scanner.nextLine();

            System.out.print("password: ");
            String password = scanner.nextLine();

            for (Regular regular : regulars) {
                if (regular.getUserInfo().getCredentials().getEmail().equals(email) &&
                        regular.getUserInfo().getCredentials().getPassword().equals(password)) {
                    userCLI = regular;
                    validCredentials = true;
                    break;
                }
            }

            if (!validCredentials) {
                for (Contributor contributor : contributors) {
                    if (contributor.getUserInfo().getCredentials().getEmail().equals(email) &&
                            contributor.getUserInfo().getCredentials().getPassword().equals(password)) {
                        userCLI = contributor;
                        validCredentials = true;
                        break;
                    }
                }
            }

            if (!validCredentials) {
                for (Admin admin : admins) {
                    if (admin.getUserInfo().getCredentials().getEmail().equals(email) &&
                            admin.getUserInfo().getCredentials().getPassword().equals(password)) {
                        userCLI = admin;
                        validCredentials = true;
                        break;
                    }
                }

                if (!validCredentials) {
                    System.out.println("Invalid credentials! Try again.");
                }
            }
        }

        System.out.println("Welcome back, " + userCLI.getUserInfo().getName() + "!");
        System.out.println("Username: " + userCLI.getUsername());
        System.out.println("Experience: " + userCLI.getExperience());
        System.out.println("Choose action:\n" + switch (userCLI.getAccountType()) {
            case REGULAR -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Create/Withdraw request\n7. Add/Remove rating\n8. Logout";
            case CONTRIBUTOR -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Create/Withdraw request\n7. Add/Remove production/actor\n8. View/Resolve request\n9. Update production/actor info\n10. Logout";
            case ADMIN -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Add/Remove production/actor\n7. View/Resolve request\n8. Update production/actor info\n9. Add/Remove user\n10. Logout";
        });

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (userCLI.getAccountType())
        {
            case REGULAR:
                handleRegularUserAction(choice, userCLI);
                break;
            case CONTRIBUTOR:
                handleContributorUserAction(choice, userCLI);
                break;
            case ADMIN:
                handleAdminUserAction(choice, userCLI);
                break;
        }
    }

    //GET REGULARS
    public List<Regular> getRegulars()
    {
        return regulars;
    }
    //GET CONTRIBUTORS
    public List<Contributor> getContributors()
    {
        return contributors;
    }
    //GET ADMINS
    public List<Admin> getAdmins()
    {
        return admins;
    }
    //GET ACTORS
    public List<Actor> getActors()
    {
        return actors;
    }
    //GET PRODUCTIONS
    public List<Production> getProductions()
    {
        return productions;
    }
    //GET REQUESTS
    public List<Request> getRequests()
    {
        return requests;
    }

    public User getUserCLI()
    {
        return userCLI;
    }

    enum Actions
    {
        ADD_RATING,
        SUCCESSFUL_REQUEST,
        ADD_NEW
    }

    public void search()
    {
        Scanner S = new Scanner(System.in);
        System.out.println("What do you want to find? ");
        System.out.println("1. Production\n2. Actor");
        int choice = S.nextInt();
        S.nextLine();

        switch (choice)
        {
            case 1 -> {
                System.out.println("Enter production title: ");
                String title = S.nextLine();
                boolean found = false;
                for (Production production : productions)
                {
                    if (title.equalsIgnoreCase(production.getProductionTitle()))
                    {
                        found = true;
                        production.display();
                    }
                }
                if (!found)
                {
                    System.out.println("Production not found.");
                }
            }
            case 2 -> {
                System.out.print("Enter actor name: ");
                String actorName = S.nextLine();
                boolean found = false;
                for (Actor actor : actors)
                {
                    if (actorName.equalsIgnoreCase(actor.getActorName()))
                    {
                        found = true;
                        actor.display();
                    }
                }
                if (!found)
                {
                    System.out.println("Actor not found.");
                }
            }
        }
    }

    public void viewProductions()
    {
        Scanner S = new Scanner(System.in);
        System.out.println("Choose sorting criteria: ");
        System.out.println("1. Genre\n2. Most rated");
        int choice = S.nextInt();

        switch (choice)
        {
            case 1 -> {
                System.out.println("Choose genre:\n1. Action\n2. Adventure\n3. Comedy\n4. Drama\n5. Horror\n6. Science-Fiction\n7. Fantasy\n8. Romance\n9. Mystery\n10. Thriller\n11. Crime\n12. Biography\n13. War");
                int genreChoice = S.nextInt();
                switch (genreChoice)
                {
                    case 1 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("ACTION"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 2 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("ADVENTURE"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 3 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("COMEDY"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 4 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("DRAMA"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 5 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("HORROR"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 6 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("SCIENCE_FICTION"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 7 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("FANTASY"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 8 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("ROMANCE"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 9 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("MYSTERY"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 10 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("THRILLER"))) {
                                production.display();
                            }
                        }
                    }
                    case 11 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("CRIME"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 12 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("BIOGRAPHY"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                    case 13 -> {
                        for (Production production : productions) {
                            if (production.getGenreList().contains(Genre.valueOf("WAR"))) {
                                production.display();
                            }
                            else
                            {
                                System.out.println("No productions found");
                                break;
                            }
                        }
                    }
                }
            }
            case 2 -> {
                productions.sort(Comparator.comparing(Production::getMovieAverageRating).reversed());
                for (Production production : productions)
                {
                    production.display();
                }
            }
        }
    }

    public void viewActors()
    {
        actors.sort(Comparator.comparing(Actor::getActorName));
        for (Actor actor : actors)
        {
            System.out.println(actor.getActorName());
        }
    }

    public int wantToDoSomethingElse(User userCLI)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to continue? (Y/N)");
        String answer = scanner.nextLine();
        int choice = 0;
        if (answer.equalsIgnoreCase("Y"))
        {
            System.out.println("Choose action:\n" + switch (userCLI.getAccountType()) {
                case REGULAR -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Create/Withdraw request\n7. Add/Remove rating\n8. Logout";
                case CONTRIBUTOR -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Create/Withdraw request\n7. Add/Remove production/actor\n8. View/Resolve request\n9. Update production/actor info\n10. Logout";
                case ADMIN -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Add/Remove production/actor\n7. View/Resolve request\n8. Update production/actor info\n9. Add/Remove user\n10. Logout";
            });
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        else if (answer.equalsIgnoreCase("N"))
        {
            System.out.println("Continue or logout?");
            System.out.println("1. Continue\n2. Logout");
            int choice1 = scanner.nextInt();
            scanner.nextLine();
            if (choice1 == 1)
            {
                System.out.println("Choose action:\n" + switch (userCLI.getAccountType()) {
                    case REGULAR -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Create/Withdraw request\n7. Add/Remove rating\n8. Logout";
                    case CONTRIBUTOR -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Create/Withdraw request\n7. Add/Remove production/actor\n8. View/Resolve request\n9. Update production/actor info\n10. Logout";
                    case ADMIN -> "1. View productions\n2. View actors\n3. View notifications\n4. Search production/actor\n5. Add/Delete production/actor from favorites\n6. Add/Remove production/actor\n7. View/Resolve request\n8. Update production/actor info\n9. Add/Remove user\n10. Logout";
                });
                choice = scanner.nextInt();
                scanner.nextLine();
                wantToDoSomethingElse(userCLI);
            }
            else if (choice1 == 2)
            {
                User.logout(userCLI);
            }
        }
        else
        {
            System.out.println("Invalid option. Try again!");
            wantToDoSomethingElse(userCLI);
        }
        return choice;
    }

    public void handleRegularUserAction(int choice, User userCLI)
    {
        Scanner scanner = new Scanner(System.in);
        boolean wantToContinue = true;

        while (wantToContinue)
        {
            switch (choice) {
                //View productions
                case 1:
                    viewProductions();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //View actors
                case 2:
                    viewActors();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //View notifications
                case 3:
                    for (String notification : userCLI.getNotifications())
                    {
                        System.out.println(notification);
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //Search movie/series/actor
                case 4:
                    search();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //Add/Delete production/actor from favorites
                case 5:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add production/actor to favorites\n2. Delete production/actor from favorites");
                    int action = scanner.nextInt();
                    scanner.nextLine();

                    switch (action)
                    {
                        case 1 -> userCLI.addToFavorites();
                        case 2 -> userCLI.removeFromFavorites();
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //Create/Withdraw request
                case 6:
                    System.out.println("Choose action: ");
                    System.out.println("1. Create request\n2. Withdraw request");
                    int action1 = scanner.nextInt();

                    switch (action1)
                    {
                        case 1 -> ((Regular) userCLI).createRequest();
                        case 2 -> ((Regular) userCLI).removeRequest();
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //Add/Remove rating
                case 7:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add rating\n2. Remove rating");
                    int action2 = scanner.nextInt();

                    switch (action2)
                    {
                        case 1 -> ((Regular) userCLI).addRating();
                        case 2 -> ((Regular) userCLI).removeRating();
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0)
                    {
                        wantToContinue = false;
                    }
                    break;
                //Logout
                case 8:
                    User.logout(userCLI);
                    break;
            }
        }
    }

    public void handleContributorUserAction(int choice, User userCLI)
    {
        Scanner S = new Scanner(System.in);
        boolean wantToContinue = true;

        while (wantToContinue)
        {
            switch (choice)
            {
                //View productions
                case 1:
                    viewProductions();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //View actors
                case 2:
                    viewActors();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //View notifications
                case 3:
                    for (String notification : userCLI.getNotifications()) {
                        System.out.println(notification);
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Search movie/series/actor
                case 4:
                    search();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Add/Delete production/actor from favorites
                case 5:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add production/actor to favorites\n2. Delete production/actor from favorites");
                    int action = S.nextInt();
                    S.nextLine();

                    switch (action) {
                        case 1 -> userCLI.addToFavorites();
                        case 2 -> userCLI.removeFromFavorites();
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Create/Withdraw request
                case 6:
                    System.out.println("Choose action: ");
                    System.out.println("1. Create request\n2. Withdraw request");
                    int action1 = S.nextInt();

                    switch (action1) {
                        case 1 -> ((Regular) userCLI).createRequest();
                        case 2 -> ((Regular) userCLI).removeRequest();
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Add/Remove production/actor
                case 7:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add production/actor\n2. Remove production/actor");
                    int action2 = S.nextInt();
                    S.nextLine();
                    switch (action2) {
                        case 1:
                            System.out.println("Choose to add: ");
                            System.out.println("1. Production\n2. Actor");
                            int add = S.nextInt();
                            S.nextLine();
                            switch (add) {
                                case 1:
                                    ((Staff) userCLI).addProduction();
                                    userCLI.updateExperience(Actions.ADD_NEW, userCLI.getExperience());
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                case 2:
                                    ((Staff) userCLI).addActor();
                                    userCLI.updateExperience(Actions.ADD_NEW, userCLI.getExperience());
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid option!");
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Choose to remove: ");
                            System.out.println("1. Production\n2. Actor");
                            int remove = S.nextInt();
                            S.nextLine();

                            switch (remove) {
                                case 1:
                                    ((Staff) userCLI).removeProduction();
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                case 2:
                                    ((Staff) userCLI).removeActor();
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                            }
                            break;
                    }
                    //View/Solve request
                case 8:
                    System.out.println("Choose action: ");
                    System.out.println("1. View requests\n2. Solve request");
                    int action3 = S.nextInt();
                    S.nextLine();

                    switch (action3) {
                        case 1:
                            for (Request request : requests) {
                                System.out.println(request.getRequestType());
                                System.out.println(request.getTitleOrName());
                                System.out.println(request.getDescription());
                                System.out.println(request.getRequesterUsername());
                                System.out.println(request.getCreationDate());
                                System.out.println(request.getRequestStatus());
                                System.out.println(request.getResolverUsername());
                            }
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                        case 2:
                            ((Staff) userCLI).solveRequests();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                    }
                    break;
                //Update production/actor info
                case 9:
                    System.out.println("Choose action: ");
                    System.out.println("1. Update production info\n2. Update actor info");
                    int action4 = S.nextInt();
                    S.nextLine();

                    switch (action4) {
                        case 1:
                            ((Staff) userCLI).updateProduction();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                        case 2:
                            ((Staff) userCLI).updateActor();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                    }
                    break;
                //Logout
                case 10:
                    User.logout(userCLI);
                    break;
            }
        }
    }


    public void handleAdminUserAction(int choice, User userCLI)
    {
        Scanner S = new Scanner(System.in);
        boolean wantToContinue = true;

        while (wantToContinue)
        {
            switch (choice)
            {
                //View productions
                case 1:
                    viewProductions();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //View actors
                case 2:
                    viewActors();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //View notifications
                case 3:
                    for (String notification : userCLI.getNotifications()) {
                        System.out.println(notification);
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Search movie/series/actor
                case 4:
                    search();
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Add/Delete production/actor from favorites
                case 5:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add production/actor to favorites\n2. Delete production/actor from favorites");
                    int action = S.nextInt();
                    S.nextLine();

                    switch (action) {
                        case 1 -> userCLI.addToFavorites();
                        case 2 -> userCLI.removeFromFavorites();
                    }
                    choice = wantToDoSomethingElse(userCLI);
                    if (choice == 0) {
                        wantToContinue = false;
                    }
                    break;
                //Add/Remove production/actor
                case 6:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add production/actor\n2. Remove production/actor");
                    int action2 = S.nextInt();
                    S.nextLine();
                    switch (action2) {
                        case 1:
                            System.out.println("Choose to add: ");
                            System.out.println("1. Production\n2. Actor");
                            int add = S.nextInt();
                            S.nextLine();
                            switch (add) {
                                case 1:
                                    ((Staff) userCLI).addProduction();
                                    userCLI.updateExperience(Actions.ADD_NEW, userCLI.getExperience());
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                case 2:
                                    ((Staff) userCLI).addActor();
                                    userCLI.updateExperience(Actions.ADD_NEW, userCLI.getExperience());
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid option!");
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Choose to remove: ");
                            System.out.println("1. Production\n2. Actor");
                            int remove = S.nextInt();
                            S.nextLine();

                            switch (remove) {
                                case 1:
                                    ((Staff) userCLI).removeProduction();
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                case 2:
                                    ((Staff) userCLI).removeActor();
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    choice = wantToDoSomethingElse(userCLI);
                                    if (choice == 0) {
                                        wantToContinue = false;
                                    }
                                    break;
                            }
                            break;
                    }
                    //View/Solve request
                case 7:
                    System.out.println("Choose action: ");
                    System.out.println("1. View requests\n2. Solve request");
                    int action3 = S.nextInt();
                    S.nextLine();

                    switch (action3) {
                        case 1:
                            for (Request request : requests) {
                                System.out.println(request.getRequestType());
                                System.out.println(request.getTitleOrName());
                                System.out.println(request.getDescription());
                                System.out.println(request.getRequesterUsername());
                                System.out.println(request.getCreationDate());
                                System.out.println(request.getRequestStatus());
                                System.out.println(request.getResolverUsername());
                            }
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                        case 2:
                            ((Staff) userCLI).solveRequests();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                    }
                    break;
                //Update production/actor info
                case 8:
                    System.out.println("Choose action: ");
                    System.out.println("1. Update production info\n2. Update actor info");
                    int action4 = S.nextInt();
                    S.nextLine();

                    switch (action4) {
                        case 1:
                            ((Staff) userCLI).updateProduction();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                        case 2:
                            ((Staff) userCLI).updateActor();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                    }
                    break;
                //Add/Remove user
                case 9:
                    System.out.println("Choose action: ");
                    System.out.println("1. Add user\n2. Remove user");
                    int action5 = S.nextInt();
                    S.nextLine();

                    switch (action5) {
                        case 1:
                            Admin.addUser();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                        case 2:
                            Admin.removeUser();
                            choice = wantToDoSomethingElse(userCLI);
                            if (choice == 0) {
                                wantToContinue = false;
                            }
                            break;
                    }
                    break;
                //Logout
                case 10:
                    User.logout(userCLI);
                    break;
            }
        }
    }
}