package curso.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;



@Entity
public class Telefone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String numero;
	
	private String tipo;
	
	
	@SuppressWarnings("deprecation")
	@ForeignKey(name="pessoa_id")
	@ManyToOne
	private Pessoa pessoa;
	
	
	
	
	public Long getId() {
		return id;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	



}
