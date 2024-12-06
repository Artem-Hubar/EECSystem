package org.example.subscriber.service.modbus;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;

@Getter
@Setter
public class ModbusServer {
    private String modbusHost = "127.0.0.1";
    private int modbusPort = 502;
    private int unitId = 1;

    // Количество регистров для чтения

    ModbusTCPMaster master = new ModbusTCPMaster(modbusHost, modbusPort);

    public ModbusServer() throws Exception {
        master.connect();
    }

    public static ModbusServer getInstance() throws Exception {
        return new ModbusServer();
    }

    public void info() {
        ModBusInfoPrinter modBusInfo = new ModBusInfoPrinter(modbusHost, modbusPort, unitId);
        modBusInfo.printInfo();
    }

    public void quit(){
        if (master == null || !master.isConnected()) {
            throw new NoSuchElementException("master null or not connected");
        }
        try {
            master.disconnect();
        } catch (Exception e) {
            System.err.println("Ошибка при закрытии подключения: " + e.getMessage());
        }
    }
}
