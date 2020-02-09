package xyz.muscaestar.muscarecipeapp.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.services.ImageService;
import xyz.muscaestar.muscarecipeapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(id)));

        return "recipe/image/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        Recipe recipe = recipeService.getRecipeById(new Long(id));
        imageService.saveImageFile(recipe, file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/imagepic")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        Byte[] image = imageService.getImageByRecipeId(Long.valueOf(id));

        if (image != null) {
            byte[] unboxByte = new byte[image.length];

            int i = 0;
            for (Byte b : image) {
                unboxByte[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(unboxByte);
            IOUtils.copy(inputStream, response.getOutputStream());
        }

    }
}
