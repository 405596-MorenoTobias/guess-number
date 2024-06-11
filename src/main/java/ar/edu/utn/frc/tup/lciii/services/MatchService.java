package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.models.MatchDifficulty;
import ar.edu.utn.frc.tup.lciii.models.MatchModel;
import ar.edu.utn.frc.tup.lciii.models.RoundMatch;
import ar.edu.utn.frc.tup.lciii.models.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {
    MatchModel createMatch(UserModel userModel, MatchDifficulty difficulty);

    MatchModel getMatchById(Long matchId);

    RoundMatch playMatch(MatchModel matchModel, Integer number);
}
