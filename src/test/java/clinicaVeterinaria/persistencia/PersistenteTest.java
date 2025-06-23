package clinicaVeterinaria.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clinicaVeterinaria.modelo.Entidade;

class PersistenteTest {


    private Persistente<DummyEntidade> repo;

    @BeforeEach
    void setUp() {
        repo = new Persistente<>();
    }

    // Agora extendendo Entidade corretamente
    private static class DummyEntidade extends Entidade {
        private String nome;
        DummyEntidade(int id, String nome) {
            setId(id);
            this.nome = nome;
        }
        String getNome() { return nome; }
        void setNome(String nome) { this.nome = nome; }
        @Override
        public String toString() {
            return "DummyEntidade{id=" + getId() + ", nome='" + nome + "'}";
        }
    }

    @Test
    void testAdicionarEListar() {
        DummyEntidade d1 = new DummyEntidade(1, "A");
        DummyEntidade d2 = new DummyEntidade(2, "B");
        repo.adicionar(d1);
        repo.adicionar(d2);

        List<DummyEntidade> todos = repo.listar();
        assertEquals(2, todos.size());
        assertSame(d1, todos.get(0));
        assertSame(d2, todos.get(1));
    }

    @Test
    void testBuscarPorIdExiste() throws IdInexistenteExcecao {
        DummyEntidade d = new DummyEntidade(10, "X");
        repo.adicionar(d);
        assertSame(d, repo.buscarPorId(10));
    }

    @Test
    void testBuscarPorIdNaoExisteLancaExcecao() {

        assertThrows(
            IdInexistenteExcecao.class,
            () -> repo.buscarPorId(999)
        );
    }

    @Test
    void testAtualizar() throws IdInexistenteExcecao {
        DummyEntidade d = new DummyEntidade(5, "Old");
        repo.adicionar(d);
        d.setNome("New");
        repo.atualizar(d);
        assertEquals("New", repo.buscarPorId(5).getNome());
    }

    @Test
    void testRemover() throws IdInexistenteExcecao {
        DummyEntidade d1 = new DummyEntidade(1, "A");
        DummyEntidade d2 = new DummyEntidade(2, "B");
        repo.adicionar(d1);
        repo.adicionar(d2);

        repo.remover(1);
        List<DummyEntidade> restantes = repo.listar();
        assertEquals(1, restantes.size());
        assertSame(d2, restantes.get(0));

        assertThrows(
            IdInexistenteExcecao.class,
            () -> repo.buscarPorId(1)
        );
    }
}
