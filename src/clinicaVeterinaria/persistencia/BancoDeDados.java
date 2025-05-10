package clinicaVeterinaria.persistencia;

import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.modelo.Animal;
import clinicaVeterinaria.modelo.Consulta;

public class BancoDeDados {
    private Persistente<Veterinario> veterinarios;
    private Persistente<Cliente> clientes;
    private Persistente<Animal> animais;
    private Persistente<Consulta> consultas;

    public BancoDeDados() {
        this.veterinarios = new Persistente<>();
        this.clientes = new Persistente<>();
        this.animais = new Persistente<>();
        this.consultas = new Persistente<>();
    }

    public Persistente<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public Persistente<Cliente> getClientes() {
        return clientes;
    }

    public Persistente<Animal> getAnimais() {
        return animais;
    }

    public Persistente<Consulta> getConsultas() {
        return consultas;
    }
}