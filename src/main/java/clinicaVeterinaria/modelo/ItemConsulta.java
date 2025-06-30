
package clinicaVeterinaria.modelo;
public class ItemConsulta {
    private Procedimento procedimento;
    private double preco;
    private int quantidade;

    public ItemConsulta(Procedimento procedimento, double preco, int quantidade) {
        this.procedimento = procedimento;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return  "   Procedimento = " + procedimento.getNome() +
                "\n   Preço = " + preco;
    }
}
