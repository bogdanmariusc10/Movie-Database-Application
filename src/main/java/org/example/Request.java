package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Request extends Subject
{
    private RequestTypes requestType;
    private LocalDateTime creationDate;
    private String titleOrName;
    private String description;
    private String requesterUsername;
    private String resolverUsername;
    private String requestStatus;
    private List<Observer> observers = new ArrayList<>();

    enum RequestStatus
    {
        PENDING,
        APPROVED,
        REJECTED
    }

    public Request(RequestTypes requestType, LocalDateTime creationDate, String titleOrName, String description,
                   String requesterUsername, String resolverUsername)
    {
        this.requestType = requestType;
        this.creationDate = LocalDateTime.now();
        this.titleOrName = titleOrName;
        this.description = description;
        this.requesterUsername = requesterUsername;

        if (requestType == RequestTypes.DELETE_ACCOUNT || requestType == RequestTypes.OTHERS)
        {
            this.resolverUsername = "ADMIN";
        }
        else
        {
            this.resolverUsername = requesterUsername;
        }
    }

    public void approveRequest()
    {
        requestStatus = String.valueOf(RequestStatus.valueOf("APPROVED"));
        setRequestStatus(requestStatus);
    }

    public void rejectRequest()
    {
        requestStatus = String.valueOf(RequestStatus.valueOf("REJECTED"));
        setRequestStatus(requestStatus);
    }

    public void pendingRequest()
    {
        requestStatus = String.valueOf(RequestStatus.valueOf("PENDING"));
        setRequestStatus(requestStatus);
    }

    //GETTERS
    public RequestTypes getRequestType()
    {
        return requestType;
    }

    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    public String getTitleOrName()
    {
        return titleOrName;
    }

    public String getDescription()
    {
        return description;
    }

    public String getRequesterUsername()
    {
        return requesterUsername;
    }

    public String getResolverUsername()
    {
        return resolverUsername;
    }

    //SETTERS
    public void setRequestType(RequestTypes requestType)
    {
        this.requestType = requestType;
    }

    public void setCreationDate(LocalDateTime creationDate)
    {
        this.creationDate = creationDate;
    }

    public void setTitleOrName(String titleOrName)
    {
        this.titleOrName = titleOrName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setRequesterUsername(String requesterUsername)
    {
        this.requesterUsername = requesterUsername;
    }

    public void setResolverUsername(String resolverUsername)
    {
        this.resolverUsername = resolverUsername;
    }

    public String getRequestStatus()
    {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus)
    {
        this.requestStatus = requestStatus;
        String username = getRequesterUsername();
        List<Observer> observers = new ArrayList<>();
        List<Regular> regulars = IMDB.getInstance().getRegulars();
        List<Contributor> contributors = IMDB.getInstance().getContributors();
        for (Regular regular : regulars)
        {
            if (regular.getUsername().equals(username))
            {
                observers.add(regular);
            }
        }
        for (Contributor contributor : contributors)
        {
            if(contributor.getUsername().equals(username))
            {
                observers.add(contributor);
            }
        }
        Subject subject = new Subject();
        for(Observer observer : observers)
        {
            subject.addObserver(observer);
        }
        subject.notifyObservers("Request status changed to: " + requestStatus, observers);
    }

    public void toString(Request request)
    {
        System.out.println("Request type: " + getRequestType());
        System.out.println("Creation date: " + getCreationDate());
        System.out.println("Title or name: " + getTitleOrName());
        System.out.println("Description: " + getDescription());
        System.out.println("Requester username: " + getRequesterUsername());
        System.out.println("Resolver username: " + getResolverUsername());
        System.out.println("Request status: " + getRequestStatus());
    }
}
