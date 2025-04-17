package clinicaVeterinaria.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Consulta extends Entidade {
    private Cliente cliente;
    private Veterinario veterinario;
    private Animal animal;
    private Date dataHora;
    private String descricao;
    private List<ItemConsulta> itens;

    public Consulta(int id, Cliente cliente, Veterinario veterinario, Animal animal, Date dataHora, String descricao) {
        super(id);
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.animal = animal;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemConsulta item) {
        itens.add(item);
    }

    public void removerItem(ItemConsulta item) {
        itens.remove(item);
    }

    public double calcularValorTotal() {
        return itens.stream().mapToDouble(ItemConsulta::getPreco).sum();
    }

    public List<ItemConsulta> getItens() {
        return itens;
    }

    public void setItens(List<ItemConsulta> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return  "   Id = " + getId() +
                "\n   Cliente = " + cliente.getNome() +
                "\n   Veterinario = " + veterinario.getNome() +
                "\n   Animal = " + animal.getNome() +
                "\n   DataHora = " + dataHora +
                "\n   Descricao = " + descricao +
                "\n   ValorTotal = " + calcularValorTotal();
    }
}
