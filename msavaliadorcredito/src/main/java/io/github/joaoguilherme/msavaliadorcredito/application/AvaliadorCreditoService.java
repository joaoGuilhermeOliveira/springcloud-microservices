package io.github.joaoguilherme.msavaliadorcredito.application;

import io.github.joaoguilherme.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.joaoguilherme.msavaliadorcredito.domain.model.DadosCliente;
import io.github.joaoguilherme.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.joaoguilherme.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.joaoguilherme.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);
        cartoesResourceClient.getCartoesByCliente(cpf);
        return SituacaoCliente.builder().cliente(dadosClienteResponse.getBody()).cartoes(cartoesResponse.getBody()).build();
    }


}
