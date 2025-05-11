package clinicaVeterinaria.modelo;

public class Procedimento extends Entidade {
    private String nome;
    private double preco;

    public Procedimento(int id, String nome, double preco) {
        super(id);
        this.nome = nome;
        this.preco = preco;
    }

    public Procedimento() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "   Id=" + getId() +
               "\n   Procedimento=" + nome +
               "\n   Preço=" + preco;
    }
}
