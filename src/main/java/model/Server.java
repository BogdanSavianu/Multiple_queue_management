package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Client> clients;
    private Integer waitingPeriod;

    public Server(Integer nrMaxPerServer) {
        this.clients = new ArrayBlockingQueue<>(nrMaxPerServer);
        waitingPeriod = 0;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        if (client == null)
            throw new NullPointerException("Can't add null client to queue");
        synchronized (clients) {
            clients.add(client);
        }
    }

    public void setWaitingPeriod(Integer waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    @Override
    public void run() {
        while (true) {
            if (!clients.isEmpty())
                try {
                    System.out.println(clients.size());
                    Client client = clients.peek();
                    int newServiceTime = client.getServiceTime() - 1;
                    client.setServiceTime(newServiceTime);
                    waitingPeriod -= 1;
                    if (newServiceTime == 0) {
                        System.out.println("Client completed service: " + client);
                        clients.poll();
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
