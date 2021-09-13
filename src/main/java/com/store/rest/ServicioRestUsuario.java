package com.store.rest;

import java.io.Serializable;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.store.dto.UsuarioDto;
import com.store.excepcion.RestExcepcion;
import com.store.util.Constantes;

public class ServicioRestUsuario implements Serializable {

	private static final long serialVersionUID = 5505619369335628735L;
	private static final Logger LOGGER = Logger.getLogger(ServicioRestUsuario.class.getName());
	private static RestTemplate restTemplate;

	public static int guardarUsuario(UsuarioDto usuarioDto) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<UsuarioDto> response = restTemplate.postForEntity(Constantes.URL_BASE.concat("/usuario"),
					usuarioDto, UsuarioDto.class);
			return response.getStatusCodeValue();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Ha ocurrido un error al ejecutar el servicio.");
		}
	}

	public static boolean buscarUsuarioPorNombreYClave(String nombre, String clave) throws RestExcepcion {
		try {
			restTemplate = new RestTemplate();
			ResponseEntity<Object> response = restTemplate
					.getForEntity(Constantes.URL_BASE.concat("/usuario/" + nombre + "/" + clave), Object.class);
			return response.getStatusCode().equals(HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			throw new RestExcepcion("Datos incorrectos");
		}
	}

	private ServicioRestUsuario() {
		super();
	}
}
