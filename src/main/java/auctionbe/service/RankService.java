package auctionbe.service;

import auctionbe.models.Rank;
import auctionbe.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankService {
    @Autowired
    private RankRepository rankRepository;

    public Rank findByName(String name) {
        return rankRepository.findByName(name);
    }
}
