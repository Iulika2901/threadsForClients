import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private boolean running = true;
    private String name;

    public Server(String name) {
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Task task = tasks.take();
                Thread.sleep(task.getServiceTime() * 1000L);
                waitingPeriod.addAndGet(-task.getServiceTime());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopServer() {
        running = false;
    }
}
