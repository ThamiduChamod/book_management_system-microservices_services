package lk.ijse.gdse.userservice.repository;

import lk.ijse.gdse.userservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
