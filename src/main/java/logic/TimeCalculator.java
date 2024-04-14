package logic;

import model.Client;
import model.Server;

import java.util.List;

public class TimeCalculator {
    public static Integer avgWaitingTime = 0;
    public static Double avgServiceTime = 0.0;
    public static Integer peakHour = -1;
    public static Integer peakClientCout = -1;

    public TimeCalculator() {
        avgWaitingTime = 0;
        avgServiceTime = 0.0;
    }

    public static void calculateWaitingTime(Server server, Client client) {
        Integer time = 0;
        for(Client c : server.getClients()) {
            if(c.compareTo(client) == 0) {
                break;
            }
            time += c.getServiceTime();
        }
        avgWaitingTime += time;
    }
    public static void calculateServiceTime(List<Client> clients) {
        for(Client client : clients) {
            avgServiceTime += client.getServiceTime().doubleValue();
        }
        avgServiceTime /= clients.size();
    }
    public static void calculatePeakHour(List<Server> servers, int currentTime) {
        Integer nrClients = 0;
        for(Server server : servers) {
            nrClients += server.getClients().size();
        }
        if(nrClients > peakClientCout) {
            peakHour = currentTime;
            peakClientCout = nrClients;
        }
    }
}
