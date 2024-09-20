package com.ativ.security.redessolid.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rede {
    private String idRede;
    private String idBroadcast;
    private Integer numeroDeHosts;
    private Integer mascara;
}
