package clinicaVeterinaria.modelo;

public class Veterinario extends Entidade {
    private String nome;
    private String crmv;
    private String especialidade;
    private String telefone;
    private String email;

    public Veterinario(int id, String nome, String crmv, String especialidade, String telefone, String email) {
        super(id);
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
    }

    public Veterinario() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
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

    @Override
    public String toString() {
        return  "   Id= " + getId() +
                "\n   Nome= " + nome +
                "\n   Crmv= " + crmv +
                "\n   Especialidade= " + especialidade +
                "\n   Telefone= " + telefone +
                "\n   Email= " + email;
    }
}
