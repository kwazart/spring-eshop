package com.polozov.springeshop.service;

import com.polozov.springeshop.dao.UserRepository;
import com.polozov.springeshop.domain.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepository = Mockito.mock(UserRepository.class);

        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @AfterEach
    void afterEach(){
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After All test");
    }

    @Test
    void checkFindByName() {
        //have
        String name = "petr";
        User expectedUser = User.builder().id(1L).name(name).build();

        Mockito.when(userRepository.findFirstByName(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);

    }

    @Test
    void checkFindByNameExact() {
        //have
        String name = "petr";
        User expectedUser = User.builder().id(1L).name(name).build();

        Mockito.when(userRepository.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);
        User rndUser = userService.findByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);

        Assertions.assertNull(rndUser);

    }
}
