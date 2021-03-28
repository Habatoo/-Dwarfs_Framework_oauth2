import com.angrydwarfs.framework.controllers.UserController;
import com.angrydwarfs.framework.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
//@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Value("${dwarfsframework.app.jwtSecret}")
    private String jwtSecret;

    @Value("${dwarfsframework.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    String username = "admin";
    String password = "12345";

    @Test
    @DisplayName("Проверяет успешную подгрузку контроллера из контекста.")
    public void loadControllers() {
        assertThat(userController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет логин с некорректным паролем.")
    public void loginForbiddenTest() throws Exception{
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"mod\", \"password\": \"123456\" }"))
                .andExpect(status().isUnauthorized());
//                .andExpect(jsonPath("path").value(""))
//                .andExpect(jsonPath("error").value("Unauthorized"))
//                .andExpect(jsonPath("message").value("Bad credentials"))
//                .andExpect(jsonPath("status").value(401));
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя ADMIN.")
    public void testAdminLogin() throws Exception{
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin\", \"password\": \"12345\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("admin"))
                .andExpect(jsonPath("$.userEmail").value("admin@admin.com"))
                .andExpect((jsonPath("$.roles", Matchers.containsInAnyOrder("ROLE_ADMINISTRATOR","ROLE_MODERATOR", "ROLE_USER"))));
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя USER.")
    public void testUserLogin() throws Exception{
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"user\", \"password\": \"12345\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("user"))
                .andExpect(jsonPath("$.userEmail").value("user@user.com"))
                .andExpect((jsonPath("$.roles", Matchers.containsInAnyOrder("ROLE_USER"))));
    }

    @Test
    @DisplayName("Проверяет выход без токена.")
    public void logoutFailTest() throws Exception {
        this.mockMvc.perform(get("/api/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"token\": \"\" }"))
                .andExpect(status().isUnauthorized());
//                .andExpect(jsonPath("path").value(""))
//                .andExpect(jsonPath("error").value("Unauthorized"))
//                .andExpect(jsonPath("message").value("Full authentication is required to access this resource"))
//                .andExpect(jsonPath("status").value(401));

    }

}
