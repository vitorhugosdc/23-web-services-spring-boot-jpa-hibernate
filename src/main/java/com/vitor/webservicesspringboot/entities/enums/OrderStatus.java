package com.vitor.webservicesspringboot.entities.enums;

public enum OrderStatus {

	/*
	 * Por padrão, o Java atribui um valor número em cada um dos valores do tipo
	 * enumerado, começando em 0, porém, isso tem um problema de manutenção, pois se
	 * um valor for posto entre todos os outros, como "empacotando" entre paid e
	 * shipped, o banco de dados vai ficar errado dele para baixo. Portanto, a gente
	 * atribui manualmente um valor numérico para cada valor do tipo enumerado.
	 * Fazendo isso, o Java exige que seja implementado outras coisas, essas coisas
	 * são todo o código abaixo além dos atributos escritos por extenso, como
	 * WAITING_PAYMENT, etc
	 */
	WAITING_PAYMENT(1), /* O número entre () é o valor */
	PAID(2), 
	SHIPPED(3), 
	DELIVERED(4), 
	CANCELED(5);

	private int code; /* variavel que é o código do tipo enumerado */

	/*
	 * Construtor desse tipo enumerado, percebe-se que ele é um caso especial, por
	 * ser 'private'
	 */
	private OrderStatus(int code) {
		this.code = code;
	}

	/* Método público para conseguir acessar esse código no mundo externo */
	public int getCode() {
		return code;
	}

	/*
	 * Método estático (não precisa ser instanciado) para converter um valor
	 * numérico para o tipo enumerado
	 */
	public static OrderStatus valueOf(int code) {
		for (OrderStatus value : OrderStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid OrderStatus code");
	}
}
