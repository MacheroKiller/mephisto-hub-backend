package com.amuryllis.mephisto_hub_backend.project;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectRegistryService registryService;

  public ProjectController(ProjectRegistryService registryService) {
    this.registryService = registryService;
  }

  @GetMapping
  public List<ProjectManifest> listAll() {
    return registryService.listAll();
  }
}
