package br.com.nadsen.desafioeveris.service;

import br.com.nadsen.desafioeveris.controller.request.ContaRequest;
import br.com.nadsen.desafioeveris.exceptions.ContaNaoEncontradaException;
import br.com.nadsen.desafioeveris.model.Cliente;
import br.com.nadsen.desafioeveris.model.Conta;
import br.com.nadsen.desafioeveris.model.enums.TipoConta;
import br.com.nadsen.desafioeveris.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private  ClienteService clienteService;
    @Mock
    private ContaRepository contaRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final long id = 1L;
    private static final String cpf = "11370729626";
    private static final int numConta = 2222;
    private static final TipoConta tipoConta = TipoConta.PESSOA_FISICA;
    private static final int digitoVerificador = 2;
    private static final double saldo = 1000.0;
    private static final double DELTA = 1e-15;


//    @Test
//    public void testSalvar_DeveraRetornarSucesso(){
//        Conta conta = new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null);
//        ContaRequest contaRequest = modelMapper.map(conta, ContaRequest.class);
//        contaService.save(contaRequest);
//        verify(contaRepository, times(1)).save(conta);
//    }

    @Test
    public void testGetSaldo(){
        Conta conta = new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null);
        when(contaRepository.getByNumConta(numConta)).thenReturn(conta);
        double saldoEncontrado = contaService.getSaldo(numConta);
        Assert.assertEquals(saldoEncontrado, saldo, DELTA);

    }

    @Test()
    public void testBuscaPorNumConta_DeveraRetornarSucesso(){
        Conta conta = new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null);
        when(contaRepository.findByNumConta(numConta)).thenReturn(Optional.of(conta));
        Conta contaEncontrada = contaService.findByNumConta(numConta);
        Assert.assertEquals(contaEncontrada, conta);
    }

    @Test()
    public void testBuscaPorNumConta_DeveraRetornarExceptionCasoNaoExista(){
        Assert.assertThrows(ContaNaoEncontradaException.class, () -> contaService.findByNumConta(numConta));
    }

    @Test()
    public void testBuscarTodasContas_DeveraRetornarUmaListaDeContas(){
        List<Conta> contaList = Arrays.asList(new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null),
                new Conta(2L, 12313, TipoConta.PESSOA_FISICA, 3, 3000, null));
        when(contaRepository.findAll()).thenReturn(contaList);
        List<Conta> contasEncontradas = contaService.findAll(null);
        Assert.assertEquals(contasEncontradas, contaList);
    }

//    @Test()
//    public void testDelete_DeveraRetornarSucesso(){
//        Conta conta = new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null);
//        when(contaService.findByNumConta(numConta)).thenReturn(Optional.of(conta));
//        contaService.delete(numConta);
//        verify(contaService, times(1)).delete(conta.getNumConta());
//    }

    @Test()
    public void testDelete_DeveraRetornarExceptionCasoNaoExista(){
        Conta conta = new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null);
        Assert.assertThrows(ContaNaoEncontradaException.class, () -> contaService.delete(numConta));
    }

    @Test()
    public void testBuscarCliente_DeveraRetornarSucesso(){
        List<Conta> contas = Arrays.asList(new Conta(1L, numConta, tipoConta, digitoVerificador, saldo, null),
                new Conta(2L, 12313, TipoConta.PESSOA_FISICA, 3, 3000, null));
        when(contaRepository.findByClienteId(id)).thenReturn(new ArrayList<>(contas));
        List<Conta> contasEncontradas = contaService.findAllByIdCliente(id);
        Assert.assertEquals(contasEncontradas, contas);
    }

}
