package com.store.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Dto para venta.
 * 
 * @author PABI1
 *
 */
@Data
public class VentaDto implements Serializable {
	private static final long serialVersionUID = 8206101638300330390L;
	private String numeroFactura;
	private boolean estado;
	private List<DetalleVentaDto> detalleVenta;

}
