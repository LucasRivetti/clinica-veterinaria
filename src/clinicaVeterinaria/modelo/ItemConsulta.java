package clinicaVeterinaria.modelo;

public class ItemConsulta {
    private Procedimento procedimento;
    private double preco;

    public ItemConsulta(Procedimento procedimento, double preco) {
        this.procedimento = procedimento;
        this.preco = preco;
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

    @Override
    public String toString() {
        return  "   Procedimento = " + procedimento.getNome() +
                "\n   Preço = " + preco;
    }
}
