package org.example;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

public class Regular extends User implements RequestsManager {
    private List<Request> requests;

    public Regular() {
        super();
        requests = new ArrayList<>();
    }

    @Override
    public void createRequest() {
        User userCLI = IMDB.getInstance().getUserCLI();
        Scanner S = new Scanner(System.in);
        System.out.println("Choose request type: ");
        System.out.println("1. Delete account\n2. Actor issue\n3. Movie issue\n4. Others");
        int action = S.nextInt();
        switch (action) {
            case 1 -> {
                LocalDateTime creationDate = LocalDateTime.now();
                String titleOrName = "";
                System.out.println("Why do you want to delete your account?");
                String description = S.nextLine();
                String requesterUsername = userCLI.getUsername();
                String resolverUsername = "ADMIN";
                Request newRequest = new Request(RequestTypes.DELETE_ACCOUNT, creationDate, titleOrName, description, requesterUsername, resolverUsername);
                requests.add(newRequest);
                RequestHolder.addRequest(newRequest);
                newRequest.pendingRequest();
                System.out.println("Your request has been sent and pending for approval.");

                List<Observer> observers = new ArrayList<>();
                List<Admin> admins = IMDB.getInstance().getAdmins();
                for (Admin admin : admins) {
                    if (admin.getUsername().equals(requesterUsername)) {
                        observers.add(admin);
                    }
                }
                Subject subject = new Subject();
                for (Observer observer : observers) {
                    subject.addObserver(observer);
                }
                subject.notifyObservers("You have received a request.", observers);
            }
            case 2 -> {
                LocalDateTime creationDate = LocalDateTime.now();
                System.out.println("Enter actor name: ");
                String titleOrName = S.nextLine();
                S.nextLine();
                System.out.println("What is the issue?");
                String description = S.nextLine();
                String requesterUsername = userCLI.getUsername();
                String resolverUsername = null;
                List<Contributor> contributors = IMDB.getInstance().getContributors();
                List<Admin> admins = IMDB.getInstance().getAdmins();
                SortedSet<String> contributions = new TreeSet<>();
                List<Observer> observers = new ArrayList<>();
                for (Contributor contributor : contributors) {
                    contributions = getContributions();
                    for (String contribution : contributions) {
                        if (contribution.equals(titleOrName)) {
                            resolverUsername = getUsername();
                            observers.add(contributor);
                        }
                    }
                }
                for (Admin admin : admins) {
                    contributions = getContributions();
                    for (String contribution : contributions) {
                        if (contribution.equals(titleOrName)) {
                            resolverUsername = getUsername();
                            observers.add(admin);
                        }
                    }
                }
                Request newRequest = new Request(RequestTypes.ACTOR_ISSUE, creationDate, titleOrName, description, requesterUsername, resolverUsername);
                requests.add(newRequest);
                newRequest.pendingRequest();
                System.out.println("Your request has been sent and pending for approval.");

                Subject subject = new Subject();
                for (Observer observer : observers) {
                    subject.addObserver(observer);
                }
                subject.notifyObservers("You have received a request.", observers);
            }
            case 3 -> {
                LocalDateTime creationDate = LocalDateTime.now();
                System.out.println("Enter movie name: ");
                String titleOrName = S.nextLine();
                S.nextLine();
                System.out.println("What is the issue?");
                String description = S.nextLine();
                String requesterUsername = userCLI.getUsername();
                String resolverUsername = null;
                List<Contributor> contributors = IMDB.getInstance().getContributors();
                List<Admin> admins = IMDB.getInstance().getAdmins();
                SortedSet<String> contributions = new TreeSet<>();
                List<Observer> observers = new ArrayList<>();
                for (Contributor contributor : contributors) {
                    contributions = getContributions();
                    for (String contribution : contributions) {
                        if (contribution.equals(titleOrName)) {
                            resolverUsername = getUsername();
                            observers.add(contributor);
                        }
                    }
                }
                for (Admin admin : admins) {
                    contributions = getContributions();
                    for (String contribution : contributions) {
                        if (contribution.equals(titleOrName)) {
                            resolverUsername = getUsername();
                            observers.add(admin);
                        }
                    }
                }
                Request newRequest = new Request(RequestTypes.ACTOR_ISSUE, creationDate, titleOrName, description, requesterUsername, resolverUsername);
                requests.add(newRequest);
                newRequest.pendingRequest();
                System.out.println("Your request has been sent and pending for approval.");

                Subject subject = new Subject();
                for (Observer observer : observers) {
                    subject.addObserver(observer);
                }
                subject.notifyObservers("You have received a request.", observers);
            }
            case 4 -> {
                LocalDateTime creationDate = LocalDateTime.now();
                String titleOrName = "";
                System.out.println("What is the issue?");
                String description = S.nextLine();
                String requesterUsername = userCLI.getUsername();
                String resolverUsername = "ADMIN";
                Request newRequest = new Request(RequestTypes.OTHERS, creationDate, titleOrName, description, requesterUsername, resolverUsername);
                requests.add(newRequest);
                RequestHolder.addRequest(newRequest);
                newRequest.pendingRequest();
                System.out.println("Your request has been sent and pending for approval.");

                List<Observer> observers = new ArrayList<>();
                List<Admin> admins = IMDB.getInstance().getAdmins();
                for (Admin admin : admins) {
                    if (admin.getUsername().equals(requesterUsername)) {
                        observers.add(admin);
                    }
                }
                Subject subject = new Subject();
                for (Observer observer : observers) {
                    subject.addObserver(observer);
                }
                subject.notifyObservers("You have received a request.", observers);
            }
        }
    }

    @Override
    public void removeRequest() {
        Scanner S = new Scanner(System.in);
        if (requests.isEmpty()) {
            System.out.println("No requests to be withdrawn.");
        } else {
            for (int i = 1; i <= requests.size(); i++) {
                System.out.println(i + ". " + requests.get(i - 1));
            }
            System.out.println("Choose a request to be withdrawn: ");
            int requestNumber = S.nextInt();
            S.nextLine();
            requests.remove(requestNumber);
            RequestHolder.removeRequest(requests.get(requestNumber));
            System.out.println("Request withdrawn.");
        }
    }

    public void addRating() {
        User userCLI = IMDB.getInstance().getUserCLI();
        Scanner S = new Scanner(System.in);
        String username = userCLI.getUsername();
        System.out.println("Enter production name: ");
        String productionTitle = S.nextLine();
        List<Production> productions = IMDB.getInstance().getProductions();

        for (Production production : productions) {
            if (production.getProductionTitle().equalsIgnoreCase(productionTitle)) {
                System.out.println("Enter rating: ");
                int rating = S.nextInt();
                S.nextLine();
                System.out.println("Enter comment: ");
                String comment = S.nextLine();
                Rating newRating = new Rating(username, rating, comment);
                List<Rating> ratings = production.getRatingList();
                ratings.add(newRating);

                List<Observer> observers = new ArrayList<>();
                List<Regular> regulars = IMDB.getInstance().getRegulars();
                for (Regular regular : regulars) {
                    if (newRating.getUsername().equals(username)) {
                        observers.add(regular);
                    }
                }
                Subject subject = new Subject();
                for (Observer observer : observers) {
                    subject.addObserver(observer);
                }
                subject.notifyObservers("User " + username + " added a new rating to '" + productionTitle + "'", observers);

                List<Contributor> contributors = IMDB.getInstance().getContributors();
                List<Admin> admins = IMDB.getInstance().getAdmins();
                SortedSet<String> contributions = new TreeSet<>();
                List<Observer> observers1 = new ArrayList<>();
                for (Contributor contributor : contributors) {
                    contributions = getContributions();
                    for (String contribution : contributions) {
                        if (contribution.equals(productionTitle)) {
                            observers1.add(contributor);
                        }
                    }
                }
                for (Admin admin : admins) {
                    contributions = getContributions();
                    for (String contribution : contributions) {
                        if (contribution.equals(productionTitle)) {
                            observers1.add(admin);
                        }
                    }
                }
                Subject subject1 = new Subject();
                for (Observer observer2 : observers) {
                    subject1.addObserver(observer2);
                }
                subject1.notifyObservers("User " + username + " has rated a production you added: '" + productionTitle + "'", observers);
            } else {
                System.out.println("Production not found.");
                break;
            }
        }
    }

    public void removeRating() {
        User userCLI = IMDB.getInstance().getUserCLI();
        Scanner S = new Scanner(System.in);
        String username = userCLI.getUsername();
        System.out.println("Enter production name: ");
        String productionName = S.nextLine();
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production production : productions) {
            if (productionName.equalsIgnoreCase(production.getProductionType())) {
                List<Rating> ratings = production.getRatingList();
                for (Rating rating : ratings) {
                    if (username.equalsIgnoreCase(rating.getUsername())) {
                        ratings.remove(rating);
                    } else {
                        System.out.println("Rating not found.");
                    }
                    break;
                }
            } else {
                System.out.println("Production not found.");
            }
            break;
        }
    }

    @Override
    public int compareTo(@NotNull User o) {
        return 0;
    }
}
