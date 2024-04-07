package africa.semicolon.CanvasSea;

import africa.semicolon.CanvasSea.Data.Model.Admin;
import africa.semicolon.CanvasSea.Data.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CanvasSeaApplication implements CommandLineRunner {
	@Autowired
	AdminRepository adminRepository;

	public final static String ADMIN_EMAIL = "www.wealthydavid@gmail.com";

	public static void main(String[] args) {
		SpringApplication.run(CanvasSeaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (adminRepository.findByUsername("Admin") == null) {
			Admin admin = new Admin();
			admin.setUsername("Admin");
			admin.setPassword("admin2035");
			admin.setEmail("www.wealthydavid@gmail.com");
			adminRepository.save(admin);}
	}

}
