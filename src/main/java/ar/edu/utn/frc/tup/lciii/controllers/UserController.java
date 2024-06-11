package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.*;
import ar.edu.utn.frc.tup.lciii.models.MatchDifficulty;
import ar.edu.utn.frc.tup.lciii.models.MatchModel;
import ar.edu.utn.frc.tup.lciii.models.RoundMatch;
import ar.edu.utn.frc.tup.lciii.models.UserModel;
import ar.edu.utn.frc.tup.lciii.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guess-number/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("modelMaper")
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserModel userModel = userService.createUser(userDto.getUserName(), userDto.getEmail());
        UserDto userDto1 = modelMapper.map(userModel, UserDto.class);
        return ResponseEntity.ok(userDto1);
    }

    @PostMapping("{userId}/matches")
    public ResponseEntity<MatchDto> createUserMatch(@PathVariable Long userId,
                                                    @RequestBody CreateUserMatchDto createUserMatchDto) {
        MatchModel matchModel = userService.createUserMatch(userId, createUserMatchDto.getDifficulty());
        MatchDto matchDto = modelMapper.map(matchModel, MatchDto.class);
        return ResponseEntity.ok(matchDto);
    }

    @PostMapping("{userId}/matches/{matchId}")
    public ResponseEntity<RoundMatchDto> playUserMatch(@PathVariable Long userId,
                                                       @PathVariable Long matchId,
                                                       @RequestBody PlayUserMatchDto playUserMatchDto) {
        RoundMatch roundMatch = userService.playUserMatch(userId, matchId, playUserMatchDto.getNumber());
        MatchDto matchDto = modelMapper.map(roundMatch.getMatchModel(),MatchDto.class);
        RoundMatchDto roundMatchDto = modelMapper.map(roundMatch, RoundMatchDto.class);
        roundMatchDto.setMatchDto(matchDto);
        return ResponseEntity.ok(roundMatchDto);
    }
}
