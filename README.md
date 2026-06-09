# CARBONEYE — Sistema de Monitoramento de CO2

Sistema de monitoramento de CO2 voltado ao agronegocio, desenvolvido em Java, baseado no diagrama do projeto.

---

## Descrição

O sistema simula um app de acompanhamento ambiental para agricultores:

- O agricultor se cadastra (nome, email, telefone, cidade).
- Cadastra plantios (cultura e area em hectares).
- Consulta relatorios de concentracao de CO2 (por pais, mundial, por localidade e comparativos) com base em uma base estatica que simula dados do satelite Sentinel-5P.
- Registra novas medicoes de CO2 que classificam o risco automaticamente.
- Recebe alertas quando a medicao indica nivel elevado/critico.
- Gerencia seus plantios (listar e excluir) e consulta as proprias informacoes.

A interação é feita por janelas `JOptionPane`.

---

## Estrutura do Projeto

```
carboneye-GS/
├── src/
│   └── br/com/fiap/
│       ├── bean/
│       │   ├── Monitoravel.java   (interface)
│       │   ├── Agricultor.java
│       │   ├── Alerta.java
│       │   ├── Cidade.java
│       │   ├── MedicaoCO2.java
│       │   ├── Pais.java
│       │   └── Plantio.java
│       └── main/
│           └── Main.java
├── Documentação/
│   └── Diagrama.png
├── out/                  (artefatos de build)
└── README.md
```

Pacote raiz: `br.com.fiap`.

---

## Classes Implementadas

### Interface `Monitoravel`
Define o contrato de monitoramento ambiental.
- `registrarMedicao(double ppm): void`
- `classificarRisco(): String`

### `Agricultor`
Representa o usuario do sistema (produtor rural).
- **Atributos**: `id`, `nome`, `email`, `telefone`, `dataCadastro` (`LocalDate`), `cidade` (Cidade)
- **Métodos**:
  - `adicionarPlantio(Plantio p)` — vincula o plantio ao agricultor
  - `receberAlerta(Alerta a)` — exibe no console o alerta recebido

### `Alerta`
Notificação gerada a partir de uma medicao critica.
- **Atributos**: `id`, `dataAlerta` (`LocalDate`), `mensagem`, `lido`, `agricultor` (Agricultor), `medicao` (MedicaoCO2)
- **Métodos**:
  - `marcarComoLido()` — define `lido = true`
  - `enviarNotificacao()` — chama `receberAlerta` no agricultor associado

### `Cidade`
Localidade onde sao realizadas as medicoes/plantios.
- **Atributos**: `id`, `nome`, `latitude`, `longitude`, `pais` (Pais)
- **Métodos**:
  - `getLocalizacao(): String` — retorna `"latitude, longitude"`
  - `validarCoordenadas(): boolean` — valida faixas de latitude/longitude

### `MedicaoCO2` *(implementa `Monitoravel`)*
Medicao de concentracao de CO2 em uma cidade.
- **Atributos**: `id`, `concentracaoPpm`, `nivelRisco`, `dataMedicao` (`LocalDate`), `cidade` (Cidade)
- **Métodos**:
  - `registrarMedicao(double ppm)` — atualiza ppm, data e nivel de risco
  - `classificarRisco(): String` — retorna `"baixo"`, `"moderado"`, `"alto"` ou `"critico"`
  - `gerarAlerta(): boolean` — `true` se `ppm >= 1000`

### `Pais`
País associado a uma cidade.
- **Atributos**: `id`, `nome`, `continente`

### `Plantio`
Cultura plantada por um agricultor.
- **Atributos**: `id`, `cultura`, `areaHectares`, `dataInicio` (`LocalDate`), `agricultor` (Agricultor)
- **Métodos**:
  - `getDetalhes(): String` — retorna resumo do plantio (id, cultura e area)

---

## Fluxo do `Main`

Loop externo para cadastrar o agricultor e cadastro inicial de plantios, seguido do menu principal:

| Opção | Ação |
|-------|------|
| 1 | Ver relatorio de CO2 (abre o submenu de relatorios) |
| 2 | Adicionar plantio |
| 3 | Listar plantios |
| 4 | Excluir plantio (por ID) |
| 5 | Ver informacoes do agricultor (dados, plantios, medicoes, alertas) |
| 6 | Encerrar programa ou cadastrar novo agricultor |

### Submenu de Relatórios (Opção 1)

| Opção | Ação |
|-------|------|
| 1 | Relatorio por pais (lista cidades ordenadas por ppm + resumo + alertas) |
| 2 | Relatorio mundial (ranking dos paises pela media de ppm) |
| 3 | Consultar localidade especifica (ou digitar `listar` para ver todas) |
| 4 | Comparar localidades (entrada separada por virgula) |
| 5 | Registrar nova medicao de CO2 (pode gerar alerta automatico) |
| 6 | Voltar ao menu principal |

### Culturas disponíveis para Plantio

| Cultura          |
|------------------|
| Soja             |
| Milho            |
| Cafe             |
| Cana de Acucar   |

### Classificação de Risco

A classe `MedicaoCO2` classifica medicoes registradas pelo usuario:

| Faixa (ppm)        | Nível      |
|--------------------|------------|
| < 400              | baixo      |
| 400 – 999          | moderado   |
| 1000 – 1999        | alto       |
| >= 2000            | critico    |

> Quando `ppm >= 1000`, `gerarAlerta()` retorna `true` e o sistema cria um `Alerta` automaticamente.

Já os relatorios usam faixas especificas do agronegocio (NASA/IPCC):

| Faixa (ppm)        | Nível    |
|--------------------|----------|
| < 400              | NORMAL   |
| 400 – 409          | ATENCAO  |
| 410 – 419          | ELEVADO  |
| >= 420             | CRITICO  |

### Base estática de cidades

O `Main` mantem arrays paralelos com 30 cidades de 9 paises (BR, AR, US, CN, IN, AU, FR, RU, ZA), simulando dados do satelite **Sentinel-5P** (medicao 01/2020). Cada cidade tem nome, codigo do pais, latitude, longitude e concentracao em ppm.

---

## Padrões Utilizados

- **Encapsulamento**: todos os atributos são `private` com `getters`/`setters`
- **Interface**: `Monitoravel` implementada por `MedicaoCO2`
- **Associações**: `Agricultor` → `Cidade` → `Pais`; `Plantio` → `Agricultor`; `Alerta` → `Agricultor` + `MedicaoCO2`
- **Construtores**: vazio e com parâmetros em todas as classes
- **Datas**: `java.time.LocalDate`
- **Formatação**: `String.format` com `Locale.US` para padronizar casas decimais
- **Tipos primitivos**: `int`, `double`, `boolean`, `String`

---

## Conceitos Aplicados

- Classes e Objetos
- Encapsulamento
- Interface e Implementação
- Construtores (sobrecarga)
- Manipulação de Strings e datas (`LocalDate`)
- Arrays paralelos e algoritmos de ordenação (Bubble Sort)
- Estruturas condicionais e de repetição (`if`, `while`, `for`)
- Tratamento de exceções (`NumberFormatException`)
- Interface gráfica simples (`JOptionPane`)

---
