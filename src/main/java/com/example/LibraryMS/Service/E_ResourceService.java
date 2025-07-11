package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.E_ResourceDto;

import java.util.List;

public interface E_ResourceService {
    E_ResourceDto createE_Resource(E_ResourceDto e_ResourceDto);

    E_ResourceDto getE_ResourceById(Long eresourceId);

    E_ResourceDto getE_ResourceByTitle(String title);

    List<E_ResourceDto> getAllE_Resources();

    E_ResourceDto updateE_Resource(Long id, E_ResourceDto e_ResourceDto);

    void deleteE_Resource(Long id);
}