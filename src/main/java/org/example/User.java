package org.example;


import java.time.LocalDate;
import java.util.*;

public abstract class User implements Comparable<User>, Observer
{
    private Information userInfo;
    private AccountType accountType;
    private String username;
    private String experience;
    private List<String> notifications;
    private SortedSet<String> favorites;
    private SortedSet<String> contributions;

    public User() {
        notifications = new ArrayList<>();
        favorites = new TreeSet<>();
        contributions = new TreeSet<>();
    }

    public User(String username, String password) {
        this();
        userInfo = new Builder(username, password).build();
        accountType = AccountType.REGULAR;
        username = generateUniqueUsername(username);
        experience = "0";
    }

    private String generateUniqueUsername(String name) {
        return name.toLowerCase().replace(" ", "_") + "_2024";
    }

    public void addToFavorites()
    {
        User userCLI = IMDB.getInstance().getUserCLI();
        Scanner S = new Scanner(System.in);
        System.out.println("What do you want to add to favorites?");
        System.out.println("1. Production\n2. Actor");
        int choice = S.nextInt();
        S.nextLine();
        switch (choice)
        {
            case 1 -> {
                System.out.println("Enter production title: ");
                String productionTitle = S.nextLine();
                List<Production> productions = IMDB.getInstance().getProductions();
                boolean found = false;
                if (userCLI.favorites.contains(productionTitle))
                {
                    System.out.println("Already in favorites!");
                }
                else
                {
                    for (Production production : productions)
                    {
                        if (production.getProductionTitle().equalsIgnoreCase(productionTitle))
                        {
                            found = true;
                            userCLI.favorites.add(productionTitle);
                            System.out.println("Added to favorites!");
                        }
                    }
                }
                if (!found)
                {
                    System.out.println("Production not found!");
                }
            }
            case 2 -> {
                System.out.println("Enter actor name: ");
                String actorName = S.nextLine();
                List<Actor> actors = IMDB.getInstance().getActors();
                boolean found = false;
                if (userCLI.favorites.contains(actorName))
                {
                    System.out.println("Already in favorites!");
                }
                else
                {
                    for (Actor actor : actors)
                    {
                        if (actor.getActorName().equalsIgnoreCase(actorName))
                        {
                            found = true;
                            userCLI.favorites.add(actorName);
                            System.out.println("Added to favorites!");
                        }
                    }
                }
                if (!found)
                {
                    System.out.println("Actor not found!");
                }
            }
        }
    }

    public void removeFromFavorites()
    {
        User userCLI = IMDB.getInstance().getUserCLI();
        Scanner S = new Scanner(System.in);
        System.out.println("What do you want to remove from favorites?");
        System.out.println("1. Production\n2. Actor");
        int choice = S.nextInt();
        S.nextLine();
        switch (choice)
        {
            case 1 -> {
                System.out.println("Enter production title: ");
                String productionTitle = S.nextLine();
                List<Production> productions = IMDB.getInstance().getProductions();
                boolean found = false;
                if (userCLI.favorites.contains(productionTitle))
                {
                    userCLI.favorites.remove(productionTitle);
                    System.out.println("Removed from favorites!");
                }
                else
                {
                    for (Production production : productions)
                    {
                        if (production.getProductionTitle().equalsIgnoreCase(productionTitle))
                        {
                            found = true;
                            System.out.println("Production not found in favorites!");
                        }
                    }
                }
                if (!found)
                {
                    System.out.println("Production not found!");
                }
            }
            case 2 -> {
                System.out.println("Enter actor name: ");
                String actorName = S.nextLine();
                List<Actor> actors = IMDB.getInstance().getActors();
                boolean found = false;
                if (userCLI.favorites.contains(actorName))
                {
                    userCLI.favorites.remove(actorName);
                    System.out.println("Removed from favorites!");
                }
                else
                {
                    for (Actor actor : actors)
                    {
                        if (actor.getActorName().equalsIgnoreCase(actorName))
                        {
                            found = true;
                            System.out.println("Actor not found in favorites!");
                        }
                    }
                }
                if (!found)
                {
                    System.out.println("Actor not found!");
                }
            }
        }
    }

    public static void logout(User userCLI)
    {
        Scanner S = new Scanner(System.in);
        System.out.println("Log into another account? (Y/N)");
        String answer = S.nextLine();
        if (answer.equalsIgnoreCase("Y"))
        {
            IMDB.getInstance().run();
        }
        else if (answer.equalsIgnoreCase("N"))
        {
            System.out.println("Goodbye, " + userCLI.getUserInfo().getName() + "!");
            System.exit(0);
        }
        else
        {
            System.out.println("Invalid option. Try again!");
            IMDB.getInstance().wantToDoSomethingElse(userCLI);
        }
    }

    public void updateExperience(IMDB.Actions action, String experience)
    {
        int newExperience = switch (action)
        {
            case ADD_RATING -> new AddRatingExperience().calculateExperience(experience);
            case SUCCESSFUL_REQUEST -> new SuccessfulRequestExperience().calculateExperience(experience);
            case ADD_NEW -> new AddNewExperience().calculateExperience(experience);
        };
    }

    public void update(String notification)
    {
        notifications.add(notification);
    }

    //GETTERS
    public Information getUserInfo() {
        return userInfo;
    }

    public AccountType getAccountType()
    {
        return accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getExperience() {
        return experience;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public SortedSet<String> getFavorites() {
        return favorites;
    }

    public SortedSet<String> getContributions()
    {
        return contributions;
    }


    //SETTERS
    public void setUserInfo(Information userInfo) {
        this.userInfo = userInfo;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setExperience(String experience)
    {
        this.experience = experience;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public void setFavorites(SortedSet<String> favorites) {
        this.favorites = favorites;
    }
    public void setContributions(SortedSet<String> contributions)
    {
        this.contributions = contributions;
    }

    static class Information
    {
        private Credentials credentials;
        private String name;
        private String country;
        private String age;
        private char gender;
        private LocalDate dateOfBirth;

        public Information(Builder b)
        {
            this.credentials = b.credentials;
            this.name = b.name;
            this.country = b.country;
            this.age = b.age;
            this.gender = b.gender;
            this.dateOfBirth = b.dateOfBirth;
        }

        // GETTERS
        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public String getAge() {
            return age;
        }

        public char getGender() {
            return gender;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }


        //SETTERS
        public void setCredentials(Builder b) {
            this.credentials = b.credentials;
        }

        public void setName(Builder b) {
            this.name = b.name;
        }

        public void setCountry(Builder b) {
            this.country = b.country;
        }

        public void setAge(Builder b) {
            this.age = String.valueOf(b.age);
        }

        public void setGender(Builder b) {
            this.gender = b.gender;
        }

        public void setDateOfBirth(Builder b) {
            this.dateOfBirth = b.dateOfBirth;
        }
    }

    public static class Builder
    {
        private Credentials credentials;
        private String name;
        private String country;
        private String age;
        private char gender;
        private LocalDate dateOfBirth;

        public Builder(String email, String password) {
            this.credentials = new Credentials(email, password);
        }

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder country(String country)
        {
            this.country = country;
            return this;
        }

        public Builder age(String age)
        {
            this.age = age;
            return this;
        }

        public Builder gender(char gender)
        {
            this.gender = gender;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth)
        {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Information build()
        {
            return new Information(this);
        }
    }
}