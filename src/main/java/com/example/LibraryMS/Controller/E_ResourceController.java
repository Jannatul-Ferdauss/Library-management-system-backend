package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.E_ResourceDto;
import com.example.LibraryMS.Service.E_ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/e_resource")
public class E_ResourceController {

    private final E_ResourceService e_ResourceService;

    @PostMapping
    public ResponseEntity<E_ResourceDto> createEbook(@RequestBody E_ResourceDto e_ResourceDto) {
        E_ResourceDto createdE_Resource = e_ResourceService.createE_Resource(e_ResourceDto);
        return ResponseEntity.ok(createdE_Resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<E_ResourceDto> getE_ResourceById(@PathVariable Long id) {
        E_ResourceDto e_ResourceDto = e_ResourceService.getE_ResourceById(id);
        return ResponseEntity.ok(e_ResourceDto);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<E_ResourceDto> getE_ResourceByTitle(@PathVariable String title) {
        E_ResourceDto e_ResourceDto = e_ResourceService.getE_ResourceByTitle(title);
        return ResponseEntity.ok(e_ResourceDto);
    }

    @GetMapping
    public ResponseEntity<List<E_ResourceDto>> getAllE_Resources() {
        List<E_ResourceDto> e_Resources = e_ResourceService.getAllE_Resources();
        return ResponseEntity.ok(e_Resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<E_ResourceDto> updateE_Resource(@PathVariable Long id, @RequestBody E_ResourceDto e_ResourceDto) {
        E_ResourceDto updatedE_Resource = e_ResourceService.updateE_Resource(id, e_ResourceDto);
        return ResponseEntity.ok(updatedE_Resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEbook(@PathVariable Long id) {
        e_ResourceService.deleteE_Resource(id);
        return ResponseEntity.noContent().build();
    }
}
