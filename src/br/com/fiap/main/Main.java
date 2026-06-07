package br.com.fiap.main;

import br.com.fiap.bean.Agricultor;
import br.com.fiap.bean.Alerta;
import br.com.fiap.bean.Cidade;
import br.com.fiap.bean.MedicaoCO2;
import br.com.fiap.bean.Pais;
import br.com.fiap.bean.Plantio;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.Locale;

public class Main {

    // ============================================================
    // BASE ESTATICA DE CIDADES (simula dados do satelite Sentinel-5P)
    // Arrays paralelos: o indice i representa a mesma cidade em todos
    // ============================================================
    private static final int N_CIDADES = 30;

    private static final String[] CID_NOMES = {
            "sao paulo", "brasilia", "sorriso", "sinop", "ribeirao preto",
            "cuiaba", "goiania", "londrina",
            "buenos aires", "cordoba", "rosario", "mendoza",
            "des moines", "chicago", "dallas", "kansas city", "omaha",
            "beijing", "shanghai", "harbin",
            "new delhi", "mumbai", "pune",
            "sydney", "perth",
            "paris", "bordeaux",
            "moscow", "krasnodar",
            "cape town"
    };

    private static final String[] CID_PAIS = {
            "BR", "BR", "BR", "BR", "BR", "BR", "BR", "BR",
            "AR", "AR", "AR", "AR",
            "US", "US", "US", "US", "US",
            "CN", "CN", "CN",
            "IN", "IN", "IN",
            "AU", "AU",
            "FR", "FR",
            "RU", "RU",
            "ZA"
    };

    private static final double[] CID_LAT = {
            -23.55, -15.78, -12.55, -11.86, -21.18, -15.60, -16.68, -23.30,
            -34.60, -31.42, -32.95, -32.89,
            41.59, 41.88, 32.78, 39.10, 41.26,
            39.91, 31.23, 45.75,
            28.61, 19.08, 18.52,
            -33.87, -31.95,
            48.86, 44.84,
            55.76, 45.04,
            -33.93
    };

    private static final double[] CID_LON = {
            -46.63, -47.93, -55.71, -55.51, -47.81, -56.10, -49.25, -51.17,
            -58.38, -64.18, -60.65, -68.83,
            -93.62, -87.63, -96.80, -94.58, -95.94,
            116.40, 121.47, 126.65,
            77.21, 72.88, 73.86,
            151.21, 115.86,
            2.35, -0.58,
            37.62, 38.98,
            18.42
    };

    private static final double[] CID_PPM = {
            415.2, 412.8, 414.5, 414.1, 416.0, 413.7, 413.2, 415.8,
            411.5, 410.9, 411.2, 409.8,
            418.4, 419.6, 417.2, 418.1, 418.8,
            424.3, 425.1, 421.7,
            423.8, 422.6, 422.1,
            409.5, 408.8,
            416.5, 415.2,
            420.8, 419.4,
            410.1
    };

    private static final String[] PAIS_COD = {
            "BR", "AR", "US", "CN", "IN", "AU", "FR", "RU", "ZA"
    };

    private static final String[] PAIS_NOMES = {
            "Brasil", "Argentina", "Estados Unidos", "China", "India",
            "Australia", "Franca", "Russia", "Africa do Sul"
    };

    public static void main(String[] args) {
        boolean continuar = true;
        int idAgricultor = 1;
        int idPlantio = 0;
        int idMedicao = 0;
        int idAlerta = 0;

        Pais pais = new Pais(1, "Brasil", "America do Sul");
        MedicaoCO2 ultimaMedicao = null;
        Alerta ultimoAlerta = null;
        String listaPlantios = "";
        String listaAlertas = "";
        String entradaUltimoAlerta = "";

        while (continuar) {
            try {
                String auxiliar;

                JOptionPane.showMessageDialog(null, "Bem-vindo ao CARBONEYE - Sistema de Monitoramento de CO2!");

                String nome = JOptionPane.showInputDialog("Digite seu nome:");
                String email = JOptionPane.showInputDialog("Digite seu email:");
                String telefone = JOptionPane.showInputDialog("Digite seu telefone:");

                String nomeCidade = JOptionPane.showInputDialog("Em qual cidade voce deseja cadastrar seus plantios?");
                Cidade cidade = new Cidade(1, nomeCidade, -23.55, -46.63, pais);

                Agricultor agricultor = new Agricultor(idAgricultor, nome, email, telefone, LocalDate.now(), cidade);
                idAgricultor = idAgricultor + 1;

                JOptionPane.showMessageDialog(null, "Agricultor cadastrado com sucesso!");

                boolean primeiroPlantio = true;
                while (primeiroPlantio) {
                    String opcoesCultura = "Escolha a cultura do plantio:\n\n" +
                            "1. Soja\n" +
                            "2. Milho\n" +
                            "3. Cafe\n" +
                            "4. Cana de Acucar";

                    auxiliar = JOptionPane.showInputDialog(opcoesCultura);
                    int opcaoCultura = Integer.parseInt(auxiliar);
                    String nomeCultura = "";
                    boolean culturaValida = true;

                    if (opcaoCultura == 1) {
                        nomeCultura = "Soja";
                    } else if (opcaoCultura == 2) {
                        nomeCultura = "Milho";
                    } else if (opcaoCultura == 3) {
                        nomeCultura = "Cafe";
                    } else if (opcaoCultura == 4) {
                        nomeCultura = "Cana de Acucar";
                    } else {
                        JOptionPane.showMessageDialog(null, "Opcao invalida!");
                        culturaValida = false;
                    }

                    if (culturaValida) {
                        auxiliar = JOptionPane.showInputDialog("Digite a area em hectares:");
                        double areaHectares = Double.parseDouble(auxiliar);

                        idPlantio = idPlantio + 1;
                        Plantio plantio = new Plantio(idPlantio, nomeCultura, areaHectares, LocalDate.now(), agricultor);
                        agricultor.adicionarPlantio(plantio);

                        listaPlantios = listaPlantios + "  - " + plantio.getDetalhes() + "\n";

                        JOptionPane.showMessageDialog(null, "Plantio cadastrado com sucesso!\n\n" + plantio.getDetalhes());

                        int novasPlantacoes = JOptionPane.showConfirmDialog(null, "Deseja cadastrar novos plantios em outras cidades?", "Novas Plantacoes", JOptionPane.YES_NO_OPTION);
                        if (novasPlantacoes == JOptionPane.NO_OPTION) {
                            primeiroPlantio = false;
                        } else {
                            String novaCidade = JOptionPane.showInputDialog("Em qual cidade voce deseja cadastrar o novo plantio?");
                            cidade = new Cidade(1, novaCidade, -23.55, -46.63, pais);
                        }
                    }
                }

                JOptionPane.showMessageDialog(null, "Voce agora pode acessar o menu principal.");

                boolean continuarMenu = true;
                while (continuarMenu) {
                    String menuPrincipal = "=== MENU PRINCIPAL ===\n\n" +
                            "1. Ver relatorio de CO2\n" +
                            "2. Adicionar plantio\n" +
                            "3. Listar plantios\n" +
                            "4. Excluir plantio\n" +
                            "5. Ver Informacoes\n" +
                            "6. Sair";

                    auxiliar = JOptionPane.showInputDialog(menuPrincipal);
                    int opcaoMenu = Integer.parseInt(auxiliar);

                    if (opcaoMenu == 1) {
                        boolean continuarRelatorio = true;
                        while (continuarRelatorio) {
                            String menuRelatorio = "=== MENU DE RELATORIOS ===\n\n" +
                                    "1. Relatorio por pais\n" +
                                    "2. Relatorio mundial\n" +
                                    "3. Consultar localidade especifica\n" +
                                    "4. Comparar localidades\n" +
                                    "5. Registrar nova medicao de CO2\n" +
                                    "6. Voltar";

                            auxiliar = JOptionPane.showInputDialog(menuRelatorio);
                            int opcaoRelatorio = Integer.parseInt(auxiliar);

                            if (opcaoRelatorio == 1) {
                                exibirRelatorioPais();

                            } else if (opcaoRelatorio == 2) {
                                exibirRelatorioMundial();

                            } else if (opcaoRelatorio == 3) {
                                exibirConsultaCidade();

                            } else if (opcaoRelatorio == 4) {
                                exibirComparacaoCidades();

                            } else if (opcaoRelatorio == 5) {
                                auxiliar = JOptionPane.showInputDialog("Digite a concentracao de CO2 (ppm):");
                                double ppm = Double.parseDouble(auxiliar);

                                idMedicao = idMedicao + 1;
                                MedicaoCO2 medicao = new MedicaoCO2(idMedicao, 0, "", LocalDate.now(), cidade);
                                medicao.registrarMedicao(ppm);
                                ultimaMedicao = medicao;

                                if (medicao.gerarAlerta()) {
                                    idAlerta = idAlerta + 1;
                                    Alerta alerta = new Alerta(idAlerta, LocalDate.now(), "Nivel de CO2 " + medicao.getNivelRisco() + " detectado em " + cidade.getNome() + " (" + ppm + " ppm)", agricultor, medicao);
                                    alerta.enviarNotificacao();
                                    ultimoAlerta = alerta;

                                    entradaUltimoAlerta = "Alerta #" + alerta.getId() + " [NAO LIDO]\n" +
                                            "  Data: " + alerta.getDataAlerta() + "\n" +
                                            "  Mensagem: " + alerta.getMensagem() + "\n" +
                                            "  Medicao: #" + medicao.getId() + " - " + medicao.getConcentracaoPpm() + " ppm (" + medicao.getNivelRisco() + ")\n\n";
                                    listaAlertas = listaAlertas + entradaUltimoAlerta;

                                    JOptionPane.showMessageDialog(null, "Medicao registrada!\n\nPPM: " + medicao.getConcentracaoPpm() + "\nRisco: " + medicao.getNivelRisco() + "\n\nALERTA GERADO:\n" + alerta.getMensagem());
                                } else {
                                    JOptionPane.showMessageDialog(null, "Medicao registrada!\n\nPPM: " + medicao.getConcentracaoPpm() + "\nRisco: " + medicao.getNivelRisco() + "\n\nNenhum alerta necessario.");
                                }

                            } else if (opcaoRelatorio == 6) {
                                continuarRelatorio = false;

                            } else {
                                JOptionPane.showMessageDialog(null, "Opcao invalida!");
                            }
                        }

                    } else if (opcaoMenu == 2) {
                        String opcoesCultura = "Escolha a cultura do plantio:\n\n" +
                                "1. Soja\n" +
                                "2. Milho\n" +
                                "3. Cafe\n" +
                                "4. Cana de Acucar";

                        auxiliar = JOptionPane.showInputDialog(opcoesCultura);
                        int opcaoCultura = Integer.parseInt(auxiliar);
                        String nomeCultura = "";
                        boolean culturaValida = true;

                        if (opcaoCultura == 1) {
                            nomeCultura = "Soja";
                        } else if (opcaoCultura == 2) {
                            nomeCultura = "Milho";
                        } else if (opcaoCultura == 3) {
                            nomeCultura = "Cafe";
                        } else if (opcaoCultura == 4) {
                            nomeCultura = "Cana de Acucar";
                        } else {
                            JOptionPane.showMessageDialog(null, "Opcao invalida!");
                            culturaValida = false;
                        }

                        if (culturaValida) {
                            auxiliar = JOptionPane.showInputDialog("Digite a area em hectares:");
                            double areaHectares = Double.parseDouble(auxiliar);

                            idPlantio = idPlantio + 1;
                            Plantio plantio = new Plantio(idPlantio, nomeCultura, areaHectares, LocalDate.now(), agricultor);
                            agricultor.adicionarPlantio(plantio);

                            listaPlantios = listaPlantios + "  - " + plantio.getDetalhes() + "\n";

                            JOptionPane.showMessageDialog(null, "Plantio cadastrado com sucesso!\n\n" + plantio.getDetalhes());
                        }

                    } else if (opcaoMenu == 3) {
                        if (idPlantio == 0) {
                            JOptionPane.showMessageDialog(null, "Nenhum plantio registrado ate o momento.");
                        } else {
                            String info = "=== PLANTIOS (" + idPlantio + ") ===\n\n" + listaPlantios;
                            JOptionPane.showMessageDialog(null, info);
                        }

                    } else if (opcaoMenu == 4) {
                        if (idPlantio == 0) {
                            JOptionPane.showMessageDialog(null, "Nenhum plantio registrado ate o momento.");
                        } else {
                            String info = "=== PLANTIOS CADASTRADOS ===\n\n" + listaPlantios;
                            JOptionPane.showMessageDialog(null, info);

                            auxiliar = JOptionPane.showInputDialog("Digite o ID do plantio que deseja excluir:");
                            int idPlantioExcluir = Integer.parseInt(auxiliar);

                            if (idPlantioExcluir > 0 && idPlantioExcluir <= idPlantio) {
                                String linhaRemover = "";
                                String[] plantiosArray = listaPlantios.split("\n");
                                for (String linha : plantiosArray) {
                                    if (linha.contains("ID: " + idPlantioExcluir)) {
                                        linhaRemover = linha + "\n";
                                        break;
                                    }
                                }

                                if (!linhaRemover.isEmpty()) {
                                    listaPlantios = listaPlantios.replace(linhaRemover, "");
                                    JOptionPane.showMessageDialog(null, "Plantio #" + idPlantioExcluir + " excluido com sucesso!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Plantio nao encontrado!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "ID invalido!");
                            }
                        }

                    } else if (opcaoMenu == 5) {
                        String info = "=== SUAS INFORMACOES ===\n\n";
                        info = info + "NOME: " + agricultor.getNome() + "\n";
                        info = info + "EMAIL: " + agricultor.getEmail() + "\n";
                        info = info + "TELEFONE: " + agricultor.getTelefone() + "\n";
                        info = info + "DATA CADASTRO: " + agricultor.getDataCadastro() + "\n";
                        info = info + "CIDADE: " + cidade.getNome() + " (" + cidade.getLocalizacao() + ")\n";
                        info = info + "PAIS: " + pais.getNome() + " - " + pais.getContinente() + "\n\n";

                        info = info + "PLANTIOS: " + idPlantio + "\n";
                        info = info + listaPlantios;

                        info = info + "\nMEDICOES REGISTRADAS: " + idMedicao + "\n";
                        info = info + "ALERTAS GERADOS: " + idAlerta + "\n";

                        JOptionPane.showMessageDialog(null, info);

                    } else if (opcaoMenu == 6) {
                        boolean menuSaida = true;
                        while (menuSaida) {
                            String opcoesSaida = "O que voce deseja fazer?\n\n" +
                                    "1. Encerrar programa\n" +
                                    "2. Cadastrar novo agricultor";

                            auxiliar = JOptionPane.showInputDialog(opcoesSaida);
                            int opcaoSaida = Integer.parseInt(auxiliar);

                            if (opcaoSaida == 1) {
                                JOptionPane.showMessageDialog(null, "Obrigado por usar o Carboneye!\nVolte sempre!");
                                continuarMenu = false;
                                continuar = false;
                                menuSaida = false;

                            } else if (opcaoSaida == 2) {
                                menuSaida = false;
                                continuarMenu = false;

                            } else {
                                JOptionPane.showMessageDialog(null, "Opcao invalida!");
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Opcao invalida!");
                    }
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erro: Digite um numero valido!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        }
    }

    // ============================================================
    // METODOS AUXILIARES DOS RELATORIOS
    // ============================================================

    // Classifica o risco com base nos limites da NASA/IPCC
    // (faixas especificas para impacto no agronegocio)
    private static String classificarRiscoRel(double ppm) {
        if (ppm < 400) {
            return "NORMAL";
        } else if (ppm < 410) {
            return "ATENCAO";
        } else if (ppm < 420) {
            return "ELEVADO";
        } else {
            return "CRITICO";
        }
    }

    // Retorna o nome completo do pais a partir do codigo ISO
    private static String nomePais(String codigo) {
        for (int i = 0; i < PAIS_COD.length; i++) {
            if (PAIS_COD[i].equals(codigo)) {
                return PAIS_NOMES[i];
            }
        }
        return codigo;
    }

    // Normaliza texto: minusculas, sem acentos, sem espacos nas pontas
    private static String normalizar(String s) {
        if (s == null) {
            return "";
        }
        s = s.toLowerCase().trim();
        s = s.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
        s = s.replace("ã", "a").replace("õ", "o").replace("â", "a").replace("ê", "e").replace("ô", "o");
        s = s.replace("ç", "c").replace("ü", "u");
        return s;
    }

    // Capitaliza a primeira letra de cada palavra (Sao Paulo, New Delhi...)
    private static String capitalizar(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        String[] palavras = s.split(" ");
        String resultado = "";
        for (int i = 0; i < palavras.length; i++) {
            if (i > 0) {
                resultado = resultado + " ";
            }
            String p = palavras[i];
            if (!p.isEmpty()) {
                resultado = resultado + Character.toUpperCase(p.charAt(0)) + p.substring(1);
            }
        }
        return resultado;
    }

    // Busca uma cidade na base por nome parcial (sem acento, case insensitive)
    // Retorna o indice no array ou -1 se nao encontrar
    private static int buscarIndiceCidade(String entrada) {
        String norm = normalizar(entrada);
        if (norm.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < N_CIDADES; i++) {
            if (CID_NOMES[i].contains(norm) || norm.contains(CID_NOMES[i])) {
                return i;
            }
        }
        return -1;
    }

    // [Opcao 1] Relatorio detalhado de um pais escolhido pelo usuario
    private static void exibirRelatorioPais() {
        String menu = "=== RELATORIO POR PAIS ===\n\nEscolha um pais:\n\n";
        for (int i = 0; i < PAIS_COD.length; i++) {
            int qtd = 0;
            for (int j = 0; j < N_CIDADES; j++) {
                if (CID_PAIS[j].equals(PAIS_COD[i])) {
                    qtd = qtd + 1;
                }
            }
            menu = menu + "[" + (i + 1) + "] " + PAIS_NOMES[i] + " (" + PAIS_COD[i] + ") - " + qtd + " localidades\n";
        }

        String aux = JOptionPane.showInputDialog(menu);
        if (aux == null || aux.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Voce nao digitou nada.");
            return;
        }

        int escolha;
        try {
            escolha = Integer.parseInt(aux.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite o numero correspondente ao pais.");
            return;
        }

        if (escolha < 1 || escolha > PAIS_COD.length) {
            JOptionPane.showMessageDialog(null, "Numero fora da lista.");
            return;
        }

        String codigoEscolhido = PAIS_COD[escolha - 1];
        String nomePaisEscolhido = PAIS_NOMES[escolha - 1];

        // Conta quantas cidades existem nesse pais
        int total = 0;
        for (int i = 0; i < N_CIDADES; i++) {
            if (CID_PAIS[i].equals(codigoEscolhido)) {
                total = total + 1;
            }
        }

        // Copia para arrays paralelos so do pais (para ordenar)
        String[] nomesPais = new String[total];
        double[] ppmPais = new double[total];
        int k = 0;
        for (int i = 0; i < N_CIDADES; i++) {
            if (CID_PAIS[i].equals(codigoEscolhido)) {
                nomesPais[k] = CID_NOMES[i];
                ppmPais[k] = CID_PPM[i];
                k = k + 1;
            }
        }

        // Bubble sort decrescente por ppm
        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - 1 - i; j++) {
                if (ppmPais[j] < ppmPais[j + 1]) {
                    double tmpP = ppmPais[j];
                    ppmPais[j] = ppmPais[j + 1];
                    ppmPais[j + 1] = tmpP;
                    String tmpN = nomesPais[j];
                    nomesPais[j] = nomesPais[j + 1];
                    nomesPais[j + 1] = tmpN;
                }
            }
        }

        // Monta o texto do relatorio
        String rel = "=== RELATORIO DE CO2 - " + nomePaisEscolhido.toUpperCase() + " ===\n";
        rel = rel + "Satelite: Sentinel-5P | Medicao: 01/2020\n\n";

        double soma = 0;
        for (int i = 0; i < total; i++) {
            String risco = classificarRiscoRel(ppmPais[i]);
            rel = rel + String.format(Locale.US, "%2d. %-22s %.2f ppm [%s]%n", (i + 1), capitalizar(nomesPais[i]), ppmPais[i], risco);
            soma = soma + ppmPais[i];
        }

        double media = soma / total;
        String nivelMedia = classificarRiscoRel(media);

        rel = rel + "\n--- RESUMO " + nomePaisEscolhido.toUpperCase() + " ---\n";
        rel = rel + String.format(Locale.US, "Media:       %.2f ppm [%s]%n", media, nivelMedia);
        rel = rel + String.format(Locale.US, "Maximo:      %.2f ppm (%s)%n", ppmPais[0], capitalizar(nomesPais[0]));
        rel = rel + String.format(Locale.US, "Minimo:      %.2f ppm (%s)%n", ppmPais[total - 1], capitalizar(nomesPais[total - 1]));
        rel = rel + "Localidades: " + total + "\n";

        // Alertas (CRITICO e ELEVADO)
        String alertas = "";
        int qtdAlertas = 0;
        for (int i = 0; i < total; i++) {
            String r = classificarRiscoRel(ppmPais[i]);
            if (r.equals("CRITICO") || r.equals("ELEVADO")) {
                alertas = alertas + String.format(Locale.US, "  ! %s: %.1f ppm - %s%n", capitalizar(nomesPais[i]), ppmPais[i], r);
                qtdAlertas = qtdAlertas + 1;
            }
        }
        if (qtdAlertas > 0) {
            rel = rel + "\nALERTAS (" + nomePaisEscolhido + "):\n" + alertas;
        }

        JOptionPane.showMessageDialog(null, rel);
    }

    // [Opcao 2] Ranking mundial: media de CO2 por pais
    private static void exibirRelatorioMundial() {
        int nPaises = PAIS_COD.length;
        String[] paisesAux = new String[nPaises];
        double[] mediasAux = new double[nPaises];
        int[] qtdCidadesAux = new int[nPaises];

        // Calcula a media de cada pais
        for (int i = 0; i < nPaises; i++) {
            double soma = 0;
            int qtd = 0;
            for (int j = 0; j < N_CIDADES; j++) {
                if (CID_PAIS[j].equals(PAIS_COD[i])) {
                    soma = soma + CID_PPM[j];
                    qtd = qtd + 1;
                }
            }
            paisesAux[i] = PAIS_COD[i];
            mediasAux[i] = soma / qtd;
            qtdCidadesAux[i] = qtd;
        }

        // Bubble sort decrescente
        for (int i = 0; i < nPaises - 1; i++) {
            for (int j = 0; j < nPaises - 1 - i; j++) {
                if (mediasAux[j] < mediasAux[j + 1]) {
                    double tm = mediasAux[j];
                    mediasAux[j] = mediasAux[j + 1];
                    mediasAux[j + 1] = tm;
                    String tp = paisesAux[j];
                    paisesAux[j] = paisesAux[j + 1];
                    paisesAux[j + 1] = tp;
                    int tq = qtdCidadesAux[j];
                    qtdCidadesAux[j] = qtdCidadesAux[j + 1];
                    qtdCidadesAux[j + 1] = tq;
                }
            }
        }

        String rel = "=== RELATORIO MUNDIAL - CO2 POR PAIS ===\n";
        rel = rel + "Satelite Sentinel-5P | 01/2020\n\n";

        double somaGlobal = 0;
        for (int i = 0; i < nPaises; i++) {
            String nivel = classificarRiscoRel(mediasAux[i]);
            rel = rel + String.format(Locale.US, "%2d. %-18s %.2f ppm (%d cidades) [%s]%n",
                    (i + 1), nomePais(paisesAux[i]), mediasAux[i], qtdCidadesAux[i], nivel);
            somaGlobal = somaGlobal + mediasAux[i];
        }

        double mediaGlobal = somaGlobal / nPaises;
        String nivelGlobal = classificarRiscoRel(mediaGlobal);

        rel = rel + "\n--- ESTATISTICAS GLOBAIS ---\n";
        rel = rel + String.format(Locale.US, "Media global:  %.2f ppm [%s]%n", mediaGlobal, nivelGlobal);
        rel = rel + String.format(Locale.US, "Maior CO2:     %s (%.2f ppm)%n", nomePais(paisesAux[0]), mediasAux[0]);
        rel = rel + String.format(Locale.US, "Menor CO2:     %s (%.2f ppm)%n", nomePais(paisesAux[nPaises - 1]), mediasAux[nPaises - 1]);
        rel = rel + String.format(Locale.US, "Diferenca:     %.2f ppm%n", mediasAux[0] - mediasAux[nPaises - 1]);

        JOptionPane.showMessageDialog(null, rel);
    }

    // [Opcao 3] Consulta uma localidade especifica e mostra dados detalhados
    private static void exibirConsultaCidade() {
        String entrada = JOptionPane.showInputDialog(
                "=== CONSULTAR LOCALIDADE ===\n\n" +
                        "Digite o nome da localidade\n" +
                        "(ou 'listar' para ver todas as cadastradas):");

        if (entrada == null || entrada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Voce nao digitou nada.");
            return;
        }

        entrada = entrada.trim();

        // Comando especial: listar todas as cidades
        if (entrada.equalsIgnoreCase("listar")) {
            String lista = "=== LOCALIDADES CADASTRADAS ===\n\n";
            for (int i = 0; i < PAIS_COD.length; i++) {
                lista = lista + "[" + PAIS_COD[i] + "] " + PAIS_NOMES[i] + ":\n";
                for (int j = 0; j < N_CIDADES; j++) {
                    if (CID_PAIS[j].equals(PAIS_COD[i])) {
                        lista = lista + "  - " + capitalizar(CID_NOMES[j]) + "\n";
                    }
                }
                lista = lista + "\n";
            }
            JOptionPane.showMessageDialog(null, lista);
            return;
        }

        int idx = buscarIndiceCidade(entrada);
        if (idx < 0) {
            JOptionPane.showMessageDialog(null,
                    "Localidade '" + entrada + "' nao encontrada na base.\n\n" +
                            "Dica: tente sem acentos ou digite 'listar'.");
            return;
        }

        String nivel = classificarRiscoRel(CID_PPM[idx]);
        String nomeP = nomePais(CID_PAIS[idx]);

        String resultado = "=== " + capitalizar(CID_NOMES[idx]) + " - " + nomeP + " ===\n\n";
        resultado = resultado + String.format(Locale.US, "Coordenadas: %.2f, %.2f%n", CID_LAT[idx], CID_LON[idx]);
        resultado = resultado + "Medicao: satelite Sentinel-5P\n\n";
        resultado = resultado + String.format(Locale.US, "Concentracao de CO2: %.2f ppm%n", CID_PPM[idx]);
        resultado = resultado + "Nivel de risco:      [" + nivel + "]\n\n";

        // Mensagem personalizada por nivel
        if (nivel.equals("CRITICO")) {
            resultado = resultado + "ALERTA MAXIMO para " + capitalizar(CID_NOMES[idx]) + "!\n";
            resultado = resultado + "Revisar planejamento de safra imediatamente.";
        } else if (nivel.equals("ELEVADO")) {
            resultado = resultado + "ALERTA para " + capitalizar(CID_NOMES[idx]) + "!\n";
            resultado = resultado + "Considerar praticas de mitigacao (plantio direto, cobertura).";
        } else if (nivel.equals("ATENCAO")) {
            resultado = resultado + "Nivel de atencao em " + capitalizar(CID_NOMES[idx]) + ".\n";
            resultado = resultado + "Acompanhar tendencia nos proximos meses.";
        } else {
            resultado = resultado + "Nivel normal em " + capitalizar(CID_NOMES[idx]) + ".";
        }

        JOptionPane.showMessageDialog(null, resultado);
    }

    // [Opcao 4] Compara CO2 entre varias localidades digitadas pelo usuario
    private static void exibirComparacaoCidades() {
        String entrada = JOptionPane.showInputDialog(
                "=== COMPARAR LOCALIDADES ===\n\n" +
                        "Digite os nomes separados por virgula\n" +
                        "(Exemplo: sorriso, buenos aires, paris, beijing):");

        if (entrada == null || entrada.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Voce nao digitou nada.");
            return;
        }

        String[] nomes = entrada.split(",");
        if (nomes.length < 2) {
            JOptionPane.showMessageDialog(null, "Digite pelo menos 2 localidades separadas por virgula.");
            return;
        }

        // Busca cada nome e separa encontradas / nao encontradas
        int[] idxEncontrados = new int[nomes.length];
        int nEncontrados = 0;
        String naoEncontradas = "";

        for (int i = 0; i < nomes.length; i++) {
            String nome = nomes[i].trim();
            if (nome.isEmpty()) {
                continue;
            }
            int idx = buscarIndiceCidade(nome);
            if (idx < 0) {
                if (!naoEncontradas.isEmpty()) {
                    naoEncontradas = naoEncontradas + ", ";
                }
                naoEncontradas = naoEncontradas + nome;
            } else {
                idxEncontrados[nEncontrados] = idx;
                nEncontrados = nEncontrados + 1;
            }
        }

        if (nEncontrados < 2) {
            String msg = "Menos de 2 localidades validas encontradas.";
            if (!naoEncontradas.isEmpty()) {
                msg = msg + "\nNao encontradas: " + naoEncontradas;
            }
            JOptionPane.showMessageDialog(null, msg);
            return;
        }

        // Bubble sort decrescente por ppm (ordena os indices)
        for (int i = 0; i < nEncontrados - 1; i++) {
            for (int j = 0; j < nEncontrados - 1 - i; j++) {
                if (CID_PPM[idxEncontrados[j]] < CID_PPM[idxEncontrados[j + 1]]) {
                    int tmp = idxEncontrados[j];
                    idxEncontrados[j] = idxEncontrados[j + 1];
                    idxEncontrados[j + 1] = tmp;
                }
            }
        }

        String rel = "=== COMPARACAO GLOBAL DE CO2 ===\n\n";
        for (int i = 0; i < nEncontrados; i++) {
            int idx = idxEncontrados[i];
            rel = rel + String.format(Locale.US, "%d. %-20s (%s) %.2f ppm [%s]%n",
                    (i + 1), capitalizar(CID_NOMES[idx]),
                    nomePais(CID_PAIS[idx]),
                    CID_PPM[idx], classificarRiscoRel(CID_PPM[idx]));
        }

        double diff = CID_PPM[idxEncontrados[0]] - CID_PPM[idxEncontrados[nEncontrados - 1]];
        rel = rel + String.format(Locale.US, "%nDiferenca entre maior e menor: %.2f ppm%n", diff);

        if (diff > 5) {
            rel = rel + "Variacao significativa entre as localidades!";
        } else if (diff > 2) {
            rel = rel + "Variacao moderada.";
        } else {
            rel = rel + "Localidades com niveis semelhantes.";
        }

        if (!naoEncontradas.isEmpty()) {
            rel = rel + "\n\nNao incluidas: " + naoEncontradas;
        }

        JOptionPane.showMessageDialog(null, rel);
    }
}
