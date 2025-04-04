import java.util.Random;

public class clients {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    public clients(int id, int arrivalMin, int arrivalMax, int serviceMin, int serviceMax) {
        Random rand = new Random();
        this.id = id;
        this.arrivalTime = rand.nextInt(arrivalMax - arrivalMin + 1) + arrivalMin;
        this.serviceTime = rand.nextInt(serviceMax - serviceMin + 1) + serviceMin;
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

}
