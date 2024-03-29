package com.example.enterpriseExam.unittests

import com.example.enterpriseExam.NewUserInfo
import com.example.enterpriseExam.model.AuthorityEntity
import com.example.enterpriseExam.model.UserEntity
import com.example.enterpriseExam.service.AnimalService
import com.example.enterpriseExam.service.AuthorityService
import com.example.enterpriseExam.service.UserService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {
    @TestConfiguration
    class ControllerTestConfig {

        @Bean
        fun userService() = mockk<UserService>()

        @Bean
        fun authorityService() = mockk<AuthorityService>()

        @Bean
        fun animalService() = mockk<AnimalService>()
    }

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var authorityService: AuthorityService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetAllUsers() {
        val userBob = UserEntity(email = "test@test.com", password = "password")
        every { userService.getUsers() } answers {
            mutableListOf(userBob)
        }
        mockMvc.get("/api/user"){

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }

    @Test
    fun shouldGetAllAuthorities() {
        val authority = AuthorityEntity(id = 1, authorityName = "USER")
        every { authorityService.getAuthorities() } answers {
            mutableListOf(authority)
        }
        mockMvc.get("/api/authentication/all"){

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }

    @Test
    fun shouldUpdateAuthority() {
        val authority = AuthorityEntity(id = 1, authorityName = "USER")
        every { authorityService.updateAuthority(1, any()) } answers {
            authority
        }
        mockMvc.put("/api/user/edituser/1"){
            contentType = APPLICATION_JSON
            content = "{\"authorityName\":\"ADMIN\"}"

        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }

    @Test
    fun testRegisterEndpoint(){
        every { userService.registerUser(NewUserInfo("test@test.com", "password")) } answers {
            val user = UserEntity(id = 1, email = "test@test.com", password = BCryptPasswordEncoder().encode("pirate"))
            user
        }

        mockMvc.post("/api/register"){
            contentType = APPLICATION_JSON
            content = "{\"email\":\"test@test.com\", \"password\":\"password\"}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }
}