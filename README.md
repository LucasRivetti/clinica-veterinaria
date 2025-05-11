# Clínica Veterinária

**Trabalho desenvolvido para a disciplina Programação Modular, do professor Matheus Viana.**

**Membros da equipe**:  
- Lucas Rivetti
- Jose Faria  
- Augusto Cezar  
- Lucas Campelo  

Este projeto é um sistema simples de gerenciamento para uma clínica veterinária, utilizando Java e interface de linha de comando (CLI).

## Funcionalidades Principais

- Cadastro, edição, remoção e listagem de **Clientes**  
- Cadastro, edição, remoção e listagem de **Veterinários**  
- Cadastro, edição, remoção e listagem de **Animais**  
- Cadastro, edição, remoção e listagem de **Consultas**, com itens de procedimento e cálculo de valor total  

## Tecnologias Utilizadas

- Java 8+  
- IDEs compatíveis (Eclipse, IntelliJ IDEA, VS Code)  
- Sistema de build simples via linha de comando (javac + java)  

## Estrutura do Projeto

clinica-veterinaria/
└── src/
    └── clinicaVeterinaria/
        ├── modelo/
        │   ├── Entidade.java
        │   ├── Cliente.java
        │   ├── Veterinario.java
        │   ├── Animal.java
        │   ├── Procedimento.java
        │   ├── Consulta.java
        │   └── ItemConsulta.java
        ├── persistencia/
        │   ├── Persistente.java
        │   ├── BancoDeDados.java
        │   └── IdInexistenteExcecao.java
        ├── visao/
        │   ├── MenuPrincipal.java
        │   ├── MenuCliente.java
        │   ├── MenuVeterinario.java
        │   ├── MenuAnimal.java
        │   ├── MenuConsulta.java
        │   └── TesteConsulta.java
        └── Programa.java

## Compilação e Execução

```bash
# Compilar todos os fontes em src para a pasta bin
javac -d bin src/clinicaVeterinaria/**/*.java

# Executar a aplicação
cd bin
java clinicaVeterinaria.Programa
```

## Executando pela IDE

1. Importe o projeto como um "Java Project".
2. Configure o JDK no seu ambiente.
3. Execute o método `main` da classe `clinicaVeterinaria.Programa`.
