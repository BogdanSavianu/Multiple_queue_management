package logic;

import model.Client;
import model.Server;

import java.util.List;

public class TimeCalculator {
    public static Integer avgWaitingTime = 0;
    public static Double avgServiceTime = 0.0;

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
}
