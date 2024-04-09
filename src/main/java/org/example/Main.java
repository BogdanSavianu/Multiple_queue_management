package org.example;

import logic.SimulationManager;
import model.Client;
import model.Server;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int timeLimit = 100;
        int maxProcessingTime = 10;
        int minProcessingTime = 1;
        int numberOfServers = 3;
        int numberOfClients = 40;
        SimulationManager simulationManager = new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients, numberOfClients);
        List<Client> generatedClients = simulationManager.getGenerator().generateNRandomClients(numberOfClients);
        simulationManager.setGeneratedClients(generatedClients);
        for (int currentTime = 0; currentTime <= timeLimit; currentTime++) {
            for (Client client: generatedClients) {
                if (client.getArrivalTime() == currentTime) {
                    simulationManager.getScheduler().dispatchClient(client);
                }
            }
            System.out.println("Time: " + currentTime);
            for (int i = 0; i < numberOfServers; i++) {
                Server server = simulationManager.getScheduler().getServers().get(i);
                System.out.println("Server " + (i + 1) + " Clients: " + server.getClients() + "server time: " + server.getWaitingPeriod().get());
            }
            System.out.println();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
