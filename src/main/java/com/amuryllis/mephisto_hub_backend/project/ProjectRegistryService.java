package com.amuryllis.mephisto_hub_backend.project;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProjectRegistryService {

  private final ObjectMapper objectMapper;

  public ProjectRegistryService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public List<ProjectManifest> listAll() {
    try {
      var resolver = new PathMatchingResourcePatternResolver();
      Resource[] resources = resolver.getResources("classpath:projects/*.json");

      return java.util.Arrays.stream(resources)
          .map(this::readManifest)
          .sorted(Comparator.comparingInt(ProjectManifest::order))
          .toList();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to list project manifests", e);
    }
  }

  private ProjectManifest readManifest(Resource resource) {
    try {
      return objectMapper.readValue(resource.getInputStream(), ProjectManifest.class);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read manifest: " + resource.getFilename(), e);
    }
  }
}
