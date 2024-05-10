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
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void uploadMultipleImages() {
        List<MultipartFile> files = new ArrayList<>();

        Path path1 = Path.of(Validator.filePath + "sci-fi.jpeg");
        Path path2 = Path.of(Validator.filePath + "Space.jpeg");
        Path path3 = Path.of(Validator.filePath + "Tech.jpeg");

        try {
            InputStream inputStream1 = Files.newInputStream(path1);
            MultipartFile file1 = new MockMultipartFile("image1", inputStream1);

            InputStream inputStream2 = Files.newInputStream(path2);
            MultipartFile file2 = new MockMultipartFile("image2", inputStream2);

            InputStream inputStream3 = Files.newInputStream(path3);
            MultipartFile file3 = new MockMultipartFile("image3", inputStream3);

            files.add(file1);
            files.add(file2);
            files.add(file3);

            List<String> uploadResponses = cloudServices.uploadMultipleImages(files);

            assertNotNull(uploadResponses);
            assertEquals(3, uploadResponses.size());

        } catch (IOException e) {
            throw new RuntimeException("image upload fail", e);
        }
    }

}