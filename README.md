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

- leitura obrigatoria do arquivo `formulario.txt`
- exibicao do menu inicial no terminal
- validacao de opcao do menu para aceitar apenas numeros validos
- cadastro de novo pet a partir das perguntas do formulario
- armazenamento do cadastro em objeto `Pet`
- salvamento de cada pet em um arquivo `.TXT`
- listagem de todos os pets cadastrados
- busca de pets por criterios
- alteracao de pet cadastrado
- exclusao de pet com confirmacao

## Requisitos do desafio atendidos

### Passo 1 - Leitura do arquivo de perguntas

- o projeto possui o arquivo [formulario.txt](/C:/Users/laris/Downloads/desafioCadastro/formulario.txt)
- as perguntas sao lidas do arquivo e exibidas no terminal
- as perguntas principais nao foram fixadas com `print` direto no codigo

### Passo 2 - Menu inicial

- o menu apresenta as 6 opcoes pedidas no desafio
- o sistema nao aceita letras ou caracteres especiais na escolha do menu
- o sistema nao aceita `0` nem numeros negativos
- em caso de opcao invalida, o menu volta a ser exibido

### Passo 3 - Cadastro de novos pets

- o cadastro cria um objeto `Pet`
- nome e sobrenome sao obrigatorios
- nome completo aceita apenas letras e espacos
- tipo e sexo utilizam `enum`
- endereco e coletado separando numero, cidade e rua
- idade e peso aceitam numero com ponto ou virgula
- peso maior que `60` ou menor que `0.5` gera excecao
- idade maior que `20` gera excecao
- idade informada entre `1` e `11` e convertida para anos decimais
- raca aceita apenas letras e espacos
- os campos previstos pelo desafio como opcionais sao preenchidos com a constante `NAO INFORMADO`

### Passo 4 - Armazenamento em arquivo

- cada pet e salvo dentro da pasta [petsCadastrados](/C:/Users/laris/Downloads/desafioCadastro/petsCadastrados)
- o nome do arquivo segue o padrao `yyyyMMddTHHmm-NOMECOMPLETO.TXT`
- o nome do pet vai em maiusculas no nome do arquivo
- o arquivo salvo contem apenas respostas
- cada resposta fica em uma linha
- o endereco e salvo em uma unica linha

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

- o sistema exige primeiro o tipo do animal
- depois permite buscar com `1` ou `2` criterios adicionais
- os criterios disponiveis sao:

```text
Nome ou sobrenome
Sexo
Idade
Peso
Raca
Endereco
```

- a busca por nome e raca aceita partes do texto
- a busca ignora diferenca entre maiusculas e minusculas
- a busca tambem ignora acentos
- os resultados sao exibidos em formato de lista numerada

### Passo 6 - Alteracao de pet

- a alteracao comeca por uma busca
- o usuario escolhe o pet em uma lista numerada
- todos os dados podem ser alterados, exceto tipo e sexo

### Passo 7 - Exclusao de pet

- a exclusao comeca por uma busca
- o usuario escolhe um pet da lista
- a exclusao pede confirmacao com `SIM` ou `NAO`
- apenas um pet e excluido por vez

### Passo 8 - Sair

- ao escolher a opcao `6`, o programa e encerrado

## O que nao foi implementado

- passo extra de manutencao dinamica do `formulario.txt`
- destaque em negrito do termo pesquisado nos resultados
- busca por data de cadastro

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

- nome completo deve conter nome e sobrenome
- nome e raca nao aceitam numeros nem caracteres especiais
- tipo e sexo sao controlados por `enum`
- o menu aceita apenas numeros validos
- campos em branco definidos pelo desafio recebem `NAO INFORMADO`
- o projeto foi estruturado com separacao entre modelo, servico e utilitarios

## Observacoes finais

Este projeto cobre o fluxo principal solicitado no desafio original e pode ser executado localmente apenas com o JDK instalado, sem necessidade de Maven ou Gradle.
