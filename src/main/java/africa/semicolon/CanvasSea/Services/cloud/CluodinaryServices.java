package africa.semicolon.CanvasSea.Services.cloud;

import africa.semicolon.CanvasSea.config.AppConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class CluodinaryServices implements CloudServices{
    private AppConfig appConfig;

    @Override
    public String uploadImage(MultipartFile toUpload) {
        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();


        try {
            Map<?,?> response = uploader.upload(toUpload.getBytes(), ObjectUtils.asMap(
                    "public_id", "CanvasSeaFolder/artImages/" + toUpload.getName(),
                    "api_key", appConfig.getCloudApiKey(),
                    "api_secret", appConfig.getCloudApiSecret(),
                    "cloud_name", appConfig.getCloudApiName(),
                    "secure", true
            ));

            return response.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }





    }
}
