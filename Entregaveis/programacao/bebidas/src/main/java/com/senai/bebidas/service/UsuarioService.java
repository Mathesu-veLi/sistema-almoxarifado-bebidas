package com.senai.bebidas.service;

import com.senai.bebidas.dto.UsuarioDTO;
import com.senai.bebidas.dto.UsuarioRequest;
import com.senai.bebidas.dto.UsuarioUpdateRequest;
import com.senai.bebidas.entity.Usuario;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO cadastrar(UsuarioRequest request) {
        if (usuarioRepository.existsByLogin(request.getLogin())) {
            throw new BusinessException("Login já cadastrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setLogin(request.getLogin());
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(true);
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar(String ativo) {
        List<Usuario> usuarios;
        if ("todos".equalsIgnoreCase(ativo)) {
            usuarios = usuarioRepository.findAll();
        } else if ("false".equalsIgnoreCase(ativo)) {
            usuarios = usuarioRepository.findByAtivoFalse();
        } else {
            usuarios = usuarioRepository.findByAtivoTrue();
        }
        return usuarios.stream().map(this::toDTO).toList();
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        if (!usuario.getLogin().equals(request.getLogin()) && usuarioRepository.existsByLogin(request.getLogin())) {
            throw new BusinessException("Login já cadastrado");
        }
        usuario.setNome(request.getNome());
        usuario.setLogin(request.getLogin());
        usuario.setAtivo(request.isAtivo());
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    @Transactional
    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioUpdateRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        if (!usuario.getLogin().equals(request.getLogin()) && usuarioRepository.existsByLogin(request.getLogin())) {
            throw new BusinessException("Login já cadastrado");
        }
        usuario.setNome(request.getNome());
        usuario.setLogin(request.getLogin());
        usuario.setAtivo(request.isAtivo());
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNome(), u.getLogin(), u.isAtivo());
    }
}
