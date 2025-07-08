package clinicaVeterinaria.visao.InterfaceTerminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clinicaVeterinaria.modelo.Animal;
import clinicaVeterinaria.modelo.Cliente;
import clinicaVeterinaria.modelo.Consulta;
import clinicaVeterinaria.modelo.ItemConsulta;
import clinicaVeterinaria.modelo.Procedimento;
import clinicaVeterinaria.modelo.Veterinario;
import clinicaVeterinaria.persistencia.BancoDeDados;


public class TesteBd {
    
    private BancoDeDados bancoDeDados;

    public TesteBd(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void inicializarBanco(BancoDeDados banco) {

        Procedimento procVacina = new Procedimento(1, "Vacinação antirrábica", 80.0);
        Procedimento procExame = new Procedimento(2, "Exame de sangue", 120.0);
        //banco.getProcedimentos().adicionar(new Procedimento(3, "Castração - Cães pequenos", 350.0));
        //banco.getProcedimentos().adicionar(new Procedimento(4, "Castração - Cães grandes", 450.0));
        //banco.getProcedimentos().adicionar(new Procedimento(5, "Castração - Gatos", 300.0));
        //banco.getProcedimentos().adicionar(new Procedimento(6, "Exame de sangue completo", 180.0));
        //banco.getProcedimentos().adicionar(new Procedimento(7, "Ultrassonografia", 250.0));
        //banco.getProcedimentos().adicionar(new Procedimento(8, "Raio-X", 150.0));
        //banco.getProcedimentos().adicionar(new Procedimento(9, "Limpeza dentária", 200.0));
        //banco.getProcedimentos().adicionar(new Procedimento(10, "Cirurgia de fratura", 800.0));
        //banco.getProcedimentos().adicionar(new Procedimento(11, "Internação diária", 100.0));
        //banco.getProcedimentos().adicionar(new Procedimento(12, "Vacina V8/V10", 90.0));
        //banco.getProcedimentos().adicionar(new Procedimento(13, "Vacina contra leishmaniose", 120.0));
        //banco.getProcedimentos().adicionar(new Procedimento(14, "Microchipagem", 150.0));
        //banco.getProcedimentos().adicionar(new Procedimento(15, "Banho e tosa - Pequeno", 60.0));
        //banco.getProcedimentos().adicionar(new Procedimento(16, "Banho e tosa - Grande", 90.0));
        //banco.getProcedimentos().adicionar(new Procedimento(17, "Vermifugação", 45.0));
        //banco.getProcedimentos().adicionar(new Procedimento(18, "Aplicação de antipulgas", 70.0));
        //banco.getProcedimentos().adicionar(new Procedimento(19, "Curativo pós-operatório", 55.0));
        //banco.getProcedimentos().adicionar(new Procedimento(20, "Teste de FIV/FeLV (gatos)", 130.0));
        //banco.getProcedimentos().adicionar(new Procedimento(21, "Eutanásia humanitária", 200.0));

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
            "(32)98888-7777",
            "fernanda.vet@clinicavet.com",
            "CRMV-12345",
            "Clínico Geral"
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
        ItemConsulta itemProcVacina = new ItemConsulta(procVacina, procVacina.getPreco(), 1);
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

        //banco.getProcedimentos().adicionar(procVacina);
        //banco.getProcedimentos().adicionar(procExame);
        //banco.getClientes().adicionar(cliente);
        //banco.getVeterinarios().adicionar(veterinario);
        //banco.getAnimais().adicionar(animal);
        //banco.getConsultas().adicionar(consulta);
    }
}
