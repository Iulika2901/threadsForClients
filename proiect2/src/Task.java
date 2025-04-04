import java.util.ArrayList;
import java.util.Collection;

public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    // Folosit doar pentru a îndeplini cerința greșită de a avea listă de taskuri în Task
    private Collection<Task> tasks = new ArrayList<>();
    private int waitingPeriod = 0;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + arrivalTime + ", " + serviceTime + ")";
    }

    // Metodă greșit plasată, dar completată pentru cerință
    public Collection<Task> getTasks() {
        return tasks;
    }

    // Metodă greșit plasată, dar completată pentru cerință
    public void addTask(Task task) {
        tasks.add(task);
        waitingPeriod += task.getServiceTime();
    }

    // Metodă greșit plasată, dar completată pentru cerință
    public int getWaitingPeriod() {
        return waitingPeriod;
    }
}
