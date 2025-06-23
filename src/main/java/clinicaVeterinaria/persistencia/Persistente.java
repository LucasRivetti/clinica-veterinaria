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
    T alvo = buscarPorId(id);
    listaDeEntidades.remove(alvo);
}
   public void atualizar(T entidade) throws IdInexistenteExcecao {
    for (int i = 0; i < listaDeEntidades.size(); i++) {
        if (listaDeEntidades.get(i).getId() == entidade.getId()) {
            listaDeEntidades.set(i, entidade);
            return;
        }
    }
    throw new IdInexistenteExcecao("Não foi possível atualizar: id " + entidade.getId() + " não existe.");
}

    public T buscarPorId(int id) throws IdInexistenteExcecao {
    for (T entidade : listaDeEntidades) {
        if (entidade.getId() == id) {
            return entidade;
        }
    }
    throw new IdInexistenteExcecao("Entidade com id " + id + " não encontrada.");
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
