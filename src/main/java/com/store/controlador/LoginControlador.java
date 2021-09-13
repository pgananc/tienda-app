package com.store.controlador;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;
import org.springframework.http.HttpStatus;

import com.store.dto.UsuarioDto;
import com.store.excepcion.RestExcepcion;
import com.store.rest.ServicioRestUsuario;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LoginControlador implements Serializable {

	private static final long serialVersionUID = -2765164024578345692L;
	private static final String COMPONENTE_MENSAJE_ID = "growl";
	private static final Logger LOGGER = Logger.getLogger(LoginControlador.class.getName());
	@Getter
	@Setter
	private String nombre;
	@Getter
	@Setter
	private String clave;
	@Getter
	@Setter
	private UsuarioDto usuarioDto;

	public String ingresar() {
		try {
			if (ServicioRestUsuario.buscarUsuarioPorNombreYClave(nombre, clave)) {
				return "paginas/producto.xhtml?faces-redirect=true";
			} else {
				Messages.addError(COMPONENTE_MENSAJE_ID, "Datos incorrectos");
				Ajax.update(COMPONENTE_MENSAJE_ID);
			}
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, e.getMessage());
			Ajax.update(COMPONENTE_MENSAJE_ID);
		}
		return "";
	}

	public String guardar() {
		try {
			usuarioDto = new UsuarioDto();
			usuarioDto.setNombre(nombre);
			usuarioDto.setClave(clave);
			usuarioDto.setEstado(true);
			int status = ServicioRestUsuario.guardarUsuario(usuarioDto);
			if (status == HttpStatus.CREATED.value()) {
				Messages.addInfo(COMPONENTE_MENSAJE_ID, "Registro guardado.");
				setClave("");
				setNombre("");
				return "login.xhtml?faces-redirect=true";

			} else {
				Messages.addError(COMPONENTE_MENSAJE_ID, "Error al guardar.");
				Ajax.update(COMPONENTE_MENSAJE_ID);
			}
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al guardar.");
			Ajax.update(COMPONENTE_MENSAJE_ID);
		}
		return "";
	}
}
