package com.harshi_solution.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harshi_solution.user.dto.RegisterRepsRequest;
import com.harshi_solution.user.dto.RegisterViewerRequest;
import com.harshi_solution.user.dto.UserResponseDTO;
import com.harshi_solution.user.entities.Reps;
import com.harshi_solution.user.entities.Role;
import com.harshi_solution.user.entities.UserRegisterEntity;
import com.harshi_solution.user.repo.RepsRepository;
import com.harshi_solution.user.repo.UserRegisterEntityRepository;

@Service
public class UserRegisterEntityService implements UserDetailsService {

private final UserRegisterEntityRepository userRepository;

    public UserRegisterEntityService(UserRegisterEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private RepsRepository repsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserRegisterEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Transactional
    public void registerReps(RegisterRepsRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        UserRegisterEntity user = new UserRegisterEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_REPS);

        userRepository.save(user);

        Reps reps = new Reps();
        reps.setRepName(request.getRepName().toUpperCase());
        reps.setRepLocation(request.getRepLocation().toUpperCase());
        reps.setUser(user);

        repsRepository.save(reps);
    }

    public void registerViewer(RegisterViewerRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        UserRegisterEntity user = new UserRegisterEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_VIEW);

        userRepository.save(user);
    }

    public List<UserResponseDTO> getUsersByRole(Role role) {

    return userRepository.findByRole(role)
            .stream()
            .map(user -> new UserResponseDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getRole()
            ))
            .toList();
}


}
