package curso.springboot.model;

public enum Cargo {

	INSTRUTOR("Instrutor Técnico"),
	CONSULTOR("Consulton Técnico"),
	OPERACIONAL("Apoio Administrativo"),
	ADMINISTRATIVO("Assistente Administrativo");
	
	private String nome;
	private String valor;
	
	private Cargo(String nome) {
		this.nome =nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}

 public void setValor(String valor) {
	this.valor = valor;
}
 public String getValor() {
	return valor= this.name();
}


}
