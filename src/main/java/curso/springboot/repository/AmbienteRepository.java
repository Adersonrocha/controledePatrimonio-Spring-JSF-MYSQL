package curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Ambiente;


@Repository
@Transactional
public interface AmbienteRepository extends CrudRepository<Ambiente, Long> {

	@Query("select p from Ambiente p where p.nomeAmbiente like %?1%")
	List<Ambiente> findAmbienteByName(String nome);
	
	
	
	
}
