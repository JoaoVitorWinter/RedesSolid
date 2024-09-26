package com.ativ.security.redessolid.service;

import com.ativ.security.redessolid.exception.MascaraInvalidaException;
import com.ativ.security.redessolid.exception.NumerosDeHostsInvalidoException;
import com.ativ.security.redessolid.exception.QuantidadeDeOctetosInvalidoException;
import com.ativ.security.redessolid.exception.RedeComNumeroLimitadosDeHostsException;
import com.ativ.security.redessolid.dto.*;
import com.ativ.security.redessolid.utils.ConversorOctetoParaStringUtils;
import com.ativ.security.redessolid.utils.ConversorStringParaOctetoUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculadoraVLSM implements Calculadora<VLSMRequest, List<VLAN>> {
    private CalculadoraRede calculadoraRede;
    private CalculadoraMascara calculadoraMascara;
    private ConversorStringParaOctetoUtils conversorStringParaOctetoUtils;
    private ConversorOctetoParaStringUtils conversorOctetoParaStringUtils;

    @Override
    public List<VLAN> calcular(VLSMRequest request)
            throws RedeComNumeroLimitadosDeHostsException,
            QuantidadeDeOctetosInvalidoException,
            MascaraInvalidaException,
            NumerosDeHostsInvalidoException {
        verificarValidadeDoNumeroDeHostsTotal(request);
        ordernarVLANRequestPorNumeroDeHostsCrescente(request.getVlans());
        List<VLAN> vlans = new ArrayList<>();
        String ip = request.getIpComMascara().getIp();
        for (VLANRequest vlanRequest : request.getVlans()) {
            if (!vlans.isEmpty()) {
                ip = conversorOctetoParaStringUtils.transformarOctetosEmIp(gerarProximoIpValido(vlans.getLast().getIpBroadcast()));
            }
            IPComMascara ipComMascara = new IPComMascara();
            ipComMascara.setIp(ip);
            ipComMascara.setMascara(calculadoraMascara.calcular(vlanRequest.getNumeroDeHosts()));
            Rede rede = calculadoraRede.calcular(ipComMascara);
            VLAN vlan = new VLAN(rede);
            vlan.setNome(vlanRequest.getNome());
            vlans.add(vlan);
        }
        return vlans;
    }

    private void ordernarVLANRequestPorNumeroDeHostsCrescente(List<VLANRequest> vlanRequests) {
        vlanRequests.sort((vlan1, vlan2) -> {
            if (vlan1.getNumeroDeHosts() > vlan2.getNumeroDeHosts()) {
                return -1;
            } else if (vlan1.getNumeroDeHosts() == vlan2.getNumeroDeHosts()) {
                return 0;
            } else {
                return 1;
            }
        });
    }

    private Integer[] gerarProximoIpValido(String ultimoIp) {
        Integer[] quatroOctetosDoIp =
                conversorStringParaOctetoUtils.converterStringParaOctetos(ultimoIp);
        for (int i = 3; i >= 0; i--) {
            if (quatroOctetosDoIp[i] != 255) {
                quatroOctetosDoIp[i] += 1;
                if (i < 3) {
                    for (int j = i + 1; j < 4; j++) {
                        quatroOctetosDoIp[j] = 0;
                    }
                }
                break;
            }
        }
        return quatroOctetosDoIp;
    }

    private void verificarValidadeDoNumeroDeHostsTotal(VLSMRequest request) throws RedeComNumeroLimitadosDeHostsException {
        Integer mascara = request.getIpComMascara().getMascara();
        Integer quantidadeTotalDeHosts = request.getVlans().stream()
                .map(VLANRequest::getNumeroDeHosts).reduce(0, Integer::sum);
        if (quantidadeTotalDeHosts > Math.pow(2, 32 - mascara) - 2 * request.getVlans().size()) {
            throw new RedeComNumeroLimitadosDeHostsException("A rede solicitada não suporta todas as VLANs desejadas!");
        }
    }
}
