package br.edu.nassau.apicursos.service;

import br.edu.nassau.apicursos.model.Curso;
import br.edu.nassau.apicursos.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso salvar(Curso curso) {
        validarCurso(curso);
        return cursoRepository.save(curso);
    }

    public Optional<Curso> atualizar(Long id, Curso dadosAtualizados) {
        validarCurso(dadosAtualizados);
        return cursoRepository.findById(id).map(cursoExistente -> {
            cursoExistente.setNome(dadosAtualizados.getNome());
            cursoExistente.setCargaHoraria(dadosAtualizados.getCargaHoraria());
            return cursoRepository.save(cursoExistente);
        });
    }

    public boolean deletar(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarCurso(Curso curso) {
        if (curso.getNome() == null || curso.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (curso.getCargaHoraria() == null || curso.getCargaHoraria() <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }
    }
}