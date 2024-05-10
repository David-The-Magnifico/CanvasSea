package africa.semicolon.CanvasSea.Services.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudServices {
    String uploadImage(MultipartFile toUpload);
}
