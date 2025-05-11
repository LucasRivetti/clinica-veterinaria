package clinicaVeterinaria.modelo;

import java.util.ArrayList;
import java.util.List;

public class Veterinario extends Entidade {
    private String nome;
    private String crmv;
    private String especialidade;
    private String telefone;
    private String email;
    private List<Consulta> consultas;

    public Veterinario() {
        super();
        this.consultas = new ArrayList<>();
    }

    public Veterinario(int id, String nome, String crmv, String especialidade, String telefone, String email) {
        super(id);
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.email = email;
        this.consultas = new ArrayList<>();
    }

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

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public void removerConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
    }

    @Override
    public String toString() {
        return  "   Id = " + getId() +
                "\n   Nome = " + nome +
                "\n   Crmv = " + crmv +
                "\n   Especialidade = " + especialidade +
                "\n   Telefone = " + telefone +
                "\n   Email = " + email;
    }
}