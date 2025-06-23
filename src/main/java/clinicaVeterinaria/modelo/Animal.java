package clinicaVeterinaria.modelo;

public class Animal extends Entidade {
        private String nome; // Nome do animal
        private String especie; // Espécie do animal (cão, gato.)
        private String raca; // Raça do animal (Labrador, Persa.)
        private int idade;
        private String sexo; // Sexo do animal (ex: M, F)
        private Cliente dono;

    public Animal(int id, String nome, String especie, String raca, int idade, String sexo, Cliente dono) {
        super(id);
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.dono = dono;
    }

    public Animal() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;

    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    @Override
    public String toString() {
        return  "   Id= " + getId() +
                "\n   Nome = " + nome +
                "\n   Especie = " + especie +
                "\n   Raca = " + raca +
                "\n   Idade = " + idade +
                "\n   Sexo = " + sexo +
                "\n   Dono = " + (dono != null ? dono.getNome() : "null");
    }
}
