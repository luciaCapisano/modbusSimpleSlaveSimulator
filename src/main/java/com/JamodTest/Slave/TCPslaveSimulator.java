package com.JamodTest.Slave;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.net.ModbusTCPListener;
import net.wimpi.modbus.procimg.SimpleDigitalIn;
import net.wimpi.modbus.procimg.SimpleDigitalOut;
import net.wimpi.modbus.procimg.SimpleInputRegister;
import net.wimpi.modbus.procimg.SimpleProcessImage;
import net.wimpi.modbus.procimg.SimpleRegister;

@Component
public class TCPslaveSimulator implements InitializingBean {

	private ModbusTCPListener listener = null;
	private SimpleProcessImage spi = null;

	@Value("${modbus.TCP.port}")
	private int PUERTO;

	@Override
	public void afterPropertiesSet() throws Exception {

		this.setUpSingleProcessImage();

		try {

			this.setUpSlave();

		} catch (UnknownHostException e) {

			System.out.println("Error al configurar esclavo. ");
			e.printStackTrace();

		}
	}

	private void setUpSingleProcessImage() {

		spi = new SimpleProcessImage();

		spi.addDigitalOut(new SimpleDigitalOut(false));
		spi.addDigitalOut(new SimpleDigitalOut(true));

		spi.addDigitalIn(new SimpleDigitalIn(false));
		spi.addDigitalIn(new SimpleDigitalIn(true));

		// HOLDING REGISTERS - Lectura escritura - Leído con ReadMultipleRegistersRequest
		spi.addRegister(new SimpleRegister(1));
		spi.addRegister(new SimpleRegister(1));

		// INPUT REGISTERS - Solo lectura - Leído con ReadInputRegistersRequest
		spi.addInputRegister(new SimpleInputRegister(5));
		spi.addInputRegister(new SimpleInputRegister(6));

	}

	private void setUpSlave() throws UnknownHostException {

		ModbusCoupler.getReference().setProcessImage(spi);
		ModbusCoupler.getReference().setMaster(false);

		listener = new ModbusTCPListener(3);
		listener.setAddress(InetAddress.getByName("localhost"));
		listener.setPort(PUERTO);
		listener.start();

	}

	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
	public void actualizaValorRegistro() throws Exception {

		int incremento = 1;

		int valor = ModbusCoupler.getReference().getProcessImage().getRegister(0).getValue();

		boolean ascendiendo = (valor >= 1000) ? false : (valor <= 0) ? true : true;

		valor = ascendiendo ? valor + incremento : valor - incremento;

		spi.getRegister(0).setValue(valor);

	}

}
