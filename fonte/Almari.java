import java.util.Scanner;
import java.io.*;

class Almari {
    public static void main(String args[]) throws Exception {
        File arquivo;
        Scanner scanner;
        Interpretador interpretador;
        String codigo = "";
        
        try {
            arquivo = new File(args[0]);
            scanner = new Scanner(arquivo);
            interpretador = new Interpretador();

            while(scanner.hasNext()) {
                codigo += scanner.nextLine();
            }            
            interpretador.interpreta(codigo);
        } catch (IOException e) {
            System.out.println("Nao foi possivel ler o arquivo: " + (args.length > 0 ? args[0] : "(desconhecido)"));
            System.out.println("Uso:");
            System.out.println("    java Almari /caminho/para/arquivo.amr");
        }
        
    }
}