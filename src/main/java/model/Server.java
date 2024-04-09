package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;

    public Server(Integer nrMaxPerServer) {
        this.clients = new ArrayBlockingQueue<>(nrMaxPerServer);
        waitingPeriod = new AtomicInteger(0);
    }

    public Server(BlockingQueue<Client> clients, AtomicInteger waitingPeriod) {
        this.clients = clients;
        this.waitingPeriod = waitingPeriod;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayBlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public synchronized void addClient(Client client) {
        if (client == null)
            throw new NullPointerException("Can't add null client to queue");
        synchronized (client) {
            clients.add(client);
            waitingPeriod.getAndAdd(client.getServiceTime());
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!clients.isEmpty())
                try {
                    Client client = clients.peek();
                    int newServiceTime = client.getServiceTime() - 1;
                    client.setServiceTime(newServiceTime);
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
