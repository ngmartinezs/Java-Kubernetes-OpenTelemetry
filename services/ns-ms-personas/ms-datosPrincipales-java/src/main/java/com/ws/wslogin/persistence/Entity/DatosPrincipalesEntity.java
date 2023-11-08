package com.ws.wslogin.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

@Builder
@AllArgsConstructor
public class DatosPrincipalesEntity {

    private String id;
    private DatosPersona datosPersona;
    private DatosContacto datosContacto;

    public DatosPrincipalesEntity() {
        this.datosPersona = new DatosPersona();
        this.datosContacto = new DatosContacto();
    }


    @Setter
    @Getter
    @NoArgsConstructor    
    @AllArgsConstructor
    public class DatosContacto {
        public String celular;
        public String telefono;
        public String correo;
        public String direccion;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    
    @AllArgsConstructor
    public class DatosPersona{
        public String tipoDocumento;
        public String numeroDocumento;
        public String primerNombre;
        public String primerApellido;
        public String segundoNombre;
        public String segundoApellido;
        public String fechaNacimiento;
        public String sexo;
        public String estadoCivil;   
    }
}
