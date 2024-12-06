package org.example.modbus;

import junit.framework.TestCase;
import org.example.subscriber.service.modbus.ModbusServer;
import org.example.subscriber.service.modbus.RegisterReader;

public class ModbusTest extends TestCase {
    public void testBaseElements() {
        ModbusServer modbusServer = null;
        try {
            // Подключение к серверу
            modbusServer = ModbusServer.getInstance();
//            modbusServer.info();
            RegisterReader registerReader = new RegisterReader(modbusServer);
            registerReader.printMultipleRegister(0, 1);
        } catch (Exception e) {
            System.err.println("Ошибка при работе с Modbus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            modbusServer.quit();
        }
    }

    public void testSetCoil() {
        ModbusServer modbusServer = null;
        try {
            // Подключение к серверу
            modbusServer = ModbusServer.getInstance();
//            modbusServer.info();
            RegisterReader registerReader = new RegisterReader(modbusServer);
            assertTrue(registerReader.setCoil(1));
        } catch (Exception e) {
            System.err.println("Ошибка при работе с Modbus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            modbusServer.quit();
        }
    }

    public void testSetHoldingRegister() {
        ModbusServer modbusServer = null;
        try {
            // Подключение к серверу
            modbusServer = ModbusServer.getInstance();
            int registerValue = 35;
            RegisterReader registerReader = new RegisterReader(modbusServer);
            assertEquals(registerValue, registerReader.setHoldingRegister(registerValue));
        } catch (Exception e) {
            System.err.println("Ошибка при работе с Modbus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            modbusServer.quit();
        }
    }


}
