package Mtracker.project.backend.repo;
import Mtracker.project.backend.models.authModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userAuthRepo extends JpaRepository<authModel, Integer>{
	authModel findByEmail(String email);
	
	authModel findByUsername(String username);
}
