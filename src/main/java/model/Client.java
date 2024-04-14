package model;

public class Client implements Comparable<Client> {
    private Integer id;
    private Integer arrivalTime;
    private Integer serviceTime;
    private Integer serviceTimeUnmodified;


    public Client() {
    }

    public Client(Integer id, Integer serviceTime, Integer arrivalTime) {
        this.id = id;
        this.serviceTime = serviceTime;
        this.arrivalTime = arrivalTime;
        this.serviceTimeUnmodified  = serviceTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getServiceTimeUnmodified() {
        return serviceTimeUnmodified;
    }

    public void setServiceTimeUnmodified(Integer serviceTimeUnmodified) {
        this.serviceTimeUnmodified = serviceTimeUnmodified;
    }

    @Override
    public int compareTo(Client otherClient) {
        return Integer.compare(this.arrivalTime, otherClient.arrivalTime);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }
}
