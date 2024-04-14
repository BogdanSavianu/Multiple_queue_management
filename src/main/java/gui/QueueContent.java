package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import logic.SimulationManager;
import logic.TimeCalculator;
import model.Client;
import model.Server;

public class QueueContent extends JFrame {

    private JPanel serverPanel;
    private List<JLabel> serverLabels;
    private List<JProgressBar> progressBars;
    private JLabel timeLabel;
    private JLabel clientsLabel;

    public QueueContent(List<Server> servers, List<Client> clients) {
        setTitle("Queue Content");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();

        serverPanel = new JPanel();
        serverPanel.setLayout(new GridLayout(0, 1));
        timeLabel = new JLabel();
        clientsLabel = new JLabel();
        serverLabels = new ArrayList<>();
        progressBars = new ArrayList<>();
        serverPanel.add(timeLabel);
        serverPanel.add(clientsLabel);

        for (int i = 0; i < servers.size(); i++) {
            JLabel label = new JLabel();
            JProgressBar progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
            serverLabels.add(label);
            progressBars.add(progressBar);

            JPanel serverInfoPanel = new JPanel(new BorderLayout());
            serverInfoPanel.add(label, BorderLayout.NORTH);
            serverInfoPanel.add(progressBar, BorderLayout.SOUTH);

            serverPanel.add(serverInfoPanel);
        }

        JScrollPane serverScrollPane = new JScrollPane(serverPanel);
        contentPane.add(serverScrollPane);
        setVisible(true);
    }

    public void updateQueueContent(List<Server> servers, List<Client> waitingClients, Integer time) {
        timeLabel.setText("Time: " + time.toString());
        clientsLabel.setText("Waiting Clients: " + waitingClients);
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            JLabel label = serverLabels.get(i);
            JProgressBar progressBar = progressBars.get(i);

            label.setText("Server " + (i+1) + " Clients: " + server.getClients() + " | Server Time: " + server.getWaitingPeriod());

            if (!server.getClients().isEmpty()) {
                Client client = server.getClients().peek();
                int progress = 100 - (int) (((double) client.getServiceTime() / client.getServiceTimeUnmodified()) * 100);
                progressBar.setValue(progress);
            } else {
                progressBar.setValue(0);
            }
        }
    }
    public void showAverageTimes(){
        JLabel averageWaitingTimeLabel = new JLabel();
        JLabel averageServiceTimeLabel = new JLabel();
        serverPanel.add(averageWaitingTimeLabel);
        serverPanel.add(averageServiceTimeLabel);
        averageWaitingTimeLabel.setText("Average Waiting Time: " + (double)TimeCalculator.avgWaitingTime/ SimulationManager.numberOfClients);
        averageServiceTimeLabel.setText("Average Service Time: " + (double)TimeCalculator.avgServiceTime);
    }
}
