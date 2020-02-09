package xyz.muscaestar.muscarecipeapp.services;

import org.springframework.web.multipart.MultipartFile;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;

public interface ImageService {

    void saveImageFile(Recipe recipe, MultipartFile file);

    Byte[] getImageByRecipeId(Long recipeId);
}
