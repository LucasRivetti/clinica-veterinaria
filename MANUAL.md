# Manual de Uso

## Requisitos
- Java 8 ou superior instalado.  
- Terminal ou prompt de comando.

## Instalação

Clone ou baixe o repositório:

```bash
cd clinica-veterinaria
```

## Compilação

```bash
javac -d bin src/clinicaVeterinaria/**/*.java
```

## Execução

```bash
cd bin
java clinicaVeterinaria.Programa
```

## Fluxo de Uso

1. **Menu Principal**  
   - Escolha entre Clientes, Veterinários, Animais ou Consultas.

2. **Submenus**  
   - **Cadastrar**: informe os dados solicitados.  
   - **Alterar**: informe o ID e depois os novos dados.  
   - **Remover**: informe o ID.  
   - **Buscar**: informe o ID.  
   - **Listar**: exibe todas as entidades do tipo.

3. **Exemplo de Cadastro de Consulta**  
   - Informe:  
     - ID da consulta  
     - ID do Cliente  
     - ID do Veterinário  
     - ID do Animal  
     - Data e hora (formato padrão `Tue May 12 14:30:00 BRT 2025`)  
     - Descrição  
   - **Loop de Itens**:  
     - Ao confirmar, será perguntado se deseja adicionar um procedimento.  
     - Informe nome e preço do procedimento.  
     - Repita até não desejar mais itens.

4. **Sair do Programa**  
   - No menu principal, escolha `0` para encerrar.

## Dicas

- Utilize o comando **Listar** antes de **Cadastrar** para verificar IDs já usados.  
- Anote os **IDs** criados para facilitar buscas e alterações.  
- Em caso de erro de **ID inexistente**, revise as Listagens ou cadastre um novo registro.

## Suporte

Para dúvidas ou sugestões, contate:  
- Lucas Rivetti (lucassantosrivetti@gmail.com)  
 
