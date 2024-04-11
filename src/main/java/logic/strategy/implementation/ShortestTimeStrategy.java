package logic.strategy.implementation;

import logic.strategy.Strategy;
import model.Client;
import model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShortestTimeStrategy implements Strategy {
    @Override
    public synchronized void addClient(List<Server> servers, Client client) {
        synchronized (client) {
            Server addHere = servers.getFirst();
            AtomicInteger minTime = servers.getFirst().getWaitingPeriod();
            for (Server server : servers) {
                if (server.getWaitingPeriod().get() < minTime.get()) {
                    addHere = server;
                }
            }
            addHere.addClient(client);
            minTime.set(addHere.getWaitingPeriod().get() + client.getServiceTime());
            addHere.setWaitingPeriod(minTime);
        }
    }

}
