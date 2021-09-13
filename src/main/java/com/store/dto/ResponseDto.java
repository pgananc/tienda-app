package com.store.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto para mensaje generico de error.
 * 
 * @author PABI1
 *
 */
@NoArgsConstructor
public class ResponseDto {
	@Getter
	@Setter
	private LocalDateTime timestamp;
	@Getter
	@Setter
	private String mensaje;
	@Getter
	@Setter
	private String detalles;

}
