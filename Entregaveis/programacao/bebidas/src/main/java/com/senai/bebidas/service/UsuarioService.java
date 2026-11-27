package com.senai.bebidas.service;

import com.senai.bebidas.dto.UsuarioDTO;
import com.senai.bebidas.dto.UsuarioRequest;
import com.senai.bebidas.dto.UsuarioUpdateRequest;
import com.senai.bebidas.entity.Usuario;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de Usuario.
 * Aplica as regras de negocio de cadastro, consulta, atualizacao e inativacao de usuarios.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Cadastra um novo usuario (endpoint publico).
     * Senha e guardada como hash. Login deve ser unico.
     */
    @Transactional
    public UsuarioDTO cadastrar(UsuarioRequest request) {
        if (usuarioRepository.existsByLogin(request.getLogin())) {
            throw new BusinessException("Login ja cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setLogin(request.getLogin());
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(true);

        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    /**
     * Busca usuario por ID. Retorna inclusive inativos (ativo = false).
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
        return toDTO(usuario);
    }

    /**
     * Busca usuario pelo login (usado no /me e no login).
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
    }

    /**
     * Lista usuarios.
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos (todos) ou ?ativo=false (apenas inativos).
     * Resultado ordenado por id crescente.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar(String ativo) {
        List<Usuario> usuarios;
        if ("todos".equalsIgnoreCase(ativo)) {
            usuarios = usuarioRepository.findAll();
        } else if ("false".equalsIgnoreCase(ativo)) {
            usuarios = usuarioRepository.findByAtivoFalse();
        } else {
            usuarios = usuarioRepository.findByAtivoTrueOrderByIdAsc();
        }
        return usuarios.stream().map(this::toDTO).toList();
    }

    /**
     * Atualiza nome, login e estado ativo do usuario.
     * Nao permite alterar senha. Login deve continuar unico.
     * ativo = true reativa o usuario.
     */
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioUpdateRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        if (!usuario.getLogin().equals(request.getLogin())
                && usuarioRepository.existsByLogin(request.getLogin())) {
            throw new BusinessException("Login ja cadastrado");
        }

        usuario.setNome(request.getNome());
        usuario.setLogin(request.getLogin());
        usuario.setAtivo(request.getAtivo());

        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    /**
     * Inativacao logica do usuario (ativo = false).
     * Nao apaga o registro nem o historico.
     */
    @Transactional
    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO toDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNome(), u.getLogin(), u.isAtivo());
    }
}
