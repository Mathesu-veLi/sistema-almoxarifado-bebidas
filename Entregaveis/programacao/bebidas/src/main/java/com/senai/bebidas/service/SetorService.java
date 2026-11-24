package com.senai.bebidas.service;

import com.senai.bebidas.dto.SetorDTO;
import com.senai.bebidas.dto.SetorRequest;
import com.senai.bebidas.entity.Setor;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;

    @Transactional
    public SetorDTO cadastrar(SetorRequest request) {
        if (setorRepository.existsByNomeAndAtivoTrue(request.getNome())) {
            throw new BusinessException("Nome já cadastrado para um setor ativo");
        }
        Setor setor = new Setor();
        setor.setNome(request.getNome());
        setor.setDescricao(request.getDescricao());
        setor.setAtivo(true);
        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    @Transactional(readOnly = true)
    public SetorDTO buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
        return toDTO(setor);
    }

    @Transactional(readOnly = true)
    public List<SetorDTO> listar(String ativo) {
        List<Setor> setores;
        if ("todos".equalsIgnoreCase(ativo)) {
            setores = setorRepository.findAll();
        } else if ("false".equalsIgnoreCase(ativo)) {
            setores = setorRepository.findByAtivoFalse();
        } else {
            setores = setorRepository.findByAtivoTrue();
        }
        setores.sort((a, b) -> a.getId().compareTo(b.getId()));
        return setores.stream().map(this::toDTO).toList();
    }

    @Transactional
    public SetorDTO atualizar(Long id, SetorRequest request) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
        if (!setor.getNome().equals(request.getNome()) && setorRepository.existsByNomeAndAtivoTrue(request.getNome())) {
            throw new BusinessException("Nome já cadastrado para um setor ativo");
        }
        setor.setNome(request.getNome());
        setor.setDescricao(request.getDescricao());
        setor.setAtivo(request.isAtivo());
        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    @Transactional
    public void excluirOuInativar(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
        boolean temVinculos = false;
        if (temVinculos) {
            setor.setAtivo(false);
            setorRepository.save(setor);
        } else {
            setorRepository.delete(setor);
        }
    }

    private SetorDTO toDTO(Setor s) {
        return new SetorDTO(s.getId(), s.getNome(), s.getDescricao(), s.isAtivo());
    }
}
