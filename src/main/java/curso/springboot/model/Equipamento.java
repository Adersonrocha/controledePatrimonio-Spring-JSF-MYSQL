package curso.springboot.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import org.hibernate.annotations.ForeignKey;

@Entity
public class Equipamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	private String codPatrimonio;
	
	
	private String nome;
	
	

	private Double valor;
	
	private String tagEquipamento;
	
	@ManyToOne
	private Categoria categoria;
	
	@ForeignKey(name="ambiente_id")
	@ManyToOne
	private Ambiente ambiente;

	
	
 
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodPatrimonio() {
		return codPatrimonio;
	}

	public void setCodPatrimonio(String codPatrimonio) {
		this.codPatrimonio = codPatrimonio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public String getTagEquipamento() {
		return tagEquipamento;
	}
	public void setTagEquipamento(String tagEquipamento) {
		this.tagEquipamento = tagEquipamento;
	}
	


	@Override
	public String toString() {
		return "Equipamento [id=" + id + ", codPatrimonio=" + codPatrimonio + ", nome=" + nome + ", valor=" + valor
				+ ", tagEquipamento=" + tagEquipamento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codPatrimonio == null) ? 0 : codPatrimonio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (codPatrimonio == null) {
			if (other.codPatrimonio != null)
				return false;
		} else if (!codPatrimonio.equals(other.codPatrimonio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
