package africa.semicolon.CanvasSea.Services.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudServices {
    String uploadImage(MultipartFile toUpload);

    List<String> uploadMultipleImages(List<MultipartFile> toUpload);
}
