package africa.semicolon.CanvasSea.Services.cloud;

import africa.semicolon.CanvasSea.Utils.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CloudServicesTest {
    @Autowired
    private CloudServices cloudServices;

    @Test
    void uploadImage() {
        Path path =  Path.of(Validator.filePath);

        try {
            InputStream inputStream = Files.newInputStream(path);
            MultipartFile file = new MockMultipartFile("testImage",inputStream);

            String uploadResponse = cloudServices.uploadImage(file);

            assertNotNull(uploadResponse);

        } catch (IOException e) {
            throw new RuntimeException("image upload fail",e);
        }
    }
}