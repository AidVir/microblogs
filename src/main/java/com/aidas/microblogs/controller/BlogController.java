package com.aidas.microblogs.controller;

import com.aidas.microblogs.dto.BlogDto;
import com.aidas.microblogs.model.Blog;
import com.aidas.microblogs.service.BlogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER')")
public class BlogController {
  @Autowired
  private BlogService blogService;

  @GetMapping("/blogs")
  public ResponseEntity<List<BlogDto>> getAllBlogs() {
    List<BlogDto> blogs = blogService.getAllBlogs();
    return new ResponseEntity<>(blogs, HttpStatus.OK);
  }

  @GetMapping("/users/{userId}/blogs")
  public ResponseEntity<List<BlogDto>> getAllBlogsByUserId(@PathVariable("userId") long userId) {
    List<BlogDto> blogs = blogService.getAllBlogsByUserId(userId);
    return new ResponseEntity<>(blogs, HttpStatus.OK);
  }

  @GetMapping("/blogs/{id}")
  public ResponseEntity<BlogDto> getBlogById(@PathVariable("id") long id) {
    BlogDto blog = Blog.toDto(blogService.getBlogById(id));
    return new ResponseEntity<>(blog, HttpStatus.OK);
  }

  @PostMapping("/users/{userId}/blogs")
  public ResponseEntity<?> createBlog(@PathVariable("userId") long userId, @RequestBody BlogDto blogDto) {
    blogService.createBlog(userId, blogDto);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/blogs/{id}")
  public ResponseEntity<?> updateBlog(@PathVariable("id") long id, @RequestBody BlogDto blogDto) {
    blogService.updateBlog(id, blogDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/blogs/{id}")
  public ResponseEntity<?> deleteBlog(@PathVariable("id") long id) {
    blogService.deleteBlog(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
