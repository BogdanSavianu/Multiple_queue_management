package logic;

import logic.strategy.SelectionPolicy;
import logic.strategy.Strategy;
import logic.strategy.implementation.ShortestQueueStrategy;
import logic.strategy.implementation.ShortestTimeStrategy;
import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private Integer maxNoOfServers;
    private Integer nrMaxPerServer;
    private Strategy strategy = new ShortestTimeStrategy();

    public Scheduler() {
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ShortestQueueStrategy();
        else strategy = new ShortestTimeStrategy();
    }

    public void dispatchClient(Client client) {
        if (client == null)
            return;
        List<Server> minList = new ArrayList<>();
        int minTime = servers.getFirst().getWaitingPeriod();
        minList.add(servers.getFirst());
        for (Server server : servers) {
            if(server.getWaitingPeriod() == minTime) {
                minList.add(server);
            }
            else if(server.getWaitingPeriod() < minTime) {
                {
                    minList = new ArrayList<>();
                    minList.add(server);
                }
            }
        }
        if(minList.size() > 1){
            changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
            strategy.addClient(minList,client);
        }
        else{
            changeStrategy(SelectionPolicy.SHORTEST_TIME);
            strategy.addClient(servers,client);
        }
    }
}
