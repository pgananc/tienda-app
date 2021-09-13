package com.store.controlador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;

import com.store.dto.InventarioDto;
import com.store.dto.ProductoDto;
import com.store.excepcion.RestExcepcion;
import com.store.rest.ServicioRestInventario;
import com.store.rest.ServicioRestProducto;

import lombok.Getter;
import lombok.Setter;

@ViewScoped
@Named
public class KardexControlador implements Serializable {
	private static final long serialVersionUID = -2765164024578345691L;
	private static final String COMPONENTE_MENSAJE_ID = "formKardex:msg";
	private static final Logger LOGGER = Logger.getLogger(KardexControlador.class.getName());
	@Getter
	@Setter
	private InventarioDto inventarioDto;
	@Getter
	@Setter
	private ProductoDto productoDto;
	@Getter
	@Setter
	private List<InventarioDto> listaInventario;
	@Getter
	@Setter
	private List<ProductoDto> listaProducto;
	@Getter
	@Setter
	private String codigoProducto;
	@Getter
	@Setter
	private Integer cantidad;
	@Getter
	@Setter
	private BigDecimal precio;
	@Getter
	@Setter
	private boolean mostrar;

	@PostConstruct
	public void inicializar() {
		try {
			listaProducto = ServicioRestProducto.listarProducto();
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al cargar datos.");
			Ajax.update(COMPONENTE_MENSAJE_ID);

		}
	}

	public void buscar() {
		try {

			listaInventario = ServicioRestInventario.buscarInventarioPorCodigoProducto(codigoProducto);
			Collections.sort(listaInventario, (x, y) -> x.getIdInventario().compareTo(y.getIdInventario()));
			listaInventario.stream().filter(e -> Objects.nonNull(e.getPrecioIngreso()))
			.forEach(e -> e.setPrecioPromedio(e.getPrecioIngreso()));
			listaInventario.stream().filter(e -> Objects.nonNull(e.getPrecioEgreso()))
					.forEach(e -> e.setPrecioPromedio(e.getPrecioEgreso()));
			;
			if (listaInventario.isEmpty()) {
				Messages.addInfo(COMPONENTE_MENSAJE_ID, "No existe datos");
				mostrar = false;
			} else {
				productoDto = listaInventario.get(0).getProducto();
				inventarioDto = new InventarioDto();
				inventarioDto.setTipoMovimiento("");
				mostrar = true;
			}
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al cargar datos.");
			Ajax.update(COMPONENTE_MENSAJE_ID);
		}
	}

	public void guardar() {
		try {
			if (inventarioDto.getTipoMovimiento().equals("I")) {
				inventarioDto.setCantidadIngreso(cantidad);
				inventarioDto.setPrecioIngreso(precio);
			} else {
				inventarioDto.setCantidadEgreso(cantidad);
				inventarioDto.setPrecioEgreso(precio);
			}
			inventarioDto.setProducto(productoDto);
			ServicioRestInventario.guardarInventario(inventarioDto);
			Messages.addInfo(COMPONENTE_MENSAJE_ID, "Registro correctamente.");
			setCantidad(null);
			setPrecio(null);
			mostrar = false;
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al actualizar.");
			Ajax.update(COMPONENTE_MENSAJE_ID);
		}
	}

}
