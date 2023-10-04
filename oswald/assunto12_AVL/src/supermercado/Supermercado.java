package supermercado;

import java.util.Scanner;

public class Supermercado {

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		int opcao, codigo;
		AVLProdutos loja = new AVLProdutos();
		
		do {
			System.out.println("0 - Sair");
			System.out.println("1 - Inserir produto no cadastro");
			System.out.println("2 - Listagem de preços");
			System.out.println("3 - Remover Produto");
			System.out.println("4 - Consultar Produto");
			opcao = kb.nextInt();
			
			switch (opcao) {
			case 0: 
				
				System.out.println("Programa encerrado");
				break;
			case 1:
				System.out.print("Informe o código do produto: ");
				codigo = kb.nextInt();
				kb.nextLine();
				Produto produto = loja.consultaCodigo(loja.root, codigo);
				if(produto == null) {
					System.out.print("Descrição: ");
					String descricao = kb.nextLine();
					System.out.print("Preço: R$");
					double preco = kb.nextDouble();
					Produto prod = new Produto(codigo, descricao, preco);
					loja.root = loja.inserirAVL(loja.root, prod);
				}else {
					int aux = codigo;
					while (aux == codigo) {
						System.out.println("Codigo repetido, digite outro");
						codigo = kb.nextInt();
					}
				}
				break;
			case 2:
				System.out.print("Preço limite: ");
				double limite = kb.nextDouble();
				loja.listaMaisBaratos(loja.root, limite);
				break;
			case 3:
				System.out.println("Informe o codigo do produto a ser removido do cadastro");
				codigo = kb.nextInt();
				loja.root = loja.removeValor(loja.root, codigo);
				loja.root = loja.atualizaAlturaBalanceamento(loja.root);
				loja.atualizaAlturas(loja.root);
				loja.mostra(loja.root);
				break;
			case 4:
				System.out.println("INFORME O CODIGO DO PRODUTO A SER CONSULTADO");
				codigo = kb.nextInt();
				 produto = loja.consultaCodigo(loja.root, codigo);
				if(produto == null) 
					System.out.println("Produto nao encontraDO");
				else
					System.out.println(produto);
				break;
				
			default:
				System.out.println("Opção inválida");
			}
		} while(opcao!=0);
		kb.close();
		

	}

}
