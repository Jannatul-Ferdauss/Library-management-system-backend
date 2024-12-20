package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Entity.KeepsTracksOf;
import com.example.LibraryMS.Service.KeepsTracksOfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class KeepsTracksOfController {

    @Autowired
    private KeepsTracksOfService keepsTracksOfService;

    // Get all tracking records
    @GetMapping
    public ResponseEntity<List<KeepsTracksOf>> getAllTracks() {
        List<KeepsTracksOf> tracks = keepsTracksOfService.getAllTracks();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    // Get tracking records for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<KeepsTracksOf>> getTracksByStudentId(@PathVariable Long studentId) {
        List<KeepsTracksOf> tracks = keepsTracksOfService.getTracksByStudentId(studentId);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    // Get tracking records for a specific admin
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<KeepsTracksOf>> getTracksByAdminId(@PathVariable Long adminId) {
        List<KeepsTracksOf> tracks = keepsTracksOfService.getTracksByAdminId(adminId);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    // Add a tracking record
    @PostMapping
    public ResponseEntity<KeepsTracksOf> addTrack(@RequestBody KeepsTracksOf track) {
        KeepsTracksOf addedTrack = keepsTracksOfService.addTrack(track);
        return new ResponseEntity<>(addedTrack, HttpStatus.CREATED);
    }

    // Delete a tracking record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        keepsTracksOfService.deleteTrack(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}