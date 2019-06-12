package co.test.ada.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.test.ada.entity.Marker;

@Repository
public interface MarkerDao extends CrudRepository<Marker, Long> {
	
	Marker findByNombre(String nombre);
	
	
	
}
