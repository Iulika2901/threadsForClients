import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;

    public Scheduler(int numServers) {
        servers = new ArrayList<>();
        for (int i = 0; i < numServers; i++) {
            int j=i+1;
            Server server = new Server("Queue" +" " +  j);
            servers.add(server);
            new Thread(server).start();
        }
    }

    public void changeStrategy(ConcreteStrategyTime.SelectionPolicy policy) {
        if (policy == ConcreteStrategyTime.SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        } else {
            strategy = new TimeStrategy();
        }
    }

    public Server dispatchTask(Task task) {
      return  strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}
