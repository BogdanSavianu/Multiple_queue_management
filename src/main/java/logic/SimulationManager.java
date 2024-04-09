package logic;

import gui.SimulationFrame;
import logic.strategy.SelectionPolicy;
import logic.strategy.Strategy;
import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager implements Runnable{
    public Integer timeLimit;
    public Integer maxProcessingTime;
    public Integer minProcessingTime;
    public Integer numberOfServers;
    public Integer nrMaxPerServer;
    public Integer numberOfClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private Generator generator;
    private SimulationFrame frame;
    private List<Client> generatedClients;
    private List<Server> servers;
    private Integer nrClients;

    public SimulationManager() {
        generator = new Generator(timeLimit-maxProcessingTime,minProcessingTime,maxProcessingTime);
        scheduler = new Scheduler();
        generatedClients = new ArrayList<Client>();
    }

    public SimulationManager(Integer timeLimit, Integer maxProcessingTime, Integer minProcessingTime, Integer numberOfServers, Integer nrMaxPerServer, Integer numberOfClients) {
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        generator = new Generator(timeLimit-maxProcessingTime,minProcessingTime,maxProcessingTime);
        scheduler = new Scheduler();
        generatedClients = new ArrayList<Client>();
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getMaxProcessingTime() {
        return maxProcessingTime;
    }

    public void setMaxProcessingTime(Integer maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public Integer getMinProcessingTime() {
        return minProcessingTime;
    }

    public void setMinProcessingTime(Integer minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }

    public Integer getNumberOfServers() {
        return numberOfServers;
    }

    public void setNumberOfServers(Integer numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public Integer getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(Integer numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public SimulationFrame getFrame() {
        return frame;
    }

    public void setFrame(SimulationFrame frame) {
        this.frame = frame;
    }

    public List<Client> getGeneratedClients() {
        return generatedClients;
    }

    public void setGeneratedClients(List<Client> generatedClients) {
        this.generatedClients = generatedClients;
    }

    public Integer getNrClients() {
        return nrClients;
    }

    public void setNrClients(Integer nrClients) {
        this.nrClients = nrClients;
    }

    public Integer getNrMaxPerServer() {
        return nrMaxPerServer;
    }

    public void setNrMaxPerServer(Integer nrMaxPerServer) {
        this.nrMaxPerServer = nrMaxPerServer;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    @Override
    public synchronized void run() {
        for (int currentTime = 0; currentTime <= timeLimit; currentTime++) {
            for (Client client : generatedClients) {
                if (client.getArrivalTime() == currentTime) {
                    this.getScheduler().dispatchClient(client);
                }
            }
            System.out.println("Time: " + currentTime);
            for (int i = 0; i < numberOfServers; i++) {
                Server server = this.getServers().get(i);
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

    public static void main(String[] args) {
        int timeLimit = 100;
        int maxProcessingTime = 10;
        int minProcessingTime = 1;
        int numberOfServers = 3;
        int numberOfClients = 40;
        SimulationManager simulationManager = new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime, numberOfServers, numberOfClients, numberOfClients);
        List<Client> generatedClients = simulationManager.getGenerator().generateNRandomClients(numberOfClients);
        simulationManager.setServers(simulationManager.getGenerator().generateServers(numberOfServers));
        simulationManager.scheduler.setServers(simulationManager.getServers());
        simulationManager.setGeneratedClients(generatedClients);
        Thread t = new Thread(simulationManager);
        t.start();
    }
}
