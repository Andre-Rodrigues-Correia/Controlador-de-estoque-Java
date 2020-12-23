package sistema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import app.App;

public class sistema {
    public static void menuPrincipal(String usuario, Scanner scan) throws IOException, InterruptedException {
        HashMap<Integer, String> produtos = new HashMap<Integer, String>();
        HashMap<Integer, Double> precos = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> quantidade = new HashMap<Integer, Integer>();
        App.LimparTela();
        System.out.format("Usuario logado: %s", usuario);
        puxarDados(produtos, precos, quantidade);
        interacaoMenu(scan, produtos, precos, quantidade);
    }
    private static void salvar(HashMap<Integer, String> produtos, HashMap<Integer, Double> precos, HashMap<Integer, Integer> quantidade) {

        String path = "C:\\Users\\Andre\\OneDrive\\Documentos\\faculdade\\projeto de sistema de estoque\\controladorEstoque\\src\\produtos.txt";

        File f = new File(path);  
        f.delete();
        
        try{
            
            FileWriter fw = new FileWriter( path, true );
            BufferedWriter bw = new BufferedWriter( fw );

            for (int chave : produtos.keySet()) {
                bw.write(chave + "," + produtos.get(chave) + "," + quantidade.get(chave) + "," +  precos.get(chave));
                bw.newLine();
            }
           
            bw.close();
            fw.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    private static void puxarDados(HashMap<Integer, String> produtos, HashMap<Integer, Double> precos, HashMap<Integer, Integer> quantidade) {
        String path = "C:\\Users\\Andre\\OneDrive\\Documentos\\faculdade\\projeto de sistema de estoque\\controladorEstoque\\src\\produtos.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            

            String conteudo = br.readLine();

            while(conteudo != null){

                String[] vetor = conteudo.split(",");
                
                Integer chave  =Integer.parseInt(vetor[0]);
                String produto = vetor[1];
                Integer qtd = Integer.parseInt(vetor[2]);
                Double valor = Double.parseDouble(vetor[3]);
                produtos.put(chave, produto);
                precos.put(chave, valor);
                quantidade.put(chave,qtd);

                conteudo = br.readLine();
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void menuSistema() throws IOException, InterruptedException {
        System.out.println("CONTROLADOR DE ESTOQUE ARC\n--------------------------");
        System.out.println("DIGITE 1 PARA REGISTRAR SAIDA DE PRODUTOS\nDIGITE 2 PARA REGISTRAR ENTRADA DE PRODUTOS\nDIGITE 3 PARA LISTAR ESTOQUE\nDIGITE 4 PARA ALTERAR PRODUTO\n");
        System.out.print("DIGITE 0 PARA SALVAR E SAIR\nDIGITE AQUI: ");
    }

    public static void interacaoMenu(Scanner scan, HashMap<Integer, String> produtos, HashMap<Integer, Double> precos, HashMap<Integer, Integer> quantidade) throws IOException, InterruptedException {
        
        Integer opcaoDesejada;
        
        do{
            menuSistema();
            opcaoDesejada = scan.nextInt();
            scan.nextLine();
            
            switch(opcaoDesejada){
                case 1:
                registrarSaida(scan, produtos, precos, quantidade);
                break;

                case 2:
                registrarEntrada(scan, produtos, precos, quantidade);
                break;
                case 3:
                listarEstoque(produtos, precos, quantidade);
                break;
                case 4:
                alterarProduto(scan ,produtos, precos, quantidade);
                break;
                case 0:
                salvar(produtos, precos, quantidade);
                App.LimparTela();
                System.out.print("SISTEMA ENCERRADO! ATE BREVE...");
                break;
                default:
                App.LimparTela();
                System.out.println("OPÇÃO INVÁLIDA, DIGITE NOVAMENTE!");
                break;
            }

        }while(opcaoDesejada != 0);
    }

    private static void alterarProduto(Scanner scan, HashMap<Integer, String> produtos, HashMap<Integer, Double> precos,
            HashMap<Integer, Integer> quantidade) throws IOException, InterruptedException {
        App.LimparTela();

        String nomeProduto;

        System.out.print("\nDIGITE A CHAVE DO PRODUTO A SER ALTERADO: ");
        int valor = scan.nextInt();
        scan.nextLine();

        if(produtos.containsKey(valor)){
            App.LimparTela();
            System.out.format("DADOS ORIGINAIS\nNOME: %s | PREÇO: %.2f | QUANTIDADE: %d\n", produtos.get(valor),precos.get(valor),quantidade.get(valor));

            System.out.print("\nDIGITE O NOVO NOME DO PRODUTO: ");
            nomeProduto = scan.nextLine();
            nomeProduto = nomeProduto.isEmpty() ? produtos.get(valor) : nomeProduto;

            System.out.print("\nDIGITE O NOVO PREÇO DO PRODUTO: ");
            String novoPrecoStr = scan.nextLine();
            Double novoPreco = novoPrecoStr.isEmpty() ? precos.get(valor) : Double.parseDouble(novoPrecoStr.replace(",", "."));

            System.out.print("\nDIGITE O NOVA QUANTIDADE DO PRODUTO: ");
            String novaQtd = scan.nextLine();
            int aux = (int) (novaQtd.isEmpty() ? precos.get(valor) : Integer.parseInt(novaQtd));


            System.out.print("\nDIGITE S/N PARA CONFIRMAR ALTERAÇÃO: ");
            String s_n = scan.nextLine().trim();

            App.LimparTela();

            if(s_n.toUpperCase().equals("S")){
                App.LimparTela();
                produtos.put(valor, nomeProduto);
                precos.put(valor, novoPreco);
                quantidade.put(valor, aux);
                System.out.println("PRODUTO ALTERADO COM SUCESSO!");
            }else{
                System.out.println("PRODUTO NÃO ALTERADO!");
            }
        }else{
            System.out.println("PRODUTO NÃO ENCONTRADO!");
        }

        System.out.println("PRESSIONE ENTER PARA VOLTAR AO MENU!");
        System.in.read();
        App.LimparTela();
    }

    private static void listarEstoque(HashMap<Integer, String> produtos, HashMap<Integer, Double> precos,
            HashMap<Integer, Integer> quantidade) throws IOException, InterruptedException {

        App.LimparTela();

        System.out.println("RELATORIO DE PRODUTOS\n");

        System.out.printf("%6s | %-10s | %-10s | %-6s\n", "código", "produto", "quantidade", "preço");

        for (int chave : produtos.keySet()) {
            System.out.format("%06d | %-10s | %010d | %6.2f\n",chave, produtos.get(chave), quantidade.get(chave), precos.get(chave));
        }
        System.out.print("\n-------------------------------\nPRESSIONE ENTER PARA VOLTAR AO MENU!");
        System.in.read();
        App.LimparTela();
    }

    private static void registrarEntrada(Scanner scan, HashMap<Integer, String> produtos, HashMap<Integer, Double> precos,HashMap<Integer, Integer> quantidade) throws IOException, InterruptedException {
        Integer valor;
        if(produtos.size() == 0){
            valor = 1;
        }else{
            valor = Collections.max(produtos.keySet())+ 1;
        }

        System.out.print("\nDIGITE O NOME DO PRODUTO A SER ACRESCENTADO: ");
        String nomeProdurto = scan.nextLine();
        for (int chave : produtos.keySet()) {
           if(nomeProdurto.equals(produtos.get(chave))){
               App.LimparTela();
               System.out.println("ATENÇÃO, JÁ EXISTE UM PRODUTO COM ESSE NOME CADASTRADO EM NOSSO SISTEMA!");
               System.out.format("DADOS:\n%06d | %-10s | %06d | %6.2f\n",chave, produtos.get(chave),quantidade.get(chave),precos.get(chave));
               System.out.println("SE O DESEJADO FOR ALTERAR ESSE PRODUTOS, CLIQUE NA OPÇÃO ALTERAR PRODUTO NO MENU!");
               break;
           }else{
                System.out.print("\nDIGITE O PREÇO DO PRODUTO A SER ACRESCENTADO: ");
                Double preco = scan.nextDouble();
                scan.nextLine();
                System.out.print("\nDIGITE A QUANTIDADE PRODUTO A SER ACRESCENTADO: ");
                Integer qtd = scan.nextInt();
                scan.nextLine();
        
                System.out.print("\nDIGITE S/N PARA CONFIRMAR ALTERAÇÃO: ");
                String s_n = scan.nextLine().trim();
        
                App.LimparTela();
        
                if(s_n.toUpperCase().equals("S")){
                    App.LimparTela();
                    System.out.println("PRODUTO INSERIDO COM SUCESSO!");
                    produtos.put(valor, nomeProdurto);
                    precos.put(valor, preco);
                    quantidade.put(valor, qtd);
                    break;
                }else{
                    App.LimparTela();
                    System.out.println("PRODUTO NÃO INSERIDO!");
                    break;
                }
           }
        }
        System.out.println("PRESSIONE ENTER PARA VOLTAR AO MENU!");
        System.in.read();
        App.LimparTela();
    }

    private static void registrarSaida(Scanner scan, HashMap<Integer, String> produtos, HashMap<Integer, Double> precos, HashMap<Integer, Integer> quantidade) throws IOException, InterruptedException{
        System.out.print("\nDIGITE A CHAVE DO PRODUTO A SER REMOVIDO: ");
        int valor = scan.nextInt();
        scan.nextLine();

        if(produtos.containsKey(valor)){
            App.LimparTela();
            System.out.format("DADOS ORIGINAIS\nCHAVE: %d\nNOME: %s\nPREÇO: %.2f\nQUANTIDADE: %d\n",valor, produtos.get(valor),precos.get(valor),quantidade.get(valor));    
            System.out.print("\nDIGITE S/N PARA CONFIRMAR REMOÇÃO: ");
            String s_n = scan.nextLine().trim();

            App.LimparTela();

            if(s_n.toUpperCase().equals("S")){
                App.LimparTela();
                produtos.remove(valor);
                precos.remove(valor);
                quantidade.remove(valor);
                System.out.println("PRODUTO REMOVIDO COM SUCESSO!");
            }else{
                System.out.println("PRODUTO NÃO REMOVIDO!");
            }
        }else{
            System.out.println("PRODUTO NÃO ENCONTRADO");
        }
        System.out.println("PRESSIONE ENTER PARA VOLTAR AO MENU!");
        System.in.read();
        App.LimparTela();

    }
}
