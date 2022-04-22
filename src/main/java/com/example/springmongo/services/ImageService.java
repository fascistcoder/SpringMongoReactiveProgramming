package com.example.springmongo.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 01/10/21
 */
public interface ImageService {
    void saveImageFile(String recipeId, MultipartFile file);
}
