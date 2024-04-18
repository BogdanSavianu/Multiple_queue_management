package model;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Client> clients;
    private Integer waitingPeriod;
    private volatile boolean isRunning = true;

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

    public void stopServer() {
        isRunning = false;
    }


    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (!clients.isEmpty()) {
                System.out.println();
                synchronized (clients) {
                    Client client = clients.peek();
                    int newServiceTime = client.getServiceTime() - 1;
                    client.setServiceTime(newServiceTime);
                    waitingPeriod -= 1;
                    if (newServiceTime == 0) {
                        System.out.println("Client completed service: " + client);
                        clients.poll();
                    }
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
        synchronized (this) {
            while (isRunning) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
    }


    @Override
    public String toString() {
        return "Server{" +
                "clients=" + clients +
                ", waitingPeriod=" + waitingPeriod +
                '}';
    }
}
