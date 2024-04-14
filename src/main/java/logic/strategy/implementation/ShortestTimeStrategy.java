package logic.strategy.implementation;

import logic.SimulationManager;
import logic.TimeCalculator;
import logic.strategy.Strategy;
import model.Client;
import model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestTimeStrategy implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client client) {
        synchronized (client) {
            Server addHere = servers.getFirst();
            Integer minTime = servers.getFirst().getWaitingPeriod();
            for (Server server : servers) {
                if (server.getWaitingPeriod() < minTime) {
                    addHere = server;
                    minTime = server.getWaitingPeriod();
                }
            }
            minTime = addHere.getWaitingPeriod() + client.getServiceTime();
            addHere.addClient(client);
            addHere.setWaitingPeriod(minTime);
            TimeCalculator.calculateWaitingTime(addHere,client);
        }
    }

}
