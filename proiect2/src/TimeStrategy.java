import java.util.List;

public class TimeStrategy implements Strategy {

    @Override
    public Server addTask(List<Server> servers, Task task) {
        Server bestServer = servers.get(0);
        for (Server server : servers) {
            if (server.getWaitingPeriod() < bestServer.getWaitingPeriod()) {
                bestServer = server;
            }
        }
        bestServer.addTask(task);
        return bestServer;
    }
}
