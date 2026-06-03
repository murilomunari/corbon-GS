package br.com.fiap.main;

import br.com.fiap.bean.Agricultor;
import br.com.fiap.bean.Alerta;
import br.com.fiap.bean.Cidade;
import br.com.fiap.bean.MedicaoCO2;
import br.com.fiap.bean.Pais;
import br.com.fiap.bean.Plantio;
import javax.swing.JOptionPane;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        boolean continuar = true;
        int idAgricultor = 1;
        int idPlantio = 0;
        int idMedicao = 0;
        int idAlerta = 0;

        Pais pais = new Pais(1, "Brasil", "America do Sul");
        Cidade cidade = new Cidade(1, "Sao Paulo", -23.55, -46.63, pais);
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

                Agricultor agricultor = new Agricultor(idAgricultor, nome, email, telefone, LocalDate.now(), cidade);
                idAgricultor = idAgricultor + 1;

                JOptionPane.showMessageDialog(null, "Agricultor cadastrado com sucesso!\n\nVoce agora pode acessar o menu principal.");

                boolean continuarMenu = true;
                while (continuarMenu) {
                    String menuPrincipal = "=== MENU PRINCIPAL ===\n\n" +
                            "1. Cadastrar Plantio\n" +
                            "2. Registrar Medicao de CO2\n" +
                            "3. Criar Alerta\n" +
                            "4. Ver Alertas\n" +
                            "5. Ver Informacoes\n" +
                            "6. Sair";

                    auxiliar = JOptionPane.showInputDialog(menuPrincipal);
                    int opcaoMenu = Integer.parseInt(auxiliar);

                    if (opcaoMenu == 1) {
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

                    } else if (opcaoMenu == 2) {
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

                    } else if (opcaoMenu == 3) {
                        if (ultimaMedicao == null) {
                            JOptionPane.showMessageDialog(null, "Voce precisa registrar uma medicao primeiro! (Opcao 2)");
                        } else {
                            String mensagem = JOptionPane.showInputDialog("Digite a mensagem do alerta:");

                            idAlerta = idAlerta + 1;
                            Alerta alerta = new Alerta(idAlerta, LocalDate.now(), mensagem, agricultor, ultimaMedicao);
                            alerta.enviarNotificacao();
                            ultimoAlerta = alerta;

                            entradaUltimoAlerta = "Alerta #" + alerta.getId() + " [NAO LIDO]\n" +
                                    "  Data: " + alerta.getDataAlerta() + "\n" +
                                    "  Mensagem: " + alerta.getMensagem() + "\n" +
                                    "  Medicao: #" + ultimaMedicao.getId() + " - " + ultimaMedicao.getConcentracaoPpm() + " ppm (" + ultimaMedicao.getNivelRisco() + ")\n\n";
                            listaAlertas = listaAlertas + entradaUltimoAlerta;

                            JOptionPane.showMessageDialog(null, "Alerta criado com sucesso!\n\nAlerta #" + alerta.getId() + "\nData: " + alerta.getDataAlerta() + "\nMensagem: " + alerta.getMensagem() + "\nMedicao vinculada: #" + ultimaMedicao.getId() + " (" + ultimaMedicao.getConcentracaoPpm() + " ppm)");
                        }

                    } else if (opcaoMenu == 4) {
                        if (idAlerta == 0) {
                            JOptionPane.showMessageDialog(null, "Nenhum alerta registrado ate o momento.");
                        } else {
                            String info = "=== ALERTAS (" + idAlerta + ") ===\n\n" + listaAlertas;
                            JOptionPane.showMessageDialog(null, info);

                            String opcoesAlerta = "1. Marcar ultimo alerta como lido\n" +
                                    "2. Voltar";

                            auxiliar = JOptionPane.showInputDialog(opcoesAlerta);
                            int opcaoAlerta = Integer.parseInt(auxiliar);

                            if (opcaoAlerta == 1) {
                                ultimoAlerta.marcarComoLido();
                                listaAlertas = listaAlertas.replace(entradaUltimoAlerta, "");
                                entradaUltimoAlerta = "";
                                JOptionPane.showMessageDialog(null, "Alerta #" + ultimoAlerta.getId() + " marcado como lido e removido da lista!");
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
}
