package com.interview.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.interview.entity.Role;
import com.interview.enums.RoleType;
import com.interview.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner{

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub

        createRoleIfNotExists(RoleType.ROLE_ADMIN.name());

        createRoleIfNotExists(RoleType.ROLE_STUDENT.name());

        createRoleIfNotExists(RoleType.ROLE_INTERVIEWER.name());
        
        
    }
    
    private void createRoleIfNotExists(String roleName){
        roleRepository.findByRoleName(roleName)
            .orElseGet(() -> {
                
                Role role = new Role();

                role.setRoleName(roleName);

                return roleRepository.save(role);
            });
    }

}
