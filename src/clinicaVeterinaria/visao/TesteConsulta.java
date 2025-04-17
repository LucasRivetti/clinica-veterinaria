//Arquivo de teste para ver o funcionamento do package clinicaVeterinaria.modelo antes de continuar a implementação do sistema no package clinicaVeterinaria.persistencia

package clinicaVeterinaria.visao;

import clinicaVeterinaria.modelo.*;

import java.util.Date;

public class TesteConsulta {
    public static void main(String[] args) {

        Cliente cliente = new Cliente(1, "Lucas Rivetti", "(32)99915-8089", "lucasrivetti@gmail.com", "13857686650", "Travessa Professora Beatriz Albergaria, 47");

        Veterinario veterinario = new Veterinario(1, "Dra. Fernanda Lima", "CRMV-12345", "Clínico Geral", "(32)98888-7777", "fernanda.vet@clinicavet.com");

        Animal animal = new Animal(1, "Nick", "Gato", "SRD", 5, "M", cliente);

        Consulta consulta = new Consulta(1, cliente, veterinario, animal, new Date(), "Consulta de rotina e vacinação.");

        consulta.adicionarItem(new ItemConsulta("Vacinação antirrábica", 80.0));
        consulta.adicionarItem(new ItemConsulta("Exame de sangue", 120.0));

        System.out.println("\n=== DADOS DO CLIENTE ===");
        System.out.println(cliente.toString());

        System.out.println("\n=== DADOS DO VETERINÁRIO ===");
        System.out.println(veterinario.toString());

        System.out.println("\n=== DADOS DO ANIMAL ===");
        System.out.println(animal.toString());

        System.out.println("\n=== DADOS DA CONSULTA ===");
        System.out.println(consulta.toString());

        System.out.println("\n=== ITENS DA CONSULTA ===");
        for (ItemConsulta item : consulta.getItens()) {
            System.out.println(item);
        }

        System.out.printf("\n   Valor Total da Consulta: R$ %.2f\n\n", consulta.calcularValorTotal());
    }
}
