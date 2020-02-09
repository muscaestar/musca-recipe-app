package xyz.muscaestar.muscarecipeapp.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import xyz.muscaestar.muscarecipeapp.domain.Recipe;
import xyz.muscaestar.muscarecipeapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    RecipeRepository recipeRepository;

    final Long ID = 1L;
    final String SOURCE = "fake image source";
    Recipe recipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipe = new Recipe();
        recipe.setId(ID);
    }

    @Test
    public void saveImageFile() throws IOException {
        //given
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "Spring Framework Guru".getBytes());

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(recipe, multipartFile);

        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }

    @Test
    public void getImageByRecipeId() {
        Byte[] fakeImage = new Byte[SOURCE.getBytes().length];
        int i = 0;
        for (byte b : SOURCE.getBytes()) {
            fakeImage[i++] = b;
        }

        recipe.setImage(fakeImage);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(recipe));

        assertEquals(SOURCE.getBytes().length, imageService.getImageByRecipeId(ID).length);
    }
}