package com.ativ.security.redessolid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VLAN extends Rede {
    private String nome;

    public VLAN(Rede rede) {
        this.setIpBroadcast(rede.getIpBroadcast());
        this.setIpRede(rede.getIpRede());
        this.setNumeroDeHosts(rede.getNumeroDeHosts());
        this.setMascara(rede.getMascara());
    }
}
