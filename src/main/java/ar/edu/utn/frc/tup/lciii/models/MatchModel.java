package ar.edu.utn.frc.tup.lciii.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {

    private Long id;
    private UserModel userModel;
    private MatchDifficulty matchDifficulty;
    private MatchStatus matchStatus;
    private Integer numberToGuess;
    private Integer remainingTries;
}
