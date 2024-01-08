package com.aidas.microblogs.dto;

import com.aidas.microblogs.model.Blog;
import com.aidas.microblogs.model.User;
import java.time.LocalDateTime;

public class BlogDto {

    private long id;
    private String content;
    private String username;
    private LocalDateTime date;

    public BlogDto() {
    }

  public BlogDto(long id, String content, String username, LocalDateTime date) {
    this.id = id;
    this.content = content;
    this.username = username;
    this.date = date;
  }

  public String getContent() {
      return content;
    }


  public static Blog toBlog(BlogDto blogDto, User user) {
      var date = blogDto.getDate();
      if (date == null) {
        date = LocalDateTime.now();
      }

    return new Blog(
      blogDto.getId(),
      blogDto.getContent(),
      user,
      date);
  }

  public long getId() {
    return id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public String getUsername() {
    return username;
  }
}
