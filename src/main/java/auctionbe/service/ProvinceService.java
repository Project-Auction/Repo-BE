package auctionbe.service;

import auctionbe.models.Province;
import auctionbe.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    public Province save(Province province) {
        return provinceRepository.save(province);
    }
}
