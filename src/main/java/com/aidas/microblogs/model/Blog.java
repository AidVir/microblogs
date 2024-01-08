package com.aidas.microblogs.model;

import com.aidas.microblogs.dto.BlogDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
public class Blog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "content", length = 1000)
  private String content;

  @Column(name = "date")
  private LocalDateTime date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Blog() {
  }

  public Blog(long id, String content, User user, LocalDateTime date) {
    this.id = id;
    this.content = content;
    this.user = user;
    this.date = date;
  }

  public static BlogDto toDto(Blog blog) {
    return new BlogDto(blog.getId(), blog.getContent(), blog.getUser().getUsername(), blog.getDate());
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public User getUser() {
    return user;
  }
}
