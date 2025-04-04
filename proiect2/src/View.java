import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private JTextField numClientsField, numQueuesField, minArrivalField, maxArrivalField, minServiceField, maxServiceField;
    private JButton startSimulationButton;
    private JTextArea queueDisplay;

    public View() {
        this.setTitle("Queue Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Simulation Parameters"));

        numClientsField = new JTextField(10);
        numQueuesField = new JTextField(10);
        minArrivalField = new JTextField(10);
        maxArrivalField = new JTextField(10);
        minServiceField = new JTextField(10);
        maxServiceField = new JTextField(10);

        inputPanel.add(new JLabel("Number of Clients:"));
        inputPanel.add(numClientsField);
        inputPanel.add(new JLabel("Number of Queues:"));
        inputPanel.add(numQueuesField);
        inputPanel.add(new JLabel("Min Arrival Time:"));
        inputPanel.add(minArrivalField);
        inputPanel.add(new JLabel("Max Arrival Time:"));
        inputPanel.add(maxArrivalField);
        inputPanel.add(new JLabel("Min Service Time:"));
        inputPanel.add(minServiceField);
        inputPanel.add(new JLabel("Max Service Time:"));
        inputPanel.add(maxServiceField);

        startSimulationButton = new JButton("Start Simulation");
        startSimulationButton.addActionListener(this);
        inputPanel.add(startSimulationButton);

        queueDisplay = new JTextArea(15, 50);
        queueDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(queueDisplay);

        this.add(inputPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int numClients = Integer.parseInt(numClientsField.getText());
            int numQueues = Integer.parseInt(numQueuesField.getText());
            int minArrival = Integer.parseInt(minArrivalField.getText());
            int maxArrival = Integer.parseInt(maxArrivalField.getText());
            int minService = Integer.parseInt(minServiceField.getText());
            int maxService = Integer.parseInt(maxServiceField.getText());

            SimulationManager simManager = new SimulationManager(numQueues, numClients, minArrival, maxArrival, minService, maxService, queueDisplay);
            new Thread(simManager).start();

        } catch (NumberFormatException ex) {
            queueDisplay.append("Invalid input! Please enter valid numbers.\n");
        }
    }
}
