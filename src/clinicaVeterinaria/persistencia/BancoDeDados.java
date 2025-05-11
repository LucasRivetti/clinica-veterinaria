package clinicaVeterinaria.persistencia;

import clinicaVeterinaria.modelo.*;

public class BancoDeDados {
    private Persistente<Cliente> clientes;
    private Persistente<Veterinario> veterinarios;
    private Persistente<Animal> animais;
    private Persistente<Consulta> consultas;
    private Persistente<Procedimento> procedimentos;

    public BancoDeDados() {
        this.clientes = new Persistente<>();
        this.veterinarios = new Persistente<>();
        this.animais = new Persistente<>();
        this.consultas = new Persistente<>();
        this.procedimentos = new Persistente<>();
    }

    public Persistente<Cliente> getClientes() { 
        return clientes; }
    public Persistente<Veterinario> getVeterinarios() { 
        return veterinarios; }
    public Persistente<Animal> getAnimais() { 
        return animais; }
    public Persistente<Consulta> getConsultas() { 
        return consultas; }
    public Persistente<Procedimento> getProcedimentos() { 
        return procedimentos; }
    }