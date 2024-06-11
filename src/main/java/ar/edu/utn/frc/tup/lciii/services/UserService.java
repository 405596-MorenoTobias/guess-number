package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.models.MatchDifficulty;
import ar.edu.utn.frc.tup.lciii.models.MatchModel;
import ar.edu.utn.frc.tup.lciii.models.RoundMatch;
import ar.edu.utn.frc.tup.lciii.models.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel createUser(String userName, String email);
    MatchModel createUserMatch(Long userId, MatchDifficulty difficulty);
    RoundMatch playUserMatch(Long userId, Long matchId, Integer numberToPlay);
}
