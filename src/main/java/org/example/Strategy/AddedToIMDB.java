package org.example.Strategy;

import org.example.Interfaces.ExperienceStrategy;

public class AddedToIMDB implements ExperienceStrategy {
    private int experience;
    public void addexp()
    {
        this.experience=experience+20;
    }
    public void removeexp()
    {
        this.experience=experience-20;
    }
    public int calculateExperience() {
        int exp=experience;
        experience=0;
        return exp;
    }
}
