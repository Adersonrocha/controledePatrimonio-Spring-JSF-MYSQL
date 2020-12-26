package curso.springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Setor;

@Transactional
@Repository
public interface SetorRepository extends CrudRepository<Setor, Long>{

	
	
	
	
}
