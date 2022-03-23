![Cabeçalho do projeto](../main/images/header.png)

## Descrição do projeto

Projeto desenvolvido em java sobre autômatos finitos determinísticos (AFD) e não-determinísticos (AFN), desenvolvido como trabalho da disciplina de Teoria da Computação pela Universidade Federal de Sergipe.

## Abordagem utilizada

> O programa importa arquivos no formato `.jff`, estruturado em XML, e realiza a leitura dos dados relacionados ao autômato para, enfim, possibilitar a manipulação e realização de operações.

> Ao finalizar as operações desejadas o programa exporta o autômato resultante em um arquivo `.jff`.

## Funcionalidades presentes

- `União`: O programa realiza a operação de união entre dois autômatos (AFD/N).
- `Intersecção`: O programa realiza a operação de intersecção entre dois autômatos (AFD/N).
- `Concatenação`: O programa realiza a operação de concatenação entre dois autômatos (AFD/N).
- `Complemento`: É feita a operação de complemento em um autômato (AFD/N).
- `Estrela`: É feita a operação de estrela (Fecho de Kleene) em um autômato (AFD/N).
- `Gerar AFD equivalente`: Importa um AFN e gera o AFD equivalente 
