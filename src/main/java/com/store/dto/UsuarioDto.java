package com.store.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Dto para entidad usuario.
 * 
 * @author PABI1
 *
 */
@Data
public class UsuarioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre;

	private String clave;

	private boolean estado;
}
