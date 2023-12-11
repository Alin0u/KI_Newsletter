package kgn;

import kgn.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() throws IOException {
        fileService = new FileService();
        fileService.createUserDirectory("testuser");
    }

    @AfterEach
    void clearUp() throws IOException {
        Path testUserDirectory = Paths.get("src/main/resources/contactlists", "testuser");
        if (Files.exists(testUserDirectory)) {
            deleteDirectoryRecursively(testUserDirectory);
        }
    }

    @Test
    void testCreateContactList() throws IOException {
        String username = "testuser";
        String filename = "testlist.txt";
        fileService.createContactList(username, filename);
        assertTrue(Files.exists(Paths.get("src/main/resources/contactlists", username, filename)));
    }

    @Test
    void testDeleteContactList() throws IOException {
        String username = "testuser";
        String filename = "deleteTestList.txt";
        fileService.createContactList(username, filename);
        fileService.deleteContactList(username, filename);
        assertFalse(Files.exists(Paths.get("src/main/resources/contactlists", username, filename)));
    }

    @Test
    void testModifyContactList() throws IOException {
        String username = "testuser";
        String filename = "modifyTestList.txt";
        String originalContent = "original@example.com";
        String modifiedContent = "modified@example.com";
        fileService.createContactList(username, filename);
        fileService.updateContactList(username, filename, modifiedContent);
        String fileContent = fileService.readFile(username, filename);
        assertEquals(modifiedContent, fileContent);
    }

    private void deleteDirectoryRecursively(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
