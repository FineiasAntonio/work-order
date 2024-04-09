package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Media;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Mapper.OSMapper;
import com.eletroficinagalvao.controledeservico.Exception.CustomExceptions.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;

import com.eletroficinagalvao.controledeservico.Repository.ReservaRepository;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class OSService {

    @Autowired
    private OSRepository repository;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private OSMapper mapper;

    public List<OS> getAll(){
        return repository.findAll();
    }

    public OS getById(int id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi encontrado essa ordem de serviço"));
    }

    @Transactional
    public OS create(CreateOSRequestDTO ordemdeservico){
        OS os = mapper.map(ordemdeservico);

        repository.save(os);
        log.info("Ordem de serviço registrada no nome de: " + os.getFuncionario().getNome());
        return os;
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
        reservaRepository.delete(reservaRepository.findByIdOS(id));
        log.info("OS {} apagada com sucesso", id);
    }

    @Transactional
    public OS update(int id, UpdateOSRequestDTO os){
        OS ordemCorrespondente = repository.findById(id).orElseThrow(() -> new NotFoundException("OS não encontrada"));
        OS ordemAtualizada = mapper.updateMap(ordemCorrespondente, os);

        repository.save(ordemAtualizada);
        log.info("OS atualizada");
        return ordemAtualizada;
    }

    @Transactional
    public void storageMidia(int id, Media media){
        OS os = repository.findById(id).orElseThrow(() -> new NotFoundException("OS não encontrada"));
        List<Media> osMedias = os.getMedias();
        repository.save(os);
        log.info("Imagem guardada");
    }
}
