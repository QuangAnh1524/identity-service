package qanh.indentityservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qanh.indentityservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
