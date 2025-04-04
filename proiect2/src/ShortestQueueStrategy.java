import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public Server addTask(List<Server> servers, Task task) {
        Server bestServer = servers.get(0);
        for (Server server : servers) {
            if (server.getTasks().size() < bestServer.getTasks().size()) {
                bestServer = server;
            }
        }
        bestServer.addTask(task);
        return bestServer;
    }
}
