package curso.springboot.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Anderson Rocha
 *
 */
@Entity
public class Historico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String ambienteAnterior;

	private String setorAnterior;

	private String funcionario;

	private String ambienteAtual;

	private String setorAtual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmbienteAnterior() {
		return ambienteAnterior;
	}

	public void setAmbienteAnterior(String ambienteAnterior) {
		this.ambienteAnterior = ambienteAnterior;
	}

	public String getSetorAnterior() {
		return setorAnterior;
	}

	public void setSetorAnterior(String setorAnterior) {
		this.setorAnterior = setorAnterior;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getAmbienteAtual() {
		return ambienteAtual;
	}

	public void setAmbienteAtual(String ambienteAtual) {
		this.ambienteAtual = ambienteAtual;
	}

	public String getSetorAtual() {
		return setorAtual;
	}

	public void setSetorAtual(String setorAtual) {
		this.setorAtual = setorAtual;
	}

}
