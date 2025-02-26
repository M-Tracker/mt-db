package Mtracker.project.backend.repo;

import Mtracker.project.backend.models.UserProblemsDataModel;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProblemDataRepo extends JpaRepository<UserProblemsDataModel, Long> {
    UserProblemsDataModel findById(Id id);

    @Override
    void delete(UserProblemsDataModel entity);
}
