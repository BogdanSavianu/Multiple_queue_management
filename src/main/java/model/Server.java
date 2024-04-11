package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;

    public Server(Integer nrMaxPerServer) {
        this.clients = new ArrayBlockingQueue<>(nrMaxPerServer);
        waitingPeriod = new AtomicInteger(0);
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public synchronized void addClient(Client client) {
        if (client == null)
            throw new NullPointerException("Can't add null client to queue");
        clients.add(client);
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (clients) {
            if (!clients.isEmpty())
                try {
                    Client client = clients.peek();
                    int newServiceTime = client.getServiceTime() - 1;
                    client.setServiceTime(newServiceTime);
                    waitingPeriod.getAndAdd(-1);
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
}
