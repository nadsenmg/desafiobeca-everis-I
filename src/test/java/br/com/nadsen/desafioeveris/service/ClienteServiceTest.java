package br.com.nadsen.desafioeveris.service;

import br.com.nadsen.desafioeveris.exceptions.ClienteJaExistenteException;
import br.com.nadsen.desafioeveris.exceptions.ClienteNaoEncontradoException;
import br.com.nadsen.desafioeveris.model.Cliente;
import br.com.nadsen.desafioeveris.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;

	private static final String nome = "Nadsen";
	private static final String cpf = "43903022055";
	private static final String telefone = "(34) 99321-3192";
	private static final String endereco = "Rua A";

	@Test
	public void testSalvar_DeveraRetornarSucesso(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		clienteService.save(cliente);
		verify(clienteRepository, times(1)).save(cliente);
	}

	@Test
	public void testSalvar_DeveraRetornarExceptionCasoJaExista(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		when(clienteRepository.getByCpf(cpf)).thenReturn(cliente);
		Assert.assertThrows(ClienteJaExistenteException.class, () -> clienteService.save(cliente));
	}

	@Test()
	public void testBuscaPorCpf_DeveraRetornarSucesso(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente));
		Cliente clienteEncontrado = clienteService.findByCpf(cpf);
		Assert.assertEquals(clienteEncontrado, cliente);
	}

	@Test()
	public void testBuscaPorCpf_DeveraRetornarExceptionCasoNaoExista(){
		Assert.assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.findByCpf(cpf));
	}

	@Test()
	public void testBuscarTodosClientes_DeveraRetornarUmaListaComClientes(){
		List<Cliente> clienteList = Arrays.asList(new Cliente("Nadsen", "43903022055",
				"34) 99321-3192", "Rua B"), new Cliente("Marcos",
				"1231313131", "(34) 92133-3121","Rua B"));
		when(clienteRepository.findAll()).thenReturn(clienteList);
		List<Cliente> clientesEncontrados = clienteService.findAll(null);
		Assert.assertEquals(clientesEncontrados, clienteList);
	}

	@Test()
	public void testDelete_DeveraRetornarSucesso(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));
		clienteService.delete(cpf);
		verify(clienteRepository, times(1)).delete(cliente);
	}

	@Test()
	public void testDelete_DeveraRetornarExceptionCasoNaoExista(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		Assert.assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.delete(cliente.getCpf()));
	}

	@Test()
	public void testUpdate_DeveraAtualizarComSucesso(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente));
		clienteService.update(cpf, cliente);
		verify(clienteRepository, times(1)).save(cliente);
	}

	@Test()
	public void testUpdate_DeveraRetornarExceptionCasoNaoExista(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		Assert.assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.update(cpf, cliente));
	}

	@Test()
	public void testObterCliente_deveRetornarCliente(){
		Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente));
		Assert.assertEquals(cliente, clienteService.obter(cpf));
	}

}
