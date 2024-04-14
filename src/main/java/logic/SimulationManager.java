package logic;

import gui.QueueContent;
import gui.StartSimulation;
import logic.strategy.SelectionPolicy;
import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationManager implements Runnable{
    private volatile boolean isRunning = true;
    public Integer timeLimit;
    public Integer maxProcessingTime;
    public Integer minProcessingTime;
    public Integer numberOfServers;
    public Integer nrMaxPerServer;
    public static Integer numberOfClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private Generator generator;
    private Logger logger;
    private StartSimulation frame;
    private QueueContent queueContent;
    private List<Client> generatedClients;
    private List<Server> servers;
    public static Integer avgWaitingTime;

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
        logger = new Logger();
        generatedClients = new ArrayList<Client>();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Generator getGenerator() {
        return generator;
    }

    public List<Client> getGeneratedClients() {
        return generatedClients;
    }

    public void setGeneratedClients(List<Client> generatedClients) {
        this.generatedClients = generatedClients;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    private void stopServers() {
        for (Server server : servers) {
            server.stopServer();
        }
    }
    public void setQueueContent(QueueContent queueContent) {
        this.queueContent = queueContent;
    }
    public void stopSimulation() {
        isRunning = false;
    }

    @Override
    public void run() {
        logger.createFile("/Users/bogdansavianu/University/Year2/Sem2/Programming_Techniques/Assignment2/log.txt");
        TimeCalculator.calculateServiceTime(generatedClients);
        for (int currentTime = 0; currentTime <= timeLimit; currentTime++) {
            Iterator<Client> iterator = generatedClients.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    this.scheduler.dispatchClient(client);
                    iterator.remove();
                }
            }

            System.out.println("Time: " + currentTime);
            System.out.println("Waiting clients: " + generatedClients);
            for (int i = 0; i < numberOfServers; i++) {
                Server server = this.getServers().get(i);
                System.out.println("Server " + (i + 1) + " Clients: " + server.getClients() + "server time: " + server.getWaitingPeriod());
            }
            queueContent.updateQueueContent(servers, generatedClients, currentTime);
            logger.logSimulation("/Users/bogdansavianu/University/Year2/Sem2/Programming_Techniques/Assignment2/log.txt", currentTime, generatedClients, servers, numberOfServers);
            System.out.println();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queueContent.showAverageTimes();
        stopServers();
        stopSimulation();
        logger.closeFile();
    }
}