package com.example.spring_app.services;

import com.example.spring_app.dtos.MatchDTO;
import com.example.spring_app.tabledatagateways.MatchTableDataGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchService {

    private final MatchTableDataGateway matchGateway;

    public MatchService(MatchTableDataGateway matchGateway) {
        this.matchGateway = matchGateway;
    }

    public List<MatchDTO> getAllMatches() {
        return matchGateway.getAllMatches();
    }


    public MatchDTO getMatchById(Long id) {
        return matchGateway.getMatchById(id);
    }


    public List<MatchDTO> getMatchesByLeagueId(Long leagueId) {
        return matchGateway.getMatchesByLeagueId(leagueId);
    }


    public MatchDTO createMatch(MatchDTO matchDTO) {
        String conflictMessage = null;

        if (hasConflict(matchDTO.getHomeTeamId(), matchDTO.getRefereeId(), matchDTO.getMatchDate())) {
            conflictMessage = "Home team has a scheduling conflict.";
        }

        if (hasConflict(matchDTO.getAwayTeamId(), matchDTO.getRefereeId(), matchDTO.getMatchDate())) {
            conflictMessage = "Away team has a scheduling conflict.";
        }

        if (hasConflict(null, matchDTO.getRefereeId(), matchDTO.getMatchDate())) {
            conflictMessage = "Referee has a scheduling conflict.";
        }

        if (conflictMessage != null) {
            throw new RuntimeException(conflictMessage);
        }

        matchGateway.createMatch(matchDTO);
        return matchDTO;
    }

    public boolean hasConflict(Long teamId, Long refereeId, LocalDateTime matchDate) {
        LocalDateTime fourHoursBefore = matchDate.minusHours(4);
        LocalDateTime fourHoursAfter = matchDate.plusHours(4);

        Integer conflictCount = matchGateway.countConflicts(teamId, refereeId, fourHoursBefore, fourHoursAfter);
        return conflictCount != null && conflictCount > 0;
    }

    private boolean hasRefereeConflict(MatchDTO matchDTO) {
        Integer count = matchGateway.countRefereeConflicts(matchDTO.getMatchDate(), matchDTO.getRefereeId());
        return count != null && count > 0;
    }


    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        matchGateway.updateMatch(id, matchDTO);
        return matchDTO;
    }


    public void deleteMatch(Long id) {
        matchGateway.deleteMatch(id);
    }

    public List<MatchDTO> getMatchesByTeam(Long teamId) {
        return matchGateway.getMatchesByTeam(teamId);
    }
}
