package logic;

import logic.strategy.SelectionPolicy;
import logic.strategy.Strategy;
import logic.strategy.implementation.ShortestQueueStrategy;
import logic.strategy.implementation.ShortestTimeStrategy;
import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.List;

public class Scheduler{
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

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Integer getMaxNoOfServers() {
        return maxNoOfServers;
    }

    public void setMaxNoOfServers(Integer maxNoOfServers) {
        this.maxNoOfServers = maxNoOfServers;
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ShortestQueueStrategy();
        else strategy = new ShortestTimeStrategy();
    }
    public void dispatchClient(Client client){
        if(client == null)
            return;
        synchronized (client) {
            strategy.addClient(servers, client);
        }
    }
}
