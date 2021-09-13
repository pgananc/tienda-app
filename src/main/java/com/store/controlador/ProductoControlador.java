package com.store.controlador;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;
import org.springframework.http.HttpStatus;

import com.store.dto.ProductoDto;
import com.store.dto.TipoProductoDto;
import com.store.excepcion.RestExcepcion;
import com.store.rest.ServicioRestProducto;
import com.store.rest.ServicioRestTipoProducto;

import lombok.Getter;
import lombok.Setter;

@ViewScoped
@Named
public class ProductoControlador implements Serializable {

	private static final long serialVersionUID = -2765164024578345691L;
	private static final String COMPONENTE_MENSAJE_ID = "formProducto:msg";
	private static final Logger LOGGER = Logger.getLogger(ProductoControlador.class.getName());

	@Getter
	@Setter
	private ProductoDto productoDto;
	@Getter
	@Setter
	private List<TipoProductoDto> listaTipoProducto;
	@Getter
	@Setter
	private Long idTipoProducto;

	@PostConstruct
	public void inicializar() {
		try {
			productoDto = new ProductoDto();
			productoDto.setTipoProducto(new TipoProductoDto());
			productoDto.setEstado(true);
			listaTipoProducto = ServicioRestTipoProducto.listarTipoProducto();
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al cargar datos.");
			Ajax.update(COMPONENTE_MENSAJE_ID);

		}
	}

	public void guardar() {
		try {
			int status = ServicioRestProducto.guardarProducto(productoDto);
			if (status == HttpStatus.CREATED.value()) {
				Messages.addInfo(COMPONENTE_MENSAJE_ID, "Registro guardado.");
				productoDto = new ProductoDto();
				productoDto.setTipoProducto(new TipoProductoDto());
			} else {
				Messages.addError(COMPONENTE_MENSAJE_ID, "Error al guardar.");
				Ajax.update(COMPONENTE_MENSAJE_ID);
			}
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al guardar.");
			Ajax.update(COMPONENTE_MENSAJE_ID);
		}
	}

}
