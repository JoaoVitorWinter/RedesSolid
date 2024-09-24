package com.ativ.security.redessolid.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rede {
    private String ipRede;
    private String ipBroadcast;
    private Integer numeroDeHosts;
    private Integer mascara;
}
