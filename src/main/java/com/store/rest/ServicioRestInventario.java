package com.store.rest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.dto.InventarioDto;
import com.store.excepcion.RestExcepcion;
import com.store.util.Constantes;

public class ServicioRestInventario implements Serializable {

	private static final long serialVersionUID = 5505619369335628735L;
	private static final Logger LOGGER = Logger.getLogger(ServicioRestInventario.class.getName());
	private static RestTemplate restTemplate;

	public static int guardarInventario(InventarioDto inventarioDto) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<InventarioDto> response = restTemplate
					.postForEntity(Constantes.URL_BASE.concat("/inventario"), inventarioDto, InventarioDto.class);
			return response.getStatusCodeValue();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	public static List<InventarioDto> buscarInventarioPorId(Long id) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<InventarioDto[]> response = restTemplate
					.getForEntity(Constantes.URL_BASE.concat("/inventario/" + id), InventarioDto[].class);
			ObjectMapper mapper = new ObjectMapper();
			return Arrays.stream(response.getBody()).map(object -> mapper.convertValue(object, InventarioDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	public static List<InventarioDto> buscarInventarioPorCodigoProducto(String codigoProducto) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<InventarioDto[]> response = restTemplate.getForEntity(
					Constantes.URL_BASE.concat("/inventario/codigoProducto/" + codigoProducto), InventarioDto[].class);
			ObjectMapper mapper = new ObjectMapper();
			return Arrays.stream(response.getBody()).map(object -> mapper.convertValue(object, InventarioDto.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	private ServicioRestInventario() {
		super();
	}
}
