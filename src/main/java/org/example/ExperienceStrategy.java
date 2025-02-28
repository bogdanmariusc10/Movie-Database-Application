package org.example;

public interface ExperienceStrategy {
    public int calculateExperience(String exp);
}

class AddRatingExperience implements ExperienceStrategy
{
    @Override
    public int calculateExperience(String exp) {
        int experience = Integer.parseInt(exp);
        return experience + 5;
    }
}

class SuccessfulRequestExperience implements ExperienceStrategy
{
    @Override
    public int calculateExperience(String exp) {
        int experience = Integer.parseInt(exp);
        return experience + 20;
    }
}

class AddNewExperience implements ExperienceStrategy
{
    @Override
    public int calculateExperience(String exp) {
        int experience = Integer.parseInt(exp);
        return experience + 10;
    }
}
