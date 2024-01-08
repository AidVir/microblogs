package com.aidas.microblogs.controller;

import com.aidas.microblogs.service.ReportsService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
public class ReportsController {

  @Autowired
  private ReportsService reportsService;

  @GetMapping("/word-count/{blogId}")
  public ResponseEntity<Long> getWordCountReport(@PathVariable long blogId) {
    Long wordCount = reportsService.getWordCountByBlog(blogId);
    return new ResponseEntity<>(wordCount, HttpStatus.OK);
  }

  @GetMapping("/most-used-words/{userId}")
  public ResponseEntity<Map<String, Long>> getMostUsedWordsReport(@PathVariable long userId) {
    Map<String, Long> mostUsedWords = reportsService.getMostUsedWordsbyUser(userId, 5);
    return new ResponseEntity<>(mostUsedWords, HttpStatus.OK);
  }

}
