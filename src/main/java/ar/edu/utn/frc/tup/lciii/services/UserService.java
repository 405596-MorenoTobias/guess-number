package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.models.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel createUser(String userName, String email);
}
