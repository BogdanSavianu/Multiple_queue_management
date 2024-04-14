package logic;

import model.Client;
import model.Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Logger {
    private File file;
    private BufferedWriter writer;
    public Logger() {
    }

    public void createFile(String fileName)  {
        file = new File(fileName);

        try {
            file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println("Could not create file " + fileName);
            e.printStackTrace();
        }
    }
    public void closeFile()  {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logSimulation(String fileName, int currentTime, List<Client> generatedClients, List<Server> servers, int numberOfServers) {
        try{
                writer.write("Time: " + currentTime + "\n");
                writer.write("Waiting clients: " + generatedClients + "\n");
                for (int i = 0; i < numberOfServers; i++) {
                    Server server = servers.get(i);
                    writer.write("Server " + (i + 1) + " Clients: " + server.getClients() + " Server time: " + server.getWaitingPeriod() + "\n");
                }
                writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
