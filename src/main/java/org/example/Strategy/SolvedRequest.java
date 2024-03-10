package org.example.Strategy;

import org.example.Interfaces.ExperienceStrategy;

public class SolvedRequest implements ExperienceStrategy {
        private int experience;
        public void addexp() {
            this.experience=experience+10;
        }
        public void removeexp() {
            this.experience=experience-10;
        }
        public int calculateExperience() {
            int exp=experience;
            experience=0;
            return exp;
        }
}
