package logic.strategy.implementation;

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
                }
            }
            addHere.addClient(client);
            minTime = addHere.getWaitingPeriod() + client.getServiceTime();
            addHere.setWaitingPeriod(minTime);
        }
    }

}
