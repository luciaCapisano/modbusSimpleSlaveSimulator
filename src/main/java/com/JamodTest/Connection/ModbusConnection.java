package com.JamodTest.Connection;

import java.net.InetAddress;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.net.TCPMasterConnection;

@Component
public class ModbusConnection implements InitializingBean {

//	private final String IP = "127.0.0.1";
	private final String IP = "190.106.131.23";

	TCPMasterConnection connection = null;
	ModbusTCPTransaction transaction = null; // the transaction

	@Override
	public void afterPropertiesSet() throws Exception {

		try {

			this.connectTCP();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	private void connectTCP() throws Exception  {

		InetAddress ipAdress = InetAddress.getByName(IP);
		connection = new TCPMasterConnection(ipAdress);
//		connection.setPort(502);
		connection.setPort(22222);
		connection.connect();

	}

	private void disconnectTCP() {
		connection.close();

	}

	void executeTransaction(ModbusTCPTransaction transaction, ModbusRequest request) {

		transaction.setRequest(request);

		try {

			transaction.execute();
//			this.disconnectTCP();

		} catch (ModbusException e) {
			e.printStackTrace();
		}
	}

	public ModbusTCPTransaction getTransaction() {

		return new ModbusTCPTransaction(connection);

//		return this.transaction;

	}

}
