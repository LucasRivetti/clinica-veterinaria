package clinicaVeterinaria.modelo;

public class ItemConsulta {
    private String procedimento; // vacinação, exame, etc.
    private double preco;

    public ItemConsulta(String procedimento, double preco) {
        this.procedimento = procedimento;
        this.preco = preco;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
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
        return  "   procedimento = " + procedimento +
                "   preco = " + preco;
    }
}
