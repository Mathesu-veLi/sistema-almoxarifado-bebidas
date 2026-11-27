package com.senai.bebidas.service;

import com.senai.bebidas.dto.SetorDTO;
import com.senai.bebidas.dto.SetorRequest;
import com.senai.bebidas.entity.Setor;
import com.senai.bebidas.exception.BusinessException;
import com.senai.bebidas.exception.EntityNotFoundException;
import com.senai.bebidas.repository.MovimentacaoRepository;
import com.senai.bebidas.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de Setor.
 * Aplica as regras de negocio de cadastro, consulta, atualizacao, exclusao e inativacao de setores.
 */
@Service
@RequiredArgsConstructor
public class SetorService {

    private final SetorRepository setorRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    /**
     * Cadastra um novo setor.
     * Nome e obrigatorio e unico entre os setores ativos.
     */
    @Transactional
    public SetorDTO cadastrar(SetorRequest request) {
        if (setorRepository.existsByNomeAndAtivoTrue(request.getNome())) {
            throw new BusinessException("Nome ja cadastrado para um setor ativo");
        }

        Setor setor = new Setor();
        setor.setNome(request.getNome());
        setor.setDescricao(request.getDescricao());
        setor.setAtivo(true);

        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    /**
     * Busca setor por ID. Retorna inclusive inativos (ativo = false).
     */
    @Transactional(readOnly = true)
    public SetorDTO buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor nao encontrado"));
        return toDTO(setor);
    }

    /**
     * Lista setores.
     * Por padrão mostra apenas ativos.
     * aceita ?ativo=todos (todos) ou ?ativo=false (apenas inativos).
     * Resultado ordenado por id crescente.
     */
    @Transactional(readOnly = true)
    public List<SetorDTO> listar(String ativo) {
        List<Setor> setores;
        if ("todos".equalsIgnoreCase(ativo)) {
            setores = setorRepository.findAll();
        } else if ("false".equalsIgnoreCase(ativo)) {
            setores = setorRepository.findByAtivoFalse();
        } else {
            setores = setorRepository.findByAtivoTrueOrderByIdAsc();
        }
        return setores.stream().map(this::toDTO).toList();
    }

    /**
     * Atualiza nome, descricao e estado ativo do setor.
     * Nome deve continuar unico entre os setores ativos.
     * ativo = true reativa o setor.
     */
    @Transactional
    public SetorDTO atualizar(Long id, SetorRequest request) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor nao encontrado"));

        if (!setor.getNome().equals(request.getNome())
                && setorRepository.existsByNomeAndAtivoTrue(request.getNome())) {
            throw new BusinessException("Nome ja cadastrado para um setor ativo");
        }

        setor.setNome(request.getNome());
        setor.setDescricao(request.getDescricao());
        setor.setAtivo(request.getAtivo());

        setor = setorRepository.save(setor);
        return toDTO(setor);
    }

    /**
     * Exclui o setor se nao houver movimentacoes vinculadas.
     * Caso contrario, inativa logica (ativo = false) preservando o historico.
     * Retorna 204 sem corpo em ambos os casos.
     */
    @Transactional
    public void excluirOuInativar(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor nao encontrado"));

        boolean temVinculos = movimentacaoRepository
                .listarComFiltros(null, id, null, null, null, null)
                .isEmpty() == false;

        if (temVinculos) {
            // Ha historico: so inativa, nunca apaga.
            setor.setAtivo(false);
            setorRepository.save(setor);
        } else {
            // Sem historico: pode excluir definitivamente.
            setorRepository.delete(setor);
        }
    }

    private SetorDTO toDTO(Setor s) {
        return new SetorDTO(s.getId(), s.getNome(), s.getDescricao(), s.isAtivo());
    }
}
