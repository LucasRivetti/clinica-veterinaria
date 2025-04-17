package clinicaVeterinaria.modelo;

public class Cliente extends Entidade {
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private String endereco;

    public Cliente(int id, String nome, String telefone, String email, String cpf, String endereco) {
        super(id);
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public Cliente() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return  "   Id = " + getId() +
                "\n   Nome = " + nome +
                "\n   Telefone = " + telefone + 
                "\n   Email = " + email + 
                "\n   Cpf = " + cpf + 
                "\n   Endereco = " + endereco;
    }
}
