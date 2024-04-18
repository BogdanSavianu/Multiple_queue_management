package gui;

import logic.SimulationManager;
import logic.TimeCalculator;
import model.Client;
import model.Server;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QueueContent extends JFrame {

    private JPanel mainPanel;
    private JTextArea clientTextArea;
    private JPanel serverPanel;
    private JProgressBar[] serverProgressBars;
    private JTextArea[] serverClientTextAreas;

    public QueueContent(List<Server> servers, List<Client> clients) {
        setTitle("Simulation GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        clientTextArea = new JTextArea(10, 20);
        JScrollPane clientScrollPane = new JScrollPane(clientTextArea);
        mainPanel.add(clientScrollPane, BorderLayout.WEST);

        serverPanel = new JPanel();
        serverPanel.setLayout(new GridLayout(servers.size(), 2));

        serverProgressBars = new JProgressBar[servers.size()];
        serverClientTextAreas = new JTextArea[servers.size()];
        for (int i = 0; i < servers.size(); i++) {
            serverProgressBars[i] = new JProgressBar(0, 100);
            serverProgressBars[i].setStringPainted(true);
            serverPanel.add(serverProgressBars[i]);

            serverClientTextAreas[i] = new JTextArea(10, 10);
            JScrollPane serverClientScrollPane = new JScrollPane(serverClientTextAreas[i]);
            serverPanel.add(serverClientScrollPane);
        }
        mainPanel.add(serverPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    public void updateQueueContent(List<Server> servers, List<Client> generatedClients, int currentTime) {
        clientTextArea.setText("Generated Clients at Time " + currentTime + ":\n");
        for (Client client : generatedClients) {
            clientTextArea.append(client.toString() + "\n");
        }

        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            int percentage = 0;
            if (!server.getClients().isEmpty())
                percentage = (int) (100 - (double) server.getClients().peek().getServiceTime() / server.getClients().peek().getServiceTimeUnmodified() * 100);
            serverProgressBars[i].setValue(percentage);
            serverProgressBars[i].setString("Server " + (i + 1) + ": " + server.getClients().size() + " clients"); // Display client count

            StringBuilder clientsInQueue = new StringBuilder("Clients in Queue:\n");
            for (Client client : server.getClients()) {
                clientsInQueue.append(client.toString()).append("\n");
            }
            serverClientTextAreas[i].setText(clientsInQueue.toString());
        }
    }

    public void showAverageTimesAndPeakHour() {
        JLabel averageWaitingTimeLabel = new JLabel();
        JLabel averageServiceTimeLabel = new JLabel();
        JLabel peakHourLabel = new JLabel();

        averageWaitingTimeLabel.setText("Average Waiting Time: " + (double) TimeCalculator.avgWaitingTime / SimulationManager.numberOfClients);
        averageServiceTimeLabel.setText("Average Service Time: " + (double) TimeCalculator.avgServiceTime);
        peakHourLabel.setText("Peak Hour: " + TimeCalculator.peakHour);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(averageWaitingTimeLabel);
        panel.add(averageServiceTimeLabel);
        panel.add(peakHourLabel);

        JOptionPane.showMessageDialog(this, panel, "Average Times and Peak Hour", JOptionPane.INFORMATION_MESSAGE);
    }

}
