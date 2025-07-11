package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.E_ResourceDto;
import com.example.LibraryMS.Entity.E_Resource;
import com.example.LibraryMS.Exception.ResourceNotFoundException;
import com.example.LibraryMS.Repository.E_ResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class E_ResourceServiceImpl implements E_ResourceService {

    private final E_ResourceRepository e_ResourceRepository;

    @Override
    public E_ResourceDto createE_Resource(E_ResourceDto e_ResourceDto) {
        // Manually mapping EbookDto to Ebook
        E_Resource e_Resource = new E_Resource();
        e_Resource.setTitle(e_ResourceDto.getTitle());
        e_Resource.setCover(e_ResourceDto.getCover());
        e_Resource.setPdf(e_ResourceDto.getPdf());
        e_Resource.setAudioLink(e_ResourceDto.getAudioLink());

        E_Resource savedE_Resource = e_ResourceRepository.save(e_Resource);

        // Manually mapping saved Ebook to EbookDto
        E_ResourceDto savedE_ResourceDto = new E_ResourceDto();
        savedE_ResourceDto.setEresourceId(savedE_Resource.getEresourceId());
        savedE_ResourceDto.setTitle(savedE_Resource.getTitle());
        savedE_ResourceDto.setCover(savedE_Resource.getCover());
        savedE_ResourceDto.setPdf(savedE_Resource.getPdf());
        savedE_ResourceDto.setAudioLink(savedE_Resource.getAudioLink());

        return savedE_ResourceDto;
    }

    @Override
    public E_ResourceDto getE_ResourceById(Long eResourceId) {
        E_Resource e_Resource = e_ResourceRepository.findById(eResourceId)
                .orElseThrow(() -> new ResourceNotFoundException("E_Resource doesn't exist with given id: " + eResourceId));

        // Manually mapping E_Resource to E_ResourceDto
        E_ResourceDto e_ResourceDto = new E_ResourceDto();
        e_ResourceDto.setEresourceId(e_Resource.getEresourceId());
        e_ResourceDto.setTitle(e_Resource.getTitle());
        e_ResourceDto.setCover(e_Resource.getCover());
        e_ResourceDto.setPdf(e_Resource.getPdf());
        e_ResourceDto.setAudioLink(e_Resource.getAudioLink());

        return e_ResourceDto;
    }

    @Override
    public E_ResourceDto getE_ResourceByTitle(String title) {
        E_Resource e_Resource = e_ResourceRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException("E_Resource with title \"" + title + "\" not found"));

        // Manually mapping E_Resource to E_ResourceDto
        E_ResourceDto e_ResourceDto = new E_ResourceDto();
        e_ResourceDto.setEresourceId(e_Resource.getEresourceId());
        e_ResourceDto.setTitle(e_Resource.getTitle());
        e_ResourceDto.setCover(e_Resource.getCover());
        e_ResourceDto.setPdf(e_Resource.getPdf());
        e_ResourceDto.setAudioLink(e_Resource.getAudioLink());

        return e_ResourceDto;
    }

    @Override
    public List<E_ResourceDto> getAllE_Resources() {
        List<E_Resource> e_Resources = e_ResourceRepository.findAll();
        // Manually mapping List<E_Resource> to List<E_ResourceDto>
        return e_Resources.stream().map(e_Resource -> {
            E_ResourceDto e_ResourceDto = new E_ResourceDto();
            e_ResourceDto.setEresourceId(e_Resource.getEresourceId());
            e_ResourceDto.setTitle(e_Resource.getTitle());
            e_ResourceDto.setCover(e_Resource.getCover());
            e_ResourceDto.setPdf(e_Resource.getPdf());
            e_ResourceDto.setAudioLink(e_Resource.getAudioLink());
            return e_ResourceDto;
        }).collect(Collectors.toList());
    }

    @Override
    public E_ResourceDto updateE_Resource(Long eresourceId, E_ResourceDto updateE_Resource) {
        E_Resource e_Resource = e_ResourceRepository.findById(eresourceId)
                .orElseThrow(() -> new ResourceNotFoundException("E_Resource doesn't exist with given id: " + eresourceId));

        // Updating fields manually
        e_Resource.setCover(updateE_Resource.getCover());
        e_Resource.setTitle(updateE_Resource.getTitle());
        e_Resource.setPdf(updateE_Resource.getPdf());
        e_Resource.setAudioLink(updateE_Resource.getAudioLink());

        E_Resource updatedE_Resource = e_ResourceRepository.save(e_Resource);

        // Manually mapping updated E_Resource to E_ResourceDto
        E_ResourceDto updatedE_ResourceDto = new E_ResourceDto();
        updatedE_ResourceDto.setEresourceId(updatedE_Resource.getEresourceId());
        updatedE_ResourceDto.setTitle(updatedE_Resource.getTitle());
        updatedE_ResourceDto.setCover(updatedE_Resource.getCover());
        updatedE_ResourceDto.setPdf(updatedE_Resource.getPdf());
        updatedE_ResourceDto.setAudioLink(updatedE_Resource.getAudioLink());

        return updatedE_ResourceDto;
    }

    @Override
    public void deleteE_Resource(Long eresourceId) {
        E_Resource eResource = e_ResourceRepository.findById(eresourceId)
                .orElseThrow(() -> new ResourceNotFoundException("E_Resource doesn't exist with given id: " + eresourceId));

        e_ResourceRepository.deleteById(eresourceId);
    }
}
