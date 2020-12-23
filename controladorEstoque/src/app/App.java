package app;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import sistema.sistema;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        imprimirMenuLogin(scan);

    }
    public static void imprimirMenuLogin(Scanner scan) throws IOException, InterruptedException{
        
        Integer opcaoDesejada;
        String opcaoDesejadaAux;


        do{
            System.out.println("CONTROLADOR DE ESTOQUE ARC\n-------------------------------\n");
            System.out.print("DIGITE 1 PARA LOGIN\nDIGITE 2 PARA CADASTRAR-SE\nDIGITE 0 PARA SAIR\nDIGITE AQUI: ");
            opcaoDesejada = scan.nextInt();
            scan.nextLine();

            switch(opcaoDesejada){
                case 1:
                realizarLogin(scan);
                break;
                case 2:
                realizarCadastro(scan);
                break;
                case 0:
                LimparTela();
                System.out.print("SISTEMA ARC ENCERRADO, ATÉ BREVE...");
                System.exit(0);
                break;
                default:
                LimparTela();
                System.out.println("OPÇÃO DIGITADA INVÁLIDA, POR FAVOR DIGITE NOVAMENTE!\n");
                break;
            }

        }while(opcaoDesejada != 0);



    }
    public static void realizarLogin(Scanner scan) throws IOException, InterruptedException {
            LimparTela();

            System.out.println("BEM VIDO A TELA DE LOGIN, PROSSIGA COM SEUS DADOS ABAIXO!");
            System.out.print("INFORME AQUI SEU USUÁRIO: ");
            String usurario = scan.nextLine();
            System.out.print("INFORME AQUI SUA SENHA: ");
            String senha = scan.nextLine();

            int checarLogin = loginExiste(usurario, senha);

            if(checarLogin == 1){
                sistema.menuPrincipal(usurario, scan);
            }else{0
                LimparTela();
                System.out.println("USUÁRIO OU SENHA INCORRETOS!");
                imprimirMenuLogin(scan);
            }

    }
    public static void realizarCadastro(Scanner scan) throws IOException, InterruptedException{

            int flag;
            String usuario, senha, confirmarSenha;
            LimparTela();
            System.out.println("BEM VIDO A TELA DE CADASTRO, PROSSIGA COM SEUS DADOS ABAIXO!");
            System.out.print("INFORME AQUI SEU USUÁRIO: ");
            usuario = scan.nextLine();

            do{
            System.out.print("INFORME AQUI SUA SENHA: ");
            senha = scan.nextLine();
            System.out.print("INFORME AQUI SUA SENHA: ");
            confirmarSenha = scan.nextLine();

                if(senha.equals(confirmarSenha)){
                    flag = 1;
                }else{
                    LimparTela();
                    System.out.println("AS SENHAS NÃO CONFEREM, DIGITE NOVAMENTE POR FAVOR!");
                    flag = 0;
                }

            }while(flag == 0);

            int checarLogin = loginExiste(usuario, senha);

            if(checarLogin == 1){
                LimparTela();
                System.out.println("FALHA NO CADASTRO, ESSE NOME DE USUÁRIO JÁ ESTÁ EM USO!");
                imprimirMenuLogin(scan);
            }else{
                cadastrandoUsuario(usuario, senha);
                sistema.menuPrincipal(usuario, scan);
            }

    }

    private static void cadastrandoUsuario(String usuario, String senha) {
        String path = "C:\\Users\\Andre\\OneDrive\\Documentos\\faculdade\\projeto de sistema de estoque\\controladorEstoque\\src\\usuarios.txt";
        try{
            
            FileWriter fw = new FileWriter( path, true );
            BufferedWriter bw = new BufferedWriter( fw );

           
            bw.write(usuario + "," + senha);
 
            //quebra de linha
            bw.newLine();

            bw.close();
            fw.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("cadastro feito");
    }

    public static int loginExiste(String usuario, String senha) throws IOException, InterruptedException {
        
        String path = "C:\\Users\\Andre\\OneDrive\\Documentos\\faculdade\\projeto de sistema de estoque\\controladorEstoque\\src\\usuarios.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            

            String conteudo = br.readLine();

            while(conteudo != null){

                String[] vetor = conteudo.split(",");
                
                String user = vetor[0];
                String pass = vetor[1];

                if((user.equals(usuario)) && (pass.equals(senha))){
                   return 1;
                }
                conteudo = br.readLine();
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return 0;
    }

    public static void LimparTela() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

}
