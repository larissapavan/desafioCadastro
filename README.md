# Desafio Cadastro de Pets

Implementacao em Java CLI do desafio [`karilho/desafioCadastro`](https://github.com/karilho/desafioCadastro).

O sistema permite cadastrar, listar, buscar, alterar e deletar pets por meio do terminal, utilizando leitura de arquivo `.txt`, orientacao a objetos, validacoes de entrada e persistencia em arquivos.

## Objetivo do projeto

Este projeto foi desenvolvido para atender ao desafio de criacao de um sistema de cadastro de pets para um abrigo de animais, seguindo as regras definidas no repositório original.

## Tecnologias e conceitos utilizados

- Java 21
- Programacao orientada a objetos
- Leitura e escrita de arquivos com Java IO/NIO
- `enum` para tipo e sexo do pet
- Tratamento de excecoes
- Validacao de dados de entrada

## Funcionalidades implementadas

- Leitura obrigatoria do arquivo `formulario.txt`.
- Exibicao do menu inicial no terminal.
- Validacao de opcao do menu para aceitar apenas numeros validos.
- Cadastro de novo pet a partir das perguntas do formulario.
- Armazenamento do cadastro em objeto `Pet`.
- Salvamento de cada pet em um arquivo `.TXT`.
- Listagem de todos os pets cadastrados.
- Busca de pets por criterios.
- Alteracao de pet cadastrado.
- Exclusao de pet com confirmacao.

## Requisitos do desafio atendidos

### Passo 1 - Leitura do arquivo de perguntas

- O projeto possui o arquivo [formulario.txt](/C:/Users/laris/Downloads/desafioCadastro/formulario.txt).
- As perguntas sao lidas do arquivo e exibidas no terminal.
- As perguntas principais nao foram fixadas com `print` direto no codigo.

### Passo 2 - Menu inicial

- O menu apresenta as 6 opcoes pedidas no desafio.
- O sistema nao aceita letras ou caracteres especiais na escolha do menu.
- O sistema nao aceita `0` nem numeros negativos.
- Em caso de opcao invalida, o menu volta a ser exibido.

### Passo 3 - Cadastro de novos pets

- O cadastro cria um objeto `Pet`.
- Nome e sobrenome sao obrigatorios.
- Nome completo aceita apenas letras e espacos.
- Tipo e sexo utilizam `enum`.
- Endereco e coletado separando numero, cidade e rua.
- Idade e peso aceitam numero com ponto ou virgula.
- Peso maior que `60` ou menor que `0.5` gera excecao.
- Idade maior que `20` gera excecao.
- Idade informada entre `1` e `11` e convertida para anos decimais.
- Raca aceita apenas letras e espacos.
- Os campos previstos pelo desafio como opcionais sao preenchidos com a constante `NAO INFORMADO`.

### Passo 4 - Armazenamento em arquivo

- Cada pet e salvo dentro da pasta [petsCadastrados](/C:/Users/laris/Downloads/desafioCadastro/petsCadastrados).
- O nome do arquivo segue o padrao `yyyyMMddTHHmm-NOMECOMPLETO.TXT`.
- O nome do pet vai em maiusculas no nome do arquivo.
- O arquivo salvo contem apenas respostas.
- Cada resposta fica em uma linha.
- O endereco e salvo em uma unica linha.

Exemplo de nome de arquivo gerado:

```text
20260411T0321-REXDASILVA.TXT
```

Exemplo de conteudo salvo:

```text
Rex da Silva
Cachorro
Macho
Rua das Flores, 123, Sao Paulo
0.5 anos
5.5kg
Viralata
```

### Passo 5 - Busca de pets cadastrados

- O sistema exige primeiro o tipo do animal.
- Depois permite buscar com `1` ou `2` criterios adicionais.
- Os criterios disponiveis sao:

```text
Nome ou sobrenome
Sexo
Idade
Peso
Raca
Endereco
```

- A busca por nome e raca aceita partes do texto.
- A busca ignora diferenca entre maiusculas e minusculas.
- A busca tambem ignora acentos.
- Os resultados sao exibidos em formato de lista numerada.

### Passo 6 - Alteracao de pet

- A alteracao comeca por uma busca.
- O usuario escolhe o pet em uma lista numerada.
- Todos os dados podem ser alterados, exceto tipo e sexo.

### Passo 7 - Exclusao de pet

- A exclusao comeca por uma busca.
- O usuario escolhe um pet da lista.
- A exclusao pede confirmacao com `SIM` ou `NAO`.
- Apenas um pet e excluido por vez.

### Passo 8 - Sair

- Ao escolher a opcao `6`, o programa e encerrado.

## O que nao foi implementado

- Passo extra de manutencao dinamica do `formulario.txt`.
- Destaque em negrito do termo pesquisado nos resultados.
- Busca por data de cadastro.

## Estrutura do projeto

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

## Como compilar

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

1. Execute a aplicacao.
2. Escolha a opcao `1` para cadastrar um pet.
3. Responda as perguntas lidas do arquivo `formulario.txt`.
4. O sistema criara um arquivo na pasta `petsCadastrados`.
5. Use as opcoes `4` e `5` para listar e buscar os pets cadastrados.
6. Use as opcoes `2` e `3` para alterar ou excluir um pet.

## Regras importantes aplicadas

- Nome completo deve conter nome e sobrenome.
- Nome e raca nao aceitam numeros nem caracteres especiais.
- Tipo e sexo sao controlados por `enum`.
- O menu aceita apenas numeros validos.
- Campos em branco definidos pelo desafio recebem `NAO INFORMADO`.
- O projeto foi estruturado com separacao entre modelo, servico e utilitarios.

## Observacoes finais

Este projeto cobre o fluxo principal solicitado no desafio original e pode ser executado localmente apenas com o JDK instalado, sem necessidade de Maven ou Gradle.
