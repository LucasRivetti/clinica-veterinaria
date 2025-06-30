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

public class TesteConsulta {
    public static void main(String[] args) {

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

        Consulta consulta = new Consulta(
            1,
            cliente,
            veterinario,
            animal,
            new Date(),
            itensConsulta, 
            "Consulta de rotina e vacinação."
        );

        consulta.adicionarItem(new ItemConsulta(procVacina, procVacina.getPreco(), 1));
        consulta.adicionarItem(new ItemConsulta(procExame, procExame.getPreco(), 1));

        System.out.println("\n=== DADOS DO CLIENTE ===");
        System.out.println(cliente);

        System.out.println("\n=== DADOS DO VETERINÁRIO ===");
        System.out.println(veterinario);

        System.out.println("\n=== DADOS DO ANIMAL ===");
        System.out.println(animal);

        System.out.println("\n=== DADOS DA CONSULTA ===");
        System.out.println(consulta);

        System.out.println("\n=== ITENS DA CONSULTA ===");
        for (ItemConsulta item : consulta.getItens()) {
            System.out.println(item);
        }

        System.out.printf("\n   Valor Total da Consulta: R$ %.2f\n\n", consulta.calcularValorTotal());
    }
}
