package com.store.excepcion;

public class ModeloExcepcion extends Exception {

	private static final long serialVersionUID = 6824974974362944978L;

	public ModeloExcepcion(String mensaje) {
		super(mensaje);
	}
}
