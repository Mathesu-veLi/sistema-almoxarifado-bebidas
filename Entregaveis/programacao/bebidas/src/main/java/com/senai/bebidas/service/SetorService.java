package com.senai.bebidas.service;

import com.senai.bebidas.dto.SetorDTO;
import com.senai.bebidas.dto.SetorRequest;
import com.senai.bebidas.entity.Setor;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.ValidationException;
import com.senai.bebidas.repository.MovimentacaoRepository;
import com.senai.bebidas.repository.SetorRepository;
import com.senai.bebidas.util.FiltroAtivo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

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
        Boolean filtro = FiltroAtivo.interpretar(ativo);
        List<Setor> setores;
        if (filtro == null) {
            setores = setorRepository.findAll();
        } else if (filtro) {
            setores = setorRepository.findByAtivoTrue();
        } else {
            setores = setorRepository.findByAtivoFalse();
        }
        setores.sort(Comparator.comparing(Setor::getId));
        return setores.stream().map(this::toDTO).toList();
    }

    @Transactional
    public SetorDTO atualizar(Long id, SetorRequest request) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
        if (request.getAtivo() == null) {
            throw new ValidationException("O campo ativo é obrigatório");
        }
        if (!setor.getNome().equals(request.getNome()) && setorRepository.existsByNomeAndAtivoTrue(request.getNome())) {
            throw new BusinessException("Nome já cadastrado para um setor ativo");
        }
        setor.setNome(request.getNome());
        setor.setDescricao(request.getDescricao());
        setor.setAtivo(request.getAtivo());
        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    @Transactional
    public void excluirOuInativar(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));
        if (movimentacaoRepository.existsBySetorId(id)) {
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
