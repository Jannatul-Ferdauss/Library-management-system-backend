package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Entity.Feedback;
import com.example.LibraryMS.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Endpoint to submit feedback
    @PostMapping("/submit/{studentId}/{adminId}")
    public Feedback submitFeedback(
            @PathVariable Long studentId,
            @PathVariable Long adminId,
            @RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(studentId, adminId, feedback);
    }

    // Endpoint to get feedback for a specific admin
    @GetMapping("/admin/{adminId}")
    public List<Feedback> getFeedbackByAdmin(@PathVariable Long adminId) {
        return feedbackService.getFeedbackByAdmin(adminId);
    }
}
