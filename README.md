# Desafio: Cadastro de Pets

Implementação em Java CLI do desafio [`karilho/desafioCadastro`](https://github.com/karilho/desafioCadastro).

O sistema permite cadastrar, listar, buscar, alterar e deletar pets por meio do terminal, utilizando leitura de arquivo `.txt`, orientação a objetos, validações de entrada e persistência em arquivos.

## Objetivo do projeto

Este projeto foi desenvolvido para atender ao desafio de criação de um sistema de cadastro de pets para um abrigo de animais, seguindo as regras definidas no repositório original.

## Tecnologias e conceitos utilizados

- Java 21.
- Programação orientada a objetos.
- Leitura e escrita de arquivos com Java IO/NIO.
- `enum` para tipo e sexo do pet.
- Tratamento de exceções.
- Validação de dados de entrada.

## Funcionalidades implementadas

- Leitura obrigatória do arquivo `formulario.txt`.
- Exibição do menu inicial no terminal.
- Validação da opção do menu para aceitar apenas números válidos.
- Cadastro de novo pet a partir das perguntas do formulário.
- Armazenamento do cadastro em objeto `Pet`.
- Salvamento de cada pet em um arquivo `.TXT`.
- Listagem de todos os pets cadastrados.
- Busca de pets por critérios.
- Alteração de pet cadastrado.
- Exclusão de pet com confirmação.

## Requisitos do desafio atendidos

### Passo 1 - Leitura do arquivo de perguntas

- O projeto possui o arquivo [formulario.txt](/C:/Users/laris/Downloads/desafioCadastro/formulario.txt).
- As perguntas são lidas do arquivo e exibidas no terminal.
- As perguntas principais não foram fixadas com `print` direto no código.

### Passo 2 - Menu inicial

- O menu apresenta as 6 opções pedidas no desafio.
- O sistema não aceita letras ou caracteres especiais na escolha do menu.
- O sistema não aceita `0` nem números negativos.
- Em caso de opção inválida, o menu volta a ser exibido.

### Passo 3 - Cadastro de novos pets

- O cadastro cria um objeto `Pet`.
- Nome e sobrenome são obrigatórios.
- Nome completo aceita apenas letras e espaços.
- Tipo e sexo utilizam `enum`.
- Endereço é coletado separando número, cidade e rua.
- Idade e peso aceitam número com ponto ou vírgula.
- Peso maior que `60` ou menor que `0.5` gera exceção.
- Idade maior que `20` gera exceção.
- Idade informada entre `1` e `11` é convertida para anos decimais.
- Raça aceita apenas letras e espaços.
- Os campos previstos pelo desafio como opcionais são preenchidos com a constante `NAO INFORMADO`.

### Passo 4 - Armazenamento em arquivo

- Cada pet é salvo dentro da pasta [petsCadastrados](/C:/Users/laris/Downloads/desafioCadastro/petsCadastrados).
- O nome do arquivo segue o padrão `yyyyMMddTHHmm-NOMECOMPLETO.TXT`.
- O nome do pet vai em maiúsculas no nome do arquivo.
- O arquivo salvo contém apenas respostas.
- Cada resposta fica em uma linha.
- O endereço é salvo em uma única linha.

Exemplo de nome de arquivo gerado:

```text
20260411T0321-REXDASILVA.TXT
```

Exemplo de conteúdo salvo:

```text
Rex da Silva
Cachorro
Macho
Rua das Flores, 123, São Paulo
0.5 anos
5.5kg
Vira-lata
```

### Passo 5 - Busca de pets cadastrados

- O sistema exige primeiro o tipo do animal.
- Depois, permite buscar com `1` ou `2` critérios adicionais.
- Os critérios disponíveis são:

```text
Nome ou sobrenome
Sexo
Idade
Peso
Raça
Endereço
```

- A busca por nome e raça aceita partes do texto.
- A busca ignora diferença entre maiúsculas e minúsculas.
- A busca também ignora acentos.
- Os resultados são exibidos em formato de lista numerada.

### Passo 6 - Alteração de pet:

- A alteração começa por uma busca.
- O usuário escolhe o pet em uma lista numerada.
- Todos os dados podem ser alterados, exceto tipo e sexo.

### Passo 7 - Exclusão de pet:

- A exclusão começa por uma busca.
- O usuário escolhe um pet da lista.
- A exclusão pede confirmação com `SIM` ou `NAO`.
- Apenas um pet é excluído por vez.

### Passo 8 - Sair:

- Ao escolher a opção `6`, o programa é encerrado.

## O que não foi implementado:

- Passo extra de manutenção dinâmica do `formulario.txt`.
- Destaque em negrito do termo pesquisado nos resultados.
- Busca por data de cadastro.

## Estrutura do Projeto:

```text
desafioCadastro/
|-- formulario.txt
|-- petsCadastrados/
|-- src/
|   `-- cadastropets/
|       |-- Aplicacao.java
|       |-- modelo/
|       |-- servico/
|       `-- util/
|-- .gitignore
`-- README.md
```

## Como compilar:

No PowerShell, dentro da raiz do projeto:

```powershell
New-Item -ItemType Directory -Force -Path out | Out-Null
javac -d out (Get-ChildItem -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
```

## Como executar

```powershell
java -cp out cadastropets.Aplicacao
```

## Exemplo de uso

1. Execute a aplicação.
2. Escolha a opção `1` para cadastrar um pet.
3. Responda às perguntas lidas do arquivo `formulario.txt`.
4. O sistema criará um arquivo na pasta `petsCadastrados`.
5. Use as opções `4` e `5` para listar e buscar os pets cadastrados.
6. Use as opções `2` e `3` para alterar ou excluir um pet.

## Regras importantes aplicadas

- Nome completo deve conter nome e sobrenome.
- Nome e raça não aceitam números nem caracteres especiais.
- Tipo e sexo são controlados por `enum`.
- O menu aceita apenas números válidos.
- Campos em branco definidos pelo desafio recebem `NAO INFORMADO`.
- O projeto foi estruturado com separação entre modelo, serviço e utilitários.

## Observações finais

Este projeto cobre o fluxo principal solicitado no desafio original e pode ser executado localmente apenas com o JDK instalado, sem necessidade de Maven ou Gradle.
