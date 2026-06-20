package com.modex.monolith.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllActiveUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO updateUser(String id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userMapper.applyUpdate(user, dto);
        User saved = userRepository.save(user);

        log.info("Usuario actualizado: {}", saved.getEmail());
        return userMapper.toResponseDTO(saved);
    }

    // Desactivacion logica - no se borra de la base de datos,
    // solo se marca active=false. Asi se preserva el historial
    // de pedidos asociados a ese usuario.
    public void deactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setActive(false);
        userRepository.save(user);

        log.info("Usuario desactivado: {}", user.getEmail());
    }
}
