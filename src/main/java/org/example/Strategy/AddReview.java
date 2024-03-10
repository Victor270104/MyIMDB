package org.example.Strategy;

import org.example.Interfaces.ExperienceStrategy;

public class AddReview implements ExperienceStrategy {
    private int experience;
    public void addexp()
    {
        experience=experience+3;
    }
    public void removeexp()
    {
        experience=experience-3;
    }
    public int calculateExperience() {
        int exp=experience;
        experience=0;
        return exp;
    }
}
