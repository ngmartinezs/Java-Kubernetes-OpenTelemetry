package com.ws.wslogin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DatosPersonaDTO {
    private String tipoDocumento;
    private String numeroDocumento;
    private String primerNombre;
    private String primerApellido;
    private String segundoNombre;
    private String segundoApellido;
    private String fechaNacimiento;
    private String sexo;
    private String estadoCivil;   
}
