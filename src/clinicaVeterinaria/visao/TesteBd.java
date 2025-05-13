package clinicaVeterinaria.visao;

import clinicaVeterinaria.modelo.*;
import clinicaVeterinaria.persistencia.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TesteBd {
    
    private BancoDeDados bancoDeDados;

    public TesteBd(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void inicializarBanco(BancoDeDados banco) {

        Procedimento procVacina = new Procedimento(1, "Vacinação antirrábica", 80.0);
        Procedimento procExame = new Procedimento(2, "Exame de sangue", 120.0);

        Cliente cliente = new Cliente(
            1,
            "Lucas Rivetti",
            "(32)99915-8089",
            "lucasrivetti@gmail.com",
            "13857686650",
            "Travessa Professora Beatriz Albergaria, 47"
        );

        Veterinario veterinario = new Veterinario(
            1,
            "Dra. Fernanda Lima",
            "CRMV-12345",
            "Clínico Geral",
            "(32)98888-7777",
            "fernanda.vet@clinicavet.com"
        );

        Animal animal = new Animal(
            1,
            "Nick",
            "Gato",
            "SRD",
            5,
            "M",
            cliente
        );
        
        List<ItemConsulta> itensConsulta = new ArrayList<>();
        ItemConsulta itemProcVacina = new ItemConsulta(procVacina, procVacina.getPreco());
        itensConsulta.add(itemProcVacina);

        Consulta consulta = new Consulta(
            1,
            cliente,
            veterinario,
            animal,
            new Date(),
            itensConsulta,
            "Consulta de rotina e vacinação."
        );

        banco.getProcedimentos().adicionar(procVacina);
        banco.getProcedimentos().adicionar(procExame);
        banco.getClientes().adicionar(cliente);
        banco.getVeterinarios().adicionar(veterinario);
        banco.getAnimais().adicionar(animal);
        banco.getConsultas().adicionar(consulta);
    }
}
