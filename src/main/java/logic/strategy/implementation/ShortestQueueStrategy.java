package logic.strategy.implementation;

import logic.strategy.Strategy;
import model.Client;
import model.Server;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client client) {
        Server addHere = servers.get(0);
        for(Server server : servers) {
            if(server.getClients().size() > addHere.getClients().size())
                addHere = server;
        }
        addHere.getClients().add(client);
    }
}
