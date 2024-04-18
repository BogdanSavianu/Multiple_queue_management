package gui;

import logic.Logger;
import logic.SimulationManager;
import model.Client;
import model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class StartSimulation extends JFrame {

    private JTextField timeLimitField;
    private JTextField maxProcessingTimeField;
    private JTextField numberOfServersField;
    private JTextField numberOfClientsField;
    private JButton startButton;

    public StartSimulation() {
        setTitle("Simulation Interface");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Time Limit:"));
        timeLimitField = new JTextField();
        panel.add(timeLimitField);

        panel.add(new JLabel("Max Processing Time:"));
        maxProcessingTimeField = new JTextField();
        panel.add(maxProcessingTimeField);

        panel.add(new JLabel("Number of Servers:"));
        numberOfServersField = new JTextField();
        panel.add(numberOfServersField);

        panel.add(new JLabel("Number of Clients:"));
        numberOfClientsField = new JTextField();
        panel.add(numberOfClientsField);

        startButton = new JButton("Start Simulation");
        panel.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        add(panel);
        setVisible(true);
    }


    private void startSimulation() {
        int timeLimit = Integer.parseInt(timeLimitField.getText());
        int maxProcessingTime = Integer.parseInt(maxProcessingTimeField.getText());
        int numberOfServers = Integer.parseInt(numberOfServersField.getText());
        int numberOfClients = Integer.parseInt(numberOfClientsField.getText());

        SimulationManager simulationManager = new SimulationManager(timeLimit, maxProcessingTime, 1, numberOfServers, numberOfClients, numberOfClients);
        List<Client> generatedClients = simulationManager.getGenerator().generateNRandomClients(numberOfClients);
        simulationManager.setServers(simulationManager.getGenerator().generateServers(numberOfServers, numberOfClients));
        simulationManager.getScheduler().setServers(simulationManager.getServers());
        simulationManager.setGeneratedClients(generatedClients);
        QueueContent queueContentFrame = new QueueContent(simulationManager.getServers(), simulationManager.getGeneratedClients());
        simulationManager.setQueueContent(queueContentFrame);
        Thread t = new Thread(simulationManager);
        t.start();
    }


    public static void main(String[] args) {
        new StartSimulation();
    }
}