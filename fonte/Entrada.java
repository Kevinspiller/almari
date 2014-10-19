import java.util.Scanner;

class Entrada {
	public static String leLinha() {
		Scanner S;
		S = new Scanner(System.in);
		String a = S.nextLine();
		return a;
	}
	
	public static String lePalavra() {
		Scanner S;
		S = new Scanner(System.in);
		String a = S.next();
		return a;
	}
	
	public static int leInteiro() {
		Scanner S;
		S = new Scanner(System.in);
		int i = S.nextInt();
		S.nextLine();
		return i;
	}
	
	public static double leReal() {
		Scanner S;
		S = new Scanner(System.in);
		double d = S.nextDouble();
		S.nextLine();
		return d;
	}
}