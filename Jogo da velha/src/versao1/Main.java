package versao1;
import java.security.SecureRandom;
import java.util.Scanner;
public class Main {
	
	public static void jogo1 (char tab[][]) {
		int op;
		String player;
		SecureRandom random = new SecureRandom();
		int numJogador, linha, coluna, verif;
		Scanner n = new Scanner(System.in);
		
		limparTabuleiro(tab);
		
		System.out.print("Insira seu nome jogador: ");
		player = n.nextLine();
		System.out.println();
		menuComputer();
		op = n.nextInt();
		System.out.println();
		
		numJogador = random.nextInt(2);
		numJogador += 1;
		
		if (numJogador == 1) {
			System.out.println("O jogador joga primeiro!");
		} else {
			System.out.println("O computador joga primeiro!");
		}
		
		exibeTabuleiro(tab);
		do{
			if (numJogador == 1) {
				System.out.println("Vez de "+player+"!");
				System.out.println("-> Linha e Coluna: 1 a 3");
				System.out.print("Linha: ");
				linha = n.nextInt();
				linha -= 1;
				System.out.print("Coluna: ");
				coluna = n.nextInt();
				coluna -= 1;
				
				verif = verificaJogada(tab, linha, coluna, numJogador);
				exibeTabuleiro(tab);
				
				if (verif == -1) {
					System.out.println("Jogada invalida! Esta posicao nao esta disponivel!");
				} else if (verif == 5) {
					System.out.println("Posicao preenchida com Sucesso!");
				}
				System.out.println("-------------------------------");
			} else {
				System.out.println("Vez do computador!");
				if (op == 1) {
					verif = computadorFacil(tab, numJogador);
				} else {
					verif = computadorDificil(tab, numJogador);
				}
				System.out.println("-------------------------------");
				exibeTabuleiro(tab);
			}
			
			if (verif == 1) {
				System.out.println("|>>>>> O jogador "+player+" venceu! <<<<<|");
			} else if (verif == 2) {
				System.out.println("|>>>>> O computador venceu! <<<<<|");
			} else if (verif == 0) {
				System.out.println("Empate!");
			}
			
			//lógica para alternar o jogador
			if (verif != -1) { ////Se o jogador digitar errado(-1) so podera trocar o simbolo quando estiver certo
				if (numJogador == 1) {
				numJogador = 2;
				} else {
					numJogador = 1;
				}
			}
			
		} while(verif != 1 && verif != 2 && verif != 0);
		System.out.println();
	}
	
	public static int computadorFacil (char tab [][], int numJogador) {
		int x, y, verif;
		SecureRandom random = new SecureRandom();
		
		do{
			x = random.nextInt(3);
			y = random.nextInt(3);
			verif = verificaJogada(tab, x, y, numJogador);
		} while (verif == -1);
		
		return verif;
	}
	
	public static int computadorDificil (char tab[][], int numJogador) {
		int x, y, verif;
		SecureRandom random = new SecureRandom();
		
		if (chanceGanhar(tab, 2, 'O') == true) {
			verif = 2; //computador ganhou
		} else if (chanceGanhar(tab, 2, 'X') == true) {
			verif = 5; //computador impediu a batida
		} else {
			do{
				x = random.nextInt(3);
				y = random.nextInt(3);
				verif = verificaJogada(tab, x, y, numJogador);
			} while (verif == -1);
		}
		return verif;
	}
	
	public static boolean chanceGanhar(char tab[][], int numJogador, char car) {
		int i;
		
		//verifica se existe possibilidade de ganhar pela diagonal principal
		if (tab[0][0] == tab[1][1] && tab[0][0] == car && tab[2][2] == ' ') {
			preenchePosicao(tab, 2, 2, numJogador);
			return true;
		} else if (tab[0][0] == tab[2][2] && tab[0][0] == car && tab[1][1] == ' ') {
			preenchePosicao(tab, 1, 1, numJogador);
			return true;
		} else if (tab[1][1] == tab[2][2] && tab[1][1] == car && tab[0][0] == ' ') {
			preenchePosicao(tab, 0, 0, numJogador);
			return true;
			
		//verifica se existe possibilidade de ganhar pela diagonal secundaria
		} else if (tab[0][2] == tab[1][1] && tab[0][2] == car && tab[2][0] == ' ') {
			preenchePosicao(tab, 2, 0, numJogador);
			return true;
		} else if (tab[0][2] == tab[2][0] && tab[0][2] == car && tab[1][1] == ' ') {
			preenchePosicao(tab, 1, 1, numJogador);
			return true;
		} else if (tab[1][1] == tab[2][0] && tab[1][1] == car && tab[0][2] == ' ') {
			preenchePosicao(tab, 0, 2, numJogador);
			return true;
		} 
		
		for (i=0; i<3;i++) {
			//verifica se existe possibilidade de ganhar pelas linhas
			if (tab[i][0] == tab[i][1] && tab[i][0] == car && tab[i][2] == ' ') {
				preenchePosicao(tab, i, 2, numJogador);
				return true;
			} else if (tab[i][0] == tab[i][2] && tab[i][0] == car && tab[i][1] == ' ') {
				preenchePosicao(tab, i, 1, numJogador);
				return true;
			} else if (tab[i][1] == tab[i][2] && tab[i][1] == car && tab[i][0] == ' ') {
				preenchePosicao(tab, i, 0, numJogador);
				return true;
				
			//verifica se existe possibilidade de ganhar pelas colunas	
			} else if (tab[0][i] == tab[1][i] && tab[0][i] == car && tab[2][i] == ' ') {
				preenchePosicao(tab, 2, i, numJogador);
				return true;
			} else if (tab[0][i] == tab[2][i] && tab[0][i] == car && tab[1][i] == ' ') {
				preenchePosicao(tab, 1, i, numJogador);
				return true;
			} else if (tab[1][i] == tab[2][i] && tab[1][i] == car && tab[0][i] == ' ') {
				preenchePosicao(tab, 0, i, numJogador);
				return true;
			}
		}
		return false; //se chegou ate aqui e porque nao tem chance de ganhar
	}
	
	public static void jogo2 (char tab[][]) {
		String player [] = new String [2];
		SecureRandom random = new SecureRandom();
		Scanner n = new Scanner(System.in);
		int numJogador, verif, linha, coluna;
		
		limparTabuleiro(tab);
		
		System.out.print("Insira seu nome jogador 1: ");
		player[0] = n.nextLine();
		System.out.print("Insira seu nome jogador 2: ");
		player[1] = n.nextLine();	
		
		numJogador = random.nextInt(2);
		numJogador += 1;
		
		System.out.println("Jogador "+numJogador+" joga primeiro!");
		
		do{
			exibeTabuleiro(tab);
			if (numJogador == 1) {
				System.out.println("Vez de "+player[0]+"!");
			} else {
				System.out.println("Vez de "+player[1]+"!");
			}
			System.out.println("-> Linha e Coluna: 1 a 3");
			System.out.print("Linha: ");
			linha = n.nextInt();
			linha -= 1;
			System.out.print("Coluna: ");
			coluna = n.nextInt();
			coluna -= 1;
			
			verif = verificaJogada(tab, linha, coluna, numJogador);
			
			if (verif == 1) {
				System.out.println("|>>>>> O jogador "+player[0]+" venceu! <<<<<|");
			} else if (verif == 2) {
				System.out.println("|>>>>> O jogador "+player[1]+" venceu! <<<<<|");
			} else if (verif == 0) {
				System.out.println("Empate!");
			} else if (verif == -1) {
				System.out.println("Jogada invalida! Esta posicao nao esta disponivel!");
			} else {
				System.out.println("Posicao preenchida com Sucesso!");
			}
			
			//lógica para alternar o jogador
			if (verif != -1) { //Se o jogador digitar errado(-1) so podera trocar o simbolo quando estiver certo
				if (numJogador == 1) {
				numJogador = 2;
				} else {
					numJogador = 1;
				}
			}
			
		} while(verif != 1 && verif != 2 && verif != 0);
		
		exibeTabuleiro(tab);
	}
	
	public static int verificaJogada (char tab [][], int x, int y, int numJogador) {
		int i, j, cont = 0;
		boolean status;
		char car;
		
		for (i=0; i<tab.length; i++) {
			for (j=0; j<tab[i].length; j++) {
				car = tab[i][j];
				if (car != ' ') {
					cont++;
				}
			}
		}
		
		if (cont == 8) {
			//solucao para o problema: dado o empate, ainda é feita a solicitacao de entrada de dados
			//visto isso, o contador vai so ate 8 e depois o preenchimento é feito
			preenchePosicao(tab, x, y, numJogador);
			return 0;
		} else if (tab[x][y] != ' ') {
			return -1;
		} else {
			preenchePosicao(tab, x, y, numJogador);
		}
		
		status = ganhou(tab, numJogador);
		
		if (numJogador == 1) { //verifica se o jogador 1 venceu ou nao.
			if (status == true) {
				return 1;
			} else {
				return 5;//numero aleatorio para simbolizar que ninguem venceu.
			}
		} else {
			if (status == true) { //verifica se o jogador 2 venceu ou nao.
				return 2;
			} else {
				return 5;//numero aleatorio para simbolizar que ninguem venceu.
			}
		}
		
	}
	
	public static boolean ganhou (char tab [][], int numJogador) {
		int i, j = 0;
		int contaLinha [] = new int [tab.length];
		int contaColuna [] = new int [tab[0].length];
		int contaDiagonais [] = new int [2];
		char car;
		
		if (numJogador == 1) {
			car = 'X';
		} else {
			car = 'O';
		}
		
		//verifica se o jogador ganhou pelas linhas ou colunas;
		for (i=0; i<tab.length; i++) {
			for (j=0; j<tab[i].length; j++) {
				if (tab[i][j] == car) {
					contaLinha[i]++;
					if (contaLinha[i] == 3) {
						return true;
					}
					contaColuna[j]++;
					if (contaColuna[j] == 3) {
						return true;
					}
				}
			}
		}
		
		//verifica se o jogador ganhou pelas diagonais
		j = 2;
		for (i=0; i<tab.length; i++) {
			if (tab[i][i] == car) {
				contaDiagonais[0]++;
				if (contaDiagonais[0] == 3) {
					return true;
				}
			} 
			if (tab[i][j] == car) {
				contaDiagonais[1]++;
				if (contaDiagonais[1] == 3) {
					return true;
				}
			}
			j--;
		}

		return false; //se chegou ate aqui, e porque o jogador nao ganhou ainda
	}
	
	public static void preenchePosicao (char tab [][], int x, int y, int numJogador) {
		char car;
		
		if (numJogador == 1) {
			car = 'X';
		} else {
			car = 'O';
		}
		
		tab[x][y] = car;
	}
	
	public static void exibeTabuleiro(char tab [][]) {
		int i, j;
		
		for (i=0; i<tab.length; i++) {
			System.out.print("| ");
			for (j=0; j<tab[i].length; j++) {
				System.out.print(tab[i][j]+" ");
			}
			System.out.println("|");
		}
	}
	
	public static void limparTabuleiro(char tab[][]) {
		int i, j;
		
		for (i=0; i<tab.length; i++) {
			for (j=0; j<tab[i].length; j++) {
				tab[i][j] = ' ';
			}
		}
	}
	
	public static void menuInicial(){
		System.out.println("--- Jogo da Velha ---");
		System.out.println("1- Jogar");
		System.out.println("2- Sair");
		System.out.print("Digite sua opcao: ");
	}
	
	public static void menuPrincipal() {
		System.out.println(">>> Menu Principal <<<");
		System.out.println("1- Um jogador (Jogador VS Computador)");
		System.out.println("2- Dois jogadores (Jogador VS Jogador)");
		System.out.print("Digite sua opcao: ");
	}
	
	public static void menuComputer() {
		System.out.println("--- Nivel Dificuldade ---");
		System.out.println("1- Facil");
		System.out.println("2- Dificil");
		System.out.print("Digite sua opcao: ");
	}

	public static void main(String[] args) {
		char tabuleiro [][] = new char [3][3];
		int op, resp;
		Scanner n = new Scanner(System.in);
		
		do {
			menuInicial();
			resp = n.nextInt();
			System.out.println();
			
			switch (resp) {
			case 1: menuPrincipal();
					op = n.nextInt();
					System.out.println();
					if (op == 1) {
						jogo1(tabuleiro);
					} else {
						jogo2(tabuleiro);
					}
					break;
			case 2: System.out.println("Fim de Jogo!");
					break;
			default: System.out.println("Opcao invalida!");
					 break;
			}
		} while (resp != 2); 
		
	}
	
}