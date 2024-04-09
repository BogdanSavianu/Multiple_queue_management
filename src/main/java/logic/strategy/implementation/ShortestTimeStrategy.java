package logic.strategy.implementation;

import logic.strategy.Strategy;
import model.Client;
import model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestTimeStrategy implements Strategy {
    @Override
    public synchronized void addClient(List<Server> servers, Client client) {
        synchronized (servers) {
            Server addHere = servers.get(0);
            AtomicInteger minTime = servers.get(0).getWaitingPeriod();
            for (Server server : servers) {
                if (server.getWaitingPeriod().get() < minTime.get()) {
                    addHere = server;
                    minTime.set(server.getWaitingPeriod().get());
                }
            }
            addHere.addClient(client);
        }
    }

}
