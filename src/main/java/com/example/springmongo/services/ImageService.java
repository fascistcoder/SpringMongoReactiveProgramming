package com.example.springmongo.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 01/10/21
 */
public interface ImageService {
    Mono<Void> saveImageFile(String recipeId, MultipartFile file);
}
