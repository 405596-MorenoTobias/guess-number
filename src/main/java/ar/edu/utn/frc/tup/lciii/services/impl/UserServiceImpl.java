package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.entities.UserEntity;
import ar.edu.utn.frc.tup.lciii.models.MatchDifficulty;
import ar.edu.utn.frc.tup.lciii.models.MatchModel;
import ar.edu.utn.frc.tup.lciii.models.RoundMatch;
import ar.edu.utn.frc.tup.lciii.models.UserModel;
import ar.edu.utn.frc.tup.lciii.repositories.UserRepository;
import ar.edu.utn.frc.tup.lciii.services.MatchService;
import ar.edu.utn.frc.tup.lciii.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    @Qualifier("modelMaper")
    private ModelMapper modelMapper;
    @Autowired
    private MatchService matchService;

    @Override
    public UserModel createUser(String userName, String email) {
        Optional<UserEntity> userEntityOptional = userRepository.getByEmail(email);
        if (userEntityOptional.isPresent()) {
            //TODO: Enviar error al usuario.
            return null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setEmail(email);
            UserEntity userEntitySaved = userRepository.save(userEntity);
            return modelMapper.map(userEntitySaved, UserModel.class);
        }
    }

    @Override
    public MatchModel createUserMatch(Long userId, MatchDifficulty difficulty) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            UserModel userModel = modelMapper.map(userEntityOptional, UserModel.class);
            return matchService.createMatch(userModel, difficulty);
        }
    }

    @Override
    public RoundMatch playUserMatch(Long userId, Long matchId, Integer numberToPlay) {
        MatchModel matchModel = matchService.getMatchById(matchId);
        if (!matchModel.getUserModel().getId().equals(userId)) {
            //TODO: ERROR
            return null;
        } else {
            return matchService.playMatch(matchModel, numberToPlay);
        }
    }
}
