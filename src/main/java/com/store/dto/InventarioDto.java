package com.store.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * Dto de la tabla inventario.
 * 
 * @author PABI1
 *
 */

@Data
public class InventarioDto {

	private Long idInventario;

	private ProductoDto producto;

	private Integer cantidadInicial;

	private Integer cantidadIngreso;

	private Integer cantidadEgreso;

	private Integer cantidadDisponible;

	private BigDecimal precioIngreso;
	
	private BigDecimal precioEgreso;

	private String tipoMovimiento;
	
	private String fechaMovimiento;
	
	private BigDecimal precioPromedio;

}
