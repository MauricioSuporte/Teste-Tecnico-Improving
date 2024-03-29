package br.com.improving.carrinho;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */
public class CarrinhoCompras {

	private final Map<Produto, Item> itens = new HashMap<>();

    /**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param produto
     * @param valorUnitario
     * @param quantidade
     */
	public void adicionarItem(Produto produto, BigDecimal valorUnitario, Integer quantidade) {
		validarItem(produto, valorUnitario, quantidade);

		if (itens.containsKey(produto)) {
			atualizarItemExistente(produto, valorUnitario, quantidade);
		} else {
			adicionarNovoItem(produto, valorUnitario, quantidade);
		}
	}

	private void validarItem(Produto produto, BigDecimal valorUnitario, Integer quantidade) {
		validarParametrosItem(produto, valorUnitario, quantidade);
		validarQuantidadeEValor(valorUnitario, quantidade);
	}

	private void validarParametrosItem(Produto produto, BigDecimal valorUnitario, Integer quantidade) {
		if (produto == null || quantidade == null || valorUnitario == null) {
			throw new IllegalArgumentException("Produto, quantidade e valor unitário não podem ser nulos");
		}
	}

	private void validarQuantidadeEValor(BigDecimal valorUnitario, Integer quantidade) {
		if (quantidade <= 0 || valorUnitario.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Quantidade e valor unitário devem ser positivos");
		}
	}

	private void atualizarItemExistente(Produto produto, BigDecimal valorUnitario, int quantidade) {
		Item item = itens.get(produto);
		item.setQuantidade(item.getQuantidade() + quantidade);
		item.setValorUnitario(valorUnitario);
	}

	private void adicionarNovoItem(Produto produto, BigDecimal valorUnitario, int quantidade) {
		Item item = new Item(produto, valorUnitario, quantidade);
		itens.put(produto, item);
	}

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param produto
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(Produto produto) {
		return itens.remove(produto) != null;
    }

    /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na 
     * coleção, em que zero representa o primeiro item.
     *
     * @param posicaoItem
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removerItem(int posicaoItem) {
		final int[] contador = {posicaoItem};
		return itens.values().removeIf(item -> contador[0]-- == 0);
    }

    /**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
    public BigDecimal getValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (Item item : itens.values()) {
			total = total.add(item.getValorTotal());
		}
		return total;
    }

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return itens
     */
    public Collection<Item> getItens() {
		return itens.values();
    }
}