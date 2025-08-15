package com.bs.file.dto.responses;

import org.springframework.core.io.Resource;

public record FileData(String contentType, Resource resource) {}