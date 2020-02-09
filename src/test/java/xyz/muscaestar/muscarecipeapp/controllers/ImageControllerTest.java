package xyz.muscaestar.muscarecipeapp.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.muscaestar.muscarecipeapp.commands.RecipeCommand;
import xyz.muscaestar.muscarecipeapp.services.ImageService;
import xyz.muscaestar.muscarecipeapp.services.RecipeService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @InjectMocks
    ImageController imageController;

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    RecipeCommand command;
    final Long ID = 1L;
    final String SOURCE = "fake image file";


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

        command = new RecipeCommand();
        command.setId(ID);
    }

    @Test
    public void showUploadForm() throws Exception {
        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
                .andExpect(view().name("recipe/image/imageuploadform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).getRecipeCommandById(anyLong());
    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(any(), any());
    }

    @Test
    public void renderImageFromDBExist() throws Exception {
        Byte[] fakeImage = new Byte[SOURCE.getBytes().length];
        int i = 0;
        for (byte b : SOURCE.getBytes()) {
            fakeImage[i++] = b;
        }

        when(imageService.getImageByRecipeId(anyLong())).thenReturn(fakeImage);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/imagepic"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals(fakeImage.length, response.getContentAsByteArray().length);
    }

    @Test
    public void renderImageFromDBNotExist() throws Exception {

        when(imageService.getImageByRecipeId(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/imagepic"))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetImageNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/asdf/imagepic"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}