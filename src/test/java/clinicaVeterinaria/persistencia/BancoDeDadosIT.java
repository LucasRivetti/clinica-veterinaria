package clinicaVeterinaria.persistencia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clinicaVeterinaria.modelo.Cliente;

import java.util.List;

class BancoDeDadosIT {

    private BancoDeDados banco;

    @BeforeEach
    void setup() {
        banco = new BancoDeDados();
    }

    @Test
    void testRepositoriosInstanciados() {
        assertNotNull(banco.getClientes(),      "Repositório de clientes não inicializado");
        assertNotNull(banco.getVeterinarios(), "Repositório de veterinários não inicializado");
        assertNotNull(banco.getAnimais(),      "Repositório de animais não inicializado");
        assertNotNull(banco.getConsultas(),    "Repositório de consultas não inicializado");
        assertNotNull(banco.getProcedimentos(),"Repositório de procedimentos não inicializado");
    }

    @Test
    void testCrudViaBancoDeDados() throws IdInexistenteExcecao {
        // 1) Adicionar e listar
        Cliente c1 = new Cliente(1, "Teste", "0000-0000", "t@t.com", "00000000000", "Rua X");
        banco.getClientes().adicionar(c1);

        List<Cliente> todos = banco.getClientes().listar();
        assertEquals(1, todos.size());
        assertSame(c1, todos.get(0));

        // 2) Buscar e atualizar
        Cliente buscado = banco.getClientes().buscarPorId(1);
        assertSame(c1, buscado);

        c1.setNome("Alterado");
        banco.getClientes().atualizar(c1);
        assertEquals("Alterado", banco.getClientes().buscarPorId(1).getNome());

        // 3) Remover e verificar exceção ao buscar
        banco.getClientes().remover(1);
        assertThrows(IdInexistenteExcecao.class,
                     () -> banco.getClientes().buscarPorId(1));
    }
}
