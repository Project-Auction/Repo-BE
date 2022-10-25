package auctionbe.service;

import auctionbe.models.Role;
import auctionbe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByNameRole(String role) {
        return roleRepository.findByNameRole(role);
    }
}
