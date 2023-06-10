package ly.qubit.inventory.repository;

import ly.qubit.inventory.config.ContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SupplierRepositoryTest {

        @Container
        @ServiceConnection
        static PostgreSQLContainer<?> postgres =
                new PostgreSQLContainer<>("postgres");

        // your tests
    @Test
    void test() {
        assertTrue(postgres.isRunning());
    }
    }

