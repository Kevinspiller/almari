import java.util.Scanner;

class Entrada {
	
	public String leLinha(){           //Faz a leitura da linha total, salvando em uma string e retornando a mesma
		Scanner S;
		S = new Scanner(System.in);
		String a = S.nextLine();
		return a;
	}
	
	public String lePalavra() {
		Scanner S;
		S = new Scanner(System.in);
		String a = S.next();
		return a;
	}
	
	public int leInteiro(){				  //Faz a leitura de um int, salvando em um int i e retornando o mesmo
		Scanner S;
		S = new Scanner(System.in);
		int i = S.nextInt();
		S.nextLine();							// como o professor comentou, a leitura de int ou double deixa um /n, por isso essa leitura de linha
		return i;
	}
	
	public double leReal(){			  //Faz a leitura de um double, salvando em um double d e retornando o mesmo
		Scanner S;
		S = new Scanner(System.in);
		double d = S.nextDouble();
		S.nextLine();
		return d;
	}
	
//	public char leCaractere(){				//Faz a leitura de um char, salvando em um char c e retornando o mesmo
//		Scanner S;
//		S = new Scanner(System.in);
//		char c = S.nextInt();
//		S.nextLine();						// não tenho certeza se no char é preciso fazer isso também
//		return c;
//	}
}
	
	//Olhem o email que mandei, pois fiquei com aquela duvida, e mais, tem que fazer o float e o long int/long long ???
