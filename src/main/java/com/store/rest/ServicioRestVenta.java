package com.store.rest;

import java.io.Serializable;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.store.dto.VentaDto;
import com.store.excepcion.RestExcepcion;
import com.store.util.Constantes;

public class ServicioRestVenta implements Serializable {

	private static final long serialVersionUID = 5505619369335628735L;
	private static final Logger LOGGER = Logger.getLogger(ServicioRestVenta.class.getName());
	private static RestTemplate restTemplate;

	public static int guardarVenta(VentaDto ventaDto) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<Object> response = restTemplate.postForEntity(Constantes.URL_BASE.concat("/venta"),
					ventaDto, Object.class);
			return response.getStatusCodeValue();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	private ServicioRestVenta() {
		super();
	}
}
