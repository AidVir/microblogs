package com.aidas.microblogs.repository;

import com.aidas.microblogs.model.Blog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
  List<Blog> findAllByUserId(Long userId);
}
