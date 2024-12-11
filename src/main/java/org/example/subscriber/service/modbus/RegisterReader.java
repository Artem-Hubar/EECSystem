package org.example.subscriber.service.modbus;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;


import java.util.NoSuchElementException;

public class RegisterReader {
    private final ModbusServer modbusServer;

    public RegisterReader(ModbusServer modbusServer) {
        this.modbusServer = modbusServer;
    }

    public void printMultipleRegister(int startAddress, int numberOfRegisters) {
        try {
            InputRegister[] registers = getInputRegisters(startAddress, numberOfRegisters);
            printRegisters(startAddress, registers);
        } catch (Exception e) {
            System.err.println("Ошибка чтения регистров: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printRegisters(int startAddress, InputRegister[] registers) {
        if (registers.length == 0) throw new NoSuchElementException("Нет данных в регистрах!");
        for (int i = 0; i < registers.length; i++) {
            System.out.printf("Регистр %d: %d%n", startAddress + i, registers[i].getValue());
        }
    }

    public InputRegister[] getInputRegisters(int startAddress, int numberOfRegisters) throws ModbusException {
        ModbusTCPMaster master = modbusServer.master;
        int unitId = modbusServer.getUnitId();
        return master.readMultipleRegisters(unitId, startAddress, numberOfRegisters);
    }

    public boolean setCoil(int value) throws ModbusException {
        ModbusTCPMaster master = modbusServer.master;
        int unitId = modbusServer.getUnitId();
        return master.writeCoil(unitId, 0, true);
    }

    public int setHoldingRegister(int value) throws ModbusException {
        ModbusTCPMaster master = modbusServer.master;
        int unitId = modbusServer.getUnitId();
        Register register = new SimpleRegister(value);
        // Запись в регистр
        return master.writeSingleRegister(unitId, 1, register);
    }


}
