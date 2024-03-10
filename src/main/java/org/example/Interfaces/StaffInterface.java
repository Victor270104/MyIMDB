package org.example.Interfaces;

import org.example.Classes.Actor;
import org.example.Classes.Production;
import org.example.Classes.Request;

public interface StaffInterface {
    public void addProductionSystem();
    public void removeProductionSystem();
    public void addActorSystem();
    public void removeActorSystem();
    public void updateProduction();
    public void updateActor();
    public void SolveRequest(Request request);
}
