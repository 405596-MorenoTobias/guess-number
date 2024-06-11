package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.entities.MatchEntity;
import ar.edu.utn.frc.tup.lciii.entities.UserEntity;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.repositories.MatchRepository;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    @Qualifier("modelMaper")
    private ModelMapper modelMapper;
    private static final Random random = new Random();

    @Override
    public MatchModel createMatch(UserModel userModel, MatchDifficulty difficulty) {
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setUserEntity(modelMapper.map(userModel, UserEntity.class));
        matchEntity.setMatchDifficulty(difficulty);
        switch (difficulty) {
            case HARD -> matchEntity.setRemainingTries(5);
            case MEDIUM -> matchEntity.setRemainingTries(8);
            case EASY -> matchEntity.setRemainingTries(10);
        }
        matchEntity.setNumberToGuess(random.nextInt(101));
        matchEntity.setMatchStatus(MatchStatus.PLAYING);
        matchEntity.setCreatedAt(LocalDateTime.now());
        matchEntity.setUpdatedAt(LocalDateTime.now());
        MatchEntity matchEntitySaved = matchRepository.save(matchEntity);
        return modelMapper.map(matchEntitySaved, MatchModel.class);
    }

    @Override
    public RoundMatch playMatch(MatchModel matchModel, Integer number) {
        RoundMatch roundMatch = new RoundMatch();
        roundMatch.setMatchModel(matchModel);
        if (matchModel.getMatchStatus().equals(MatchStatus.FINISH)) {
            //TODO: error
            return null;
        }
        if (matchModel.getNumberToGuess().equals(number)) {
            //TODO: Calcular Score
            roundMatch.setRespuesta("GANÓ");
        } else {
            matchModel.setRemainingTries(matchModel.getRemainingTries() - 1);
            if (matchModel.getRemainingTries().equals(0)) {
                matchModel.setMatchStatus(MatchStatus.FINISH);
                roundMatch.setRespuesta("PERDIÓ");
            } else {
                if (number > matchModel.getNumberToGuess()) {
                    roundMatch.setRespuesta("MENOR");
                } else {
                    roundMatch.setRespuesta("MAYOR");
                }
            }
        }
        UserEntity userEntity = modelMapper.map(matchModel.getUserModel(),UserEntity.class);
        MatchEntity matchEntity = modelMapper.map(matchModel, MatchEntity.class);
        matchEntity.setUserEntity(userEntity);
        matchEntity.setUpdatedAt(LocalDateTime.now());
        matchRepository.save(matchEntity);
        return roundMatch;
    }

    @Override
    public MatchModel getMatchById(Long matchId) {
        Optional<MatchEntity> matchEntityOptional = matchRepository.findById(matchId);
        if (matchEntityOptional.isPresent()) {
            return modelMapper.map(matchEntityOptional, MatchModel.class);

        } else {
            throw new EntityNotFoundException();
        }
    }
}
