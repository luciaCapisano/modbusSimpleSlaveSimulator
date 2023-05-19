package com.JamodTest.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JamodTest.Connection.ModbusRequests;

import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;

@RestController
@RequestMapping("")
public class RequestsController {

	@Autowired
	private ModbusRequests modbusReq;

	@GetMapping("/registers")
	private ResponseEntity<?> getAllRegisters() {

		ReadMultipleRegistersResponse response = modbusReq.requestAllRegisters();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/registers/{adress}")
	private ResponseEntity<?> getRegister(@PathVariable int adress) {
		
		ReadMultipleRegistersResponse response = modbusReq.requestRegister(adress);
		
		return ResponseEntity.ok(response);
	}

	@PostMapping("/registers")
	private ResponseEntity<?> changeCoils(@RequestParam("adress") int adress, @RequestParam("value") int value) {

		ReadMultipleRegistersResponse response = modbusReq.modifyRegister(adress, value);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/coils")
	private ResponseEntity<?> getCoils() {

		ReadCoilsResponse response = modbusReq.requestCoils();

		return ResponseEntity.ok(response);
	}

	@PostMapping("/coils")
	private ResponseEntity<?> changeCoils(@RequestParam("adress") int adress, @RequestParam("value") boolean value) {

		ReadCoilsResponse response = modbusReq.modifyCoil(adress, value);

		return ResponseEntity.ok(response);
	}

}
