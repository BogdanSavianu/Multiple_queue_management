package logic;

import logic.strategy.Strategy;
import logic.strategy.implementation.ShortestTimeStrategy;
import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator {
    Integer maxArrivalTime;
    Integer minProcessingTime;
    Integer maxProcessingTime;

    public Generator(Integer maxArrivalTime, Integer minProcessingTime, Integer maxProcessingTime) {
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
    }
    public List<Server> generateServers(Integer n, Integer nrClients) {
        List<Server> servers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Server server = new Server(nrClients);
            servers.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
        return servers;
    }

    public List<Client> generateNRandomClients(Integer n){
        List<Client> clients = new ArrayList<Client>();
        Random rand = new Random();
        for(int i=0; i<n; i++){
            Client client = new Client();
            client.setId(i+1);
            client.setArrivalTime(rand.nextInt(maxArrivalTime));
            client.setServiceTime(rand.nextInt(maxProcessingTime-minProcessingTime) + minProcessingTime);
            client.setServiceTimeUnmodified(client.getServiceTime());
            clients.add(client);
        }
        Collections.sort(clients);
        return clients;
    }
}
