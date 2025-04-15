# Clínica Veterinária

Este projeto é um sistema simples de gerenciamento para uma clínica veterinária, desenvolvido em Java utilizando conceitos de Programação Orientada a Objetos (POO). O projeto organiza o código em pacotes (`modelo`, `persistencia`, `visao`) e utiliza uma interface de linha de comando para interação com o usuário.

## Estrutura do Projeto

clinica-veterinaria/ 
├── src/ 
│ └── clinicaVeterinaria/ 
│ ├── modelo/ 
│ │ ├── Entidade.java 
│ │ ├── Animal.java 
│ │ ├── Veterinario.java 
│ │ ├── Consulta.java 
│ │ └── ProcedimentoConsulta.java 
│ ├── persistencia/ 
│ │ ├── Persistente.java 
│ │ ├── BancoDeDados.java 
│ │ └── EntidadeNaoEncontradaException.java 
│ ├── visao/ 
│ │ ├── MenuPrincipal.java 
│ │ ├── MenuAnimal.java 
│ │ ├── MenuVeterinario.java 
│ │ └── MenuConsulta.java 
│ └── Programa.java 
└── README.md

## Como Compilar e Executar

### Pré-requisitos
- Java Development Kit (JDK) instalado (versão 8 ou superior)
- Uma IDE Java (VScode, IntelliJ) ou o terminal/linha de comando para compilar e executar

### Compilando pelo Terminal

1. Abra o terminal e navegue até a pasta raiz do projeto:

   ```bash
   cd /caminho/para/clinica-veterinaria

2. Compile todas as classes. Supondo que seu código-fonte está na pasta src, execute:

javac -d bin src/clinicaVeterinaria/**/*.java

3. Execute o programa a partir da pasta bin:

cd bin
java clinicaVeterinaria.Programa

## Executando pela IDE

Importe o projeto como um "Java Project" na sua IDE favorita.

Certifique-se de que o JDK esteja corretamente configurado.

Localize a classe Programa.java no pacote clinicaVeterinaria e execute o método main.

A interface de linha de comando será iniciada no console da IDE.