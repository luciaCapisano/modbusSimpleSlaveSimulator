package com.JamodTest.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.procimg.SimpleRegister;

@Component
public class ModbusRequests {
	
	@Autowired
	private ModbusConnection connection;
	
	public ReadMultipleRegistersResponse requestAllRegisters() {

		ModbusTCPTransaction transaction = connection.getTransaction();
		ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(4001, 8);

		connection.executeTransaction(transaction, request);

		ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();

		return response;

	}

	public ReadMultipleRegistersResponse requestRegister(int adress) {
		
		ModbusTCPTransaction transaction = connection.getTransaction();
		ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(adress, 1);
		
		connection.executeTransaction(transaction, request);
		
		ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();
		
		return response;
		
	}

	public ReadCoilsResponse requestCoils() {

		ModbusTCPTransaction transaction = connection.getTransaction();
		ReadCoilsRequest request = new ReadCoilsRequest(0, 10);

		connection.executeTransaction(transaction, request);

		ReadCoilsResponse responseCoils = (ReadCoilsResponse) transaction.getResponse();

		return responseCoils;
	}

	public ReadCoilsResponse modifyCoil(int reference, boolean state) {

		ModbusTCPTransaction transaction = connection.getTransaction();
		WriteCoilRequest request = new WriteCoilRequest(reference, state);
		
		connection.executeTransaction(transaction, request);

		return this.requestCoils();
	}

	public ReadMultipleRegistersResponse modifyRegister(int adress, int value) {
		
		ModbusTCPTransaction transaction = connection.getTransaction();

		SimpleRegister register = new SimpleRegister(value);
		WriteSingleRegisterRequest request = new WriteSingleRegisterRequest(adress, register);

		connection.executeTransaction(transaction, request);

		return this.requestAllRegisters();

	}

}
