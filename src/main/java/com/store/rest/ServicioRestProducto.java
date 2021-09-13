package com.store.rest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.ProductoDto;
import com.store.excepcion.RestExcepcion;
import com.store.util.Constantes;

public class ServicioRestProducto implements Serializable {

	private static final long serialVersionUID = 5505619369335628735L;
	private static final Logger LOGGER = Logger.getLogger(ServicioRestProducto.class.getName());
	private static RestTemplate restTemplate;

	
	public static int guardarProducto(ProductoDto empleadoDto) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<ProductoDto> response = restTemplate.postForEntity(Constantes.URL_BASE.concat("/producto"),
					empleadoDto, ProductoDto.class);
			return response.getStatusCodeValue();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	public static List<ProductoDto> listarProducto() throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<ProductoDto[]> response = restTemplate
					.getForEntity(Constantes.URL_BASE.concat("/producto"), ProductoDto[].class);
			ObjectMapper mapper = new ObjectMapper();
			return Arrays.stream(response.getBody()).map(object -> mapper.convertValue(object, ProductoDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}


	private ServicioRestProducto() {
		super();
	}
}
