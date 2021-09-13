package com.store.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * Dto de la tabla producto.
 * 
 * @author PABI1
 *
 */

@Data
public class ProductoDto implements Serializable {
	private static final long serialVersionUID = -6329580276259098822L;

	private Long idProducto;

	private String codigo;

	private TipoProductoDto tipoProducto;

	private String nombre;

	private String descripcion;

	private BigDecimal precio;
	
	private Integer cantidad;

	private Boolean estado;


}
