package curso.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Ambiente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Nome do Ambiente não pode ser nulo")
	@NotEmpty(message = "Nome do Ambiente não pode ser vazio")
	private String nomeAmbiente;
	

	@ManyToOne
	private Setor setor;
	
	
	private String tagAmbiente;
	
	@OneToMany(mappedBy = "ambiente")
	private List<Equipamento> equipamentos;

	// essa variavel local, irá ser atribuido com o nome do objeto Setor
	private String local;
	
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
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

	public String getNomeAmbiente() {
		return nomeAmbiente;
	}

	public void setNomeAmbiente(String nomeAmbiente) {
		this.nomeAmbiente = nomeAmbiente;
	}



	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public String getTagAmbiente() {
		return tagAmbiente;
	}

	public void setTagAmbiente(String tagAmbiente) {
		this.tagAmbiente = tagAmbiente;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(Setor local) {
		this.local = local.getNome();
	}
	
	
}
