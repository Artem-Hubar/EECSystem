package org.example.subscriber.service.modbus;

public class ModBusInfoPrinter {

    private final String modbusHost;
    private final int modbusPort;
    private final int unitId;

    public ModBusInfoPrinter(String modbusHost, int modbusPort, int unitId) {
        this.modbusHost = modbusHost;
        this.modbusPort = modbusPort;
        this.unitId = unitId;
    }

    public void printInfo() {
        System.out.println("Чтение регистров Modbus...");
        System.out.println("Подключение к Modbus-серверу...");
        infoHost();
        infoPost();
        infoUnitId();
    }

    public void infoHost() {
        System.out.println("Host: " + modbusHost);
    }

    public void infoPost() {
        System.out.println("Port: " + modbusPort);
    }

    public void infoUnitId() {
        System.out.println("Unit ID: " + unitId);
    }
}
