package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Equipamento;




@Repository
@Transactional
public interface EquipamentoRepository extends CrudRepository<Equipamento, Long> {
	
	@Query("select p from Equipamento p where p.codPatrimonio like %?1%")
	List<Equipamento> findEquipamentoByName(String cod);

	
	@Query("select e from Equipamento e where e.ambiente.id =?1")
	public List<Equipamento> getEquipamento(Long ambienteid);
	
}
