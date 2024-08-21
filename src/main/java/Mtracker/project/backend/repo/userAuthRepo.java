package Mtracker.project.backend.repo;
import Mtracker.project.backend.models.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userAuthRepo extends JpaRepository<AuthModel, Integer>{
	AuthModel findByEmail(String email);
	
	AuthModel findByUsername(String username);
}
