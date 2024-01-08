package com.aidas.microblogs.service;

import com.aidas.microblogs.dto.BlogDto;
import com.aidas.microblogs.model.Blog;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportsService {

  @Autowired
  private BlogService blogService;

  public long getWordCountByBlog(long blogId) {
    Blog blog = blogService.getBlogById(blogId);
    String content = blog.getContent();

    return getWordCount(content);
  }

  private static long getWordCount(String str) {
    if (str == null || str.isEmpty()) {
      return 0;
    }

    return Arrays.stream(str.split("\\s+"))
      .count();
  }

  public Map<String, Long> getMostUsedWordsbyUser(long userId, int topCount) {
    List<String> contentList = blogService.getAllBlogsByUserId(userId).stream()
      .map(BlogDto::getContent)
      .collect(Collectors.toList());

    return getMostUsedWords(contentList, topCount);
  }

  private static Map<String, Long> getMostUsedWords(List<String> contentList, int topCount) {
    List<String> words = contentList.stream()
      .flatMap(line -> Arrays.stream(line.split("\\s+")))
      .collect(Collectors.toList());

    Map<String, Long> wordCounts = words.stream()
      .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

    return wordCounts.entrySet().stream()
      .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
      .limit(topCount)
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
}