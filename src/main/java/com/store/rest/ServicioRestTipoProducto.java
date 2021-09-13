package com.store.rest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.TipoProductoDto;
import com.store.excepcion.RestExcepcion;
import com.store.util.Constantes;

public class ServicioRestTipoProducto implements Serializable {

	private static final long serialVersionUID = 5505619369335628735L;
	private static final Logger LOGGER = Logger.getLogger(ServicioRestTipoProducto.class.getName());
	private static RestTemplate restTemplate;

	public static List<TipoProductoDto> listarTipoProducto() throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<TipoProductoDto[]> response = restTemplate
					.getForEntity(Constantes.URL_BASE.concat("/tipo-producto"), TipoProductoDto[].class);
			ObjectMapper mapper = new ObjectMapper();
			return Arrays.stream(response.getBody()).map(object -> mapper.convertValue(object, TipoProductoDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	private ServicioRestTipoProducto() {
		super();
	}
}
