package com.example.LibraryMS.Service;

import com.example.LibraryMS.Entity.KeepsTracksOf;
import com.example.LibraryMS.Repository.KeepsTracksOfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeepsTracksOfService {

    @Autowired
    private KeepsTracksOfRepository keepsTracksOfRepository;

    public KeepsTracksOf addTrack(KeepsTracksOf track) {
        return keepsTracksOfRepository.save(track);
    }

    public List<KeepsTracksOf> getAllTracks() {
        return keepsTracksOfRepository.findAll();
    }

    public List<KeepsTracksOf> getTracksByStudentId(Long studentId) {
        return keepsTracksOfRepository.findByStudentId(studentId);
    }

    public List<KeepsTracksOf> getTracksByAdminId(Long adminId) {
        return keepsTracksOfRepository.findByAdminId(adminId);
    }

    public void deleteTrack(Long id) {
        keepsTracksOfRepository.deleteById(id);
    }
}
