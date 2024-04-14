package logic.strategy.implementation;

import logic.TimeCalculator;
import logic.strategy.Strategy;
import model.Client;
import model.Server;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client client) {
        synchronized (client) {
            Server addHere = servers.getFirst();
            Integer time;
            for (Server server : servers) {
                if (server.getClients().size() < addHere.getClients().size()) {
                    addHere = server;
                }
            }
            time = addHere.getWaitingPeriod() + client.getServiceTime();
            addHere.addClient(client);
            addHere.setWaitingPeriod(time);
            TimeCalculator.calculateWaitingTime(addHere,client);
        }
    }
}
