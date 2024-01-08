package com.aidas.microblogs.service;

import com.aidas.microblogs.dto.BlogDto;
import com.aidas.microblogs.exception.ResourceNotFoundException;
import com.aidas.microblogs.model.Blog;
import com.aidas.microblogs.model.User;
import com.aidas.microblogs.repository.BlogRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private UserService userService;

  public List<BlogDto> getAllBlogs() {
    return blogRepository.findAll().stream().map(Blog::toDto).collect(Collectors.toList());
  }

  public Blog getBlogById(Long id) {
    return blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found"));
  }

  public void createBlog(long userId, BlogDto blogDto) {
    User user = userService.getUserById(userId);

    blogRepository.save(BlogDto.toBlog(blogDto, user));
  }

  public void updateBlog(Long id, BlogDto blogDto) {
    Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog with id " + id + " not found"));
    blog.setContent(blogDto.getContent());

    blogRepository.save(blog);
  }

  public void deleteBlog(Long id) {
    blogRepository.deleteById(id);
  }

  public List<BlogDto> getAllBlogsByUserId(Long userId) {
    return blogRepository.findAllByUserId(userId).stream().map(Blog::toDto).collect(Collectors.toList());
  }

}
