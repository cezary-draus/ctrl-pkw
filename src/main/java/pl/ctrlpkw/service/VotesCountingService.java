package pl.ctrlpkw.service;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pl.ctrlpkw.api.dto.BallotResult;
import pl.ctrlpkw.model.read.Ballot;
import pl.ctrlpkw.model.write.Protocol;
import pl.ctrlpkw.model.write.ProtocolAccessor;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class VotesCountingService {

    @Resource
    ResultsSelector resultsSelector;

    @Resource
    private ProtocolAccessor protocolAccessor;

    public BallotResult sumVotes(Ballot ballot) {
        return sumVotes(protocolAccessor.findByBallot(
                pl.ctrlpkw.model.write.Ballot.builder()
                        .votingDate(ballot.getVoting().getDate().toDate())
                        .no(ballot.getNo())
                        .build()));
    }

    public BallotResult sumVotes(Iterable<Protocol> protocols) {
        return StreamUtils.aggregate(StreamSupport.stream(protocols.spliterator(), false), Protocol::isSameWard)
                .map(resultsSelector)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(new BallotResult(0l, 0l, 0l, 0l, Lists.newArrayList()), BallotResult::add);
    }

}
