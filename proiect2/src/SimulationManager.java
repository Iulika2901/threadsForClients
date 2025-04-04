import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private List<Task> tasks;
    private JTextArea queueDisplay;
    private int numClients, arrivalMin, arrivalMax, serviceMin, serviceMax;

    public SimulationManager(int numServers, int numClients, int arrivalMin, int arrivalMax, int serviceMin, int serviceMax, JTextArea queueDisplay) {
        this.scheduler = new Scheduler(numServers);
        this.scheduler.changeStrategy(ConcreteStrategyTime.SelectionPolicy.SHORTEST_TIME);
        this.queueDisplay = queueDisplay;
        this.numClients = numClients;
        this.arrivalMin = arrivalMin;
        this.arrivalMax = arrivalMax;
        this.serviceMin = serviceMin;
        this.serviceMax = serviceMax;
        this.tasks = new ArrayList<>();
    }

    private void generateRandomTasks() {
        Random rand = new Random();
        for (int i = 1; i <= numClients; i++) {
            int arrivalTime = rand.nextInt(arrivalMax - arrivalMin + 1) + arrivalMin;
            int serviceTime = rand.nextInt(serviceMax - serviceMin + 1) + serviceMin;
            tasks.add(new Task(i, arrivalTime, serviceTime));
        }
    }

    @Override
    public void run() {
        generateRandomTasks();
        tasks.sort((a, b) -> Integer.compare(a.getArrivalTime(), b.getArrivalTime()));

        float totalWaitingTime = 0f;
        float totalServiceTime = 0f;
        int peakHour = 0;
        int maxClientsInQueues = 0;

        int currentTime = 0;
        for (Task task : tasks) {
            int delay = Math.max(0, task.getArrivalTime() - currentTime);
            try {
                Thread.sleep(delay * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            currentTime += delay;

            // Trimite task-ul și obține serverul
            Server assignedServer = scheduler.dispatchTask(task);

            // Timp de așteptare estimat = cât are deja în coadă + cât durează toate taskurile din coadă
            int estimatedWait = assignedServer.getWaitingPeriod(); // cât are deja în coadă
            totalWaitingTime += estimatedWait;
            totalServiceTime += task.getServiceTime();

            // Verifică câți clienți sunt în total în toate cozile (pentru peak hour)
            int totalClientsInQueues = scheduler.getServers().stream()
                    .mapToInt(s -> s.getTasks().size())
                    .sum();
            if (totalClientsInQueues > maxClientsInQueues) {
                maxClientsInQueues = totalClientsInQueues;
                peakHour = currentTime;
            }

            // Afișare
            Task finalTask = task;
            int finalCurrentTime = currentTime;
            SwingUtilities.invokeLater(() ->
                    queueDisplay.append("Time " + finalCurrentTime + ": Task " + finalTask.getId() +
                            " added to " + assignedServer.getName() + ".\n")
            );
        }

        // Calculează medii
        float avgWaitingTime = totalWaitingTime / tasks.size();
        float avgServiceTime = totalServiceTime / tasks.size();
        int finalPeakHour = peakHour;

        SwingUtilities.invokeLater(() -> {
            queueDisplay.append("\n--- Simulation Statistics ---\n");
            queueDisplay.append("Average Waiting Time: " + String.format("%.2f", avgWaitingTime) + "\n");
            queueDisplay.append("Average Service Time: " + String.format("%.2f", avgServiceTime) + "\n");
            queueDisplay.append("Peak Hour: " + finalPeakHour + " (most clients in queues)\n");
            queueDisplay.append("Simulation finished!\n");
        });
    }
}
