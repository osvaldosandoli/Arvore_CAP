package arvores;

import supermercado.Produto;

public class AVLint {
	private class ARVORE {
		int dado;
		ARVORE dir;
		ARVORE esq;
		int hEsq;
		int hDir;
	}

	public ARVORE root = null;

	public void mostra(ARVORE p) {
		if (p != null) {
			mostra(p.esq);
			System.out.println(p.dado);
			mostra(p.dir);
		}
	}

	public void mostraFB(ARVORE p) {
		if (p != null) {
			mostraFB(p.esq);
			mostraFB(p.dir);
			System.out.println("Dado: " + p.dado + "\t FB = " + (p.hDir - p.hEsq));
		}
	}

	public ARVORE rotacaoEsquerda(ARVORE p) {
		// faz rotação para esquerda em relação ao nó apontado por p
		ARVORE q, temp;
		q = p.dir;
		temp = q.esq;
		q.esq = p;
		p.dir = temp;
		return q;
	}

	public ARVORE rotacaoDireita(ARVORE p) {
		// faz rotação para direita em relação ao nó apontado por p
		ARVORE q, temp;
		q = p.esq;
		temp = q.dir;
		q.dir = p;
		p.esq = temp;
		return q;
	}

	public ARVORE balanceamento(ARVORE p) {
		// analisa FB e realiza rotações necessárias para balancear árvore
		int FB = p.hDir - p.hEsq;
		if (FB > 1) {
			int fbFilhoDir = p.dir.hDir - p.dir.hEsq;
			if (fbFilhoDir >= 0)
				p = rotacaoEsquerda(p);
			else {
				p.dir = rotacaoDireita(p.dir);
				p = rotacaoEsquerda(p);
			}
		} else {
			if (FB < -1) {
				int fbFilhoEsq = p.esq.hDir - p.esq.hEsq;
				if (fbFilhoEsq <= 0)
					p = rotacaoDireita(p);
				else {
					p.esq = rotacaoEsquerda(p.esq);
					p = rotacaoDireita(p);
				}
			}
		}
		return p;
	}

	public void atualizaAlturas(ARVORE p) {
		/*
		 * atualiza informação da altura de cada nó depois da remoção percorre a árvore
		 * usando percurso pós-ordem para ajustar primeiro os nós folhas (profundidade
		 * maior) e depois os níveis acima
		 */
		if (p != null) {
			atualizaAlturas(p.esq);
			if (p.esq == null)
				p.hEsq = 0;
			else if (p.esq.hEsq > p.esq.hDir)
				p.hEsq = p.esq.hEsq + 1;
			else
				p.hEsq = p.esq.hDir + 1;
			atualizaAlturas(p.dir);
			if (p.dir == null)
				p.hDir = 0;
			else if (p.dir.hEsq > p.dir.hDir)
				p.hDir = p.dir.hEsq + 1;
			else
				p.hDir = p.dir.hDir + 1;
		}
	}

	public ARVORE inserirAVL(ARVORE p, int info) {
		if (p == null) { // nó inserido sempre será nó folha
			p = new ARVORE();
			p.dado = info;
			p.esq = null;
			p.dir = null;
			p.hDir = 0;
			p.hEsq = 0;
		} else if (info < p.dado) {
			p.esq = inserirAVL(p.esq, info);
			if (p.esq.hDir > p.esq.hEsq) // Altura do nó será a maior
				p.hEsq = p.esq.hDir + 1; // altura dos seus filhos
			else
				p.hEsq = p.esq.hEsq + 1;
			p = balanceamento(p);
		} else {
			p.dir = inserirAVL(p.dir, info);
			if (p.dir.hDir > p.dir.hEsq)
				p.hDir = p.dir.hDir + 1;
			else
				p.hDir = p.dir.hEsq + 1;
			p = balanceamento(p);
		}
		return p;
	}

	public ARVORE removeValor(ARVORE p, int info) {
		if (p != null) {
			if (info == p.dado) {
				if (p.esq == null && p.dir == null)
					return null;
				else {
					if (p.esq == null)
						return p.dir;
					else if (p.dir == null)
						return p.esq;
					else {
						ARVORE aux, ref;
						ref = p.dir;
						aux = p.dir;
						while (aux.esq != null)
							aux = aux.esq;
						aux.esq = p.esq;
						return ref;
					}
				}
			} else { // procura dado a ser removido na ABB
				if (info < p.dado)
					p.esq = removeValor(p.esq, info);
				else
					p.dir = removeValor(p.dir, info);
			}
		}
		return p;
	}

	public ARVORE atualizaAlturaBalanceamento(ARVORE p) {
		/*
		 * atualiza informação da altura de cada nó depois da remoção percorre a árvore
		 * usando percurso pós-ordem para ajustar primeiro os nós folhas (profundidade
		 * maior) e depois os níveis acima
		 */
		if (p != null) {
			p.esq = atualizaAlturaBalanceamento(p.esq);
			if (p.esq == null)
				p.hEsq = 0;
			else if (p.esq.hEsq > p.esq.hDir)
				p.hEsq = p.esq.hEsq + 1;
			else
				p.hEsq = p.esq.hDir + 1;
			p.dir = atualizaAlturaBalanceamento(p.dir);
			if (p.dir == null)
				p.hDir = 0;
			else if (p.dir.hEsq > p.dir.hDir)
				p.hDir = p.dir.hEsq + 1;
			else
				p.hDir = p.dir.hDir + 1;
			p = balanceamento(p);
		}
		return p;
	}
	public int contaNos(ARVORE p, int cont) {
		if (p != null) {
			cont++;
			cont = contaNos(p.esq, cont);
			cont = contaNos(p.dir, cont);
		}
		return cont;
	}
	

}
