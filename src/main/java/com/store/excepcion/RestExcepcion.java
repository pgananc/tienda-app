package com.store.excepcion;

public class RestExcepcion extends Exception {

	private static final long serialVersionUID = 6824974974362944978L;

	public RestExcepcion(String mensaje) {
		super(mensaje);
	}
}
