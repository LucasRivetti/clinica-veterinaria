package clinicaVeterinaria.persistencia;

import java.util.ArrayList;
import java.util.List;

import clinicaVeterinaria.modelo.Entidade;

public class Persistente<T extends Entidade> {
    private List<T> listaDeEntidades; //Atributo para armazenar os listaDeEntidades

    public Persistente( ) {
        this.listaDeEntidades = new ArrayList<>();
    }

    public void adicionar(T entidade) {
        this.listaDeEntidades.add(entidade);
    }

    public void remover(int id) throws IdInexistenteExcecao {
        T entidade = buscarPorId(id);
        this.listaDeEntidades.remove(entidade);
    }

   public void atualizar(T entidade) throws IdInexistenteExcecao {
        for (int i = 0; i < listaDeEntidades.size(); i++) {
            if (listaDeEntidades.get(i).getId() == entidade.getId()) {
                listaDeEntidades.set(i, entidade);
                return;
            }
        }
        throw new IdInexistenteExcecao("ID não encontrado: " + entidade.getId());
    }

    public T buscarPorId(int id) throws IdInexistenteExcecao {
        for (T entidade : listaDeEntidades) {
            if (entidade.getId() == id) {
                return entidade;
            }
        }
        throw new IdInexistenteExcecao("ID não encontrado: " + id);
    }

     public List<T> listar() { //ver sobre deixar imodificavel
        return this.listaDeEntidades;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T entidade : listaDeEntidades) {
            sb.append(entidade.toString()).append("\n");
        }
        return sb.toString();
    }
}
