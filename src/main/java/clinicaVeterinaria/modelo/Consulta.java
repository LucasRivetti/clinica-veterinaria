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

    public Consulta() {
        super();
        this.itens = new ArrayList<>();
    }

    public Consulta(int id, Cliente cliente, Veterinario veterinario,
                    Animal animal, Date dataHora, List<ItemConsulta> itens, String descricao) {
        super(id);
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.animal = animal;
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.itens = itens;
    } 

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<ItemConsulta> getItens() {
        return itens;
    }

    public void setItens(List<ItemConsulta> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemConsulta item) {
        this.itens.add(item);
    }

    public void removerItem(ItemConsulta item) {
        this.itens.remove(item);
    }

    public double calcularValorTotal() {
        return itens.stream()
            .mapToDouble(item -> item.getPreco() * item.getQuantidade())
            .sum();
    }

    @Override
    public String toString() {
        String resultado =  "   Id = " + getId() +
                "\n   Cliente = " + cliente.getNome() +
                "\n   Veterinario = " + veterinario.getNome() +
                "\n   Animal = " + animal.getNome() +
                "\n   DataHora = " + dataHora +
                "\n   Descricao = " + descricao;

        for (ItemConsulta item : itens) {
            resultado += "\n   Procedimento = " + item.getProcedimento().getNome() +
                        " (R$ " + item.getPreco() + ")";
        }

        resultado += "\n   ValorTotal = " + calcularValorTotal();

        return resultado;
    }
}
