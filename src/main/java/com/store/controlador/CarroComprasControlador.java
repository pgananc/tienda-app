package com.store.controlador;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.omnifaces.util.Ajax;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.springframework.http.HttpStatus;

import com.store.dto.DetalleVentaDto;
import com.store.dto.ProductoDto;
import com.store.dto.VentaDto;
import com.store.excepcion.RestExcepcion;
import com.store.rest.ServicioRestProducto;
import com.store.rest.ServicioRestVenta;

import lombok.Getter;
import lombok.Setter;

@SessionScoped
@Named
public class CarroComprasControlador implements Serializable {

	private static final long serialVersionUID = -5410668683216251453L;
	private static final String COMPONENTE_MENSAJE_ID = "formProducto:msg";
	private static final Logger LOGGER = Logger.getLogger(KardexControlador.class.getName());

	@Getter
	@Setter
	private List<ProductoDto> listaProducto;
	@Getter
	@Setter
	private List<DetalleVentaDto> listaDetalle;
	@Getter
	@Setter
	private DetalleVentaDto detalleVenta;

	@Getter
	@Setter
	private ProductoDto producto;

	@PostConstruct
	public void inicializar() {
		try {
			setListaDetalle(new ArrayList<>());
			listaProducto = ServicioRestProducto.listarProducto();
		} catch (RestExcepcion e) {
			LOGGER.severe(e.getMessage());
			Messages.addError(COMPONENTE_MENSAJE_ID, "Error al cargar datos.");
			Ajax.update(COMPONENTE_MENSAJE_ID);

		}
	}

	public void seleccionar(ProductoDto productoDto) {
		producto = productoDto;
		PrimeFaces current = PrimeFaces.current();
		setDetalleVenta(new DetalleVentaDto());
		current.executeScript("PF('myDialogVar').show();");
	}

	public void agregarCarro() {
		detalleVenta.setProducto(producto);
		listaDetalle.add(detalleVenta);
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('myDialogVar').hide();");
	}

	public void guardar() {
		try {
			VentaDto ventaDto = new VentaDto();
			ventaDto.setNumeroFactura(LocalDateTime.now().toString());
			ventaDto.setEstado(true);
			ventaDto.setDetalleVenta(listaDetalle);
			int status = ServicioRestVenta.guardarVenta(ventaDto);
			if (status == HttpStatus.CREATED.value()) {
				ventaDto = new VentaDto();
				Messages.addInfo(COMPONENTE_MENSAJE_ID, "Registro guardado.");
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
