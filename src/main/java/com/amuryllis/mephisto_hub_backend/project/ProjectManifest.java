package com.amuryllis.mephisto_hub_backend.project;

import java.util.List;

public record ProjectManifest(
    String id,
    String name,
    String tagline,
    String status,
    List<String> category,
    List<String> stack,
    String demoUrl,
    String repoUrl,
    String backendRepoUrl,
    String docsUrl,
    String accentColor,
    String coverImage,
    List<String> screenshots,
    String problem,
    String architectureDiagram,
    boolean featured,
    int order) {}
