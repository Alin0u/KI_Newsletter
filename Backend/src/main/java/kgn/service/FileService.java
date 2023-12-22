package kgn.service;

import kgn.model.ContactListData;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.io.File;
import java.util.stream.Collectors;

/**
 * Service for managing file operations related to contact lists.
 */
@Service
public class FileService {

    private final Path rootLocation;

    public FileService() {
        this.rootLocation = Paths.get("Backend/contactlists").toAbsolutePath();
    }

    /**
     * Lists all contact list files for a given user.
     *
     * @param username the username whose contact lists are to be listed
     * @return a list of filenames representing the user's contact lists
     * @throws IOException if the user directory does not exist
     */
    public List<ContactListData> listContactLists(String username) throws IOException {
        Path userDirectory = rootLocation.resolve(username);
        if (!Files.exists(userDirectory)) {
            throw new IOException("User directory does not exist");
        }

        return Files.list(userDirectory)
                .filter(Files::isRegularFile)
                .map(path -> {
                    try {
                        String content = new String(Files.readAllBytes(path));
                        return new ContactListData(path.getFileName().toString(), content);
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading file: " + path, e);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Creates a new contact list.
     *
     * @param username the username for whom to create the contact list
     * @param filename the name of the new contact list file
     * @throws IOException if the file already exists or cannot be created
     */
    public void createContactList(String username, String filename) throws IOException {
        Path userDirectory = rootLocation.resolve(username);
        Path filePath = userDirectory.resolve(filename);
        if (Files.exists(filePath)) {
            throw new IOException("File already exists");
        }
        Files.createFile(filePath);
        String defaultContent = "example@example.com, example2@example.com";
        Files.write(filePath, defaultContent.getBytes());
    }

    /**
     * Deletes a contact list.
     *
     * @param username the username for whom to delete the contact list
     * @param filename the name of the contact list file to delete
     * @throws IOException if the file does not exist or cannot be deleted
     */
    public void deleteContactList(String username, String filename) throws IOException {
        Path userDirectory = rootLocation.resolve(username);
        Path file = userDirectory.resolve(filename);
        if (!Files.exists(file)) {
            throw new IOException("File does not exist");
        }
        Files.delete(file);
    }

    /**
     * Updates the content of a contact list file.
     *
     * @param username the username for whom to update the contact list
     * @param filename the name of the contact list file to update
     * @param content the new content to be written to the file
     * @throws IOException if the file does not exist or cannot be updated
     */
    public void updateContactList(String username, String filename, String content) throws IOException {
        Path userDirectory = rootLocation.resolve(username);
        Path file = userDirectory.resolve(filename);
        if (!Files.exists(file)) {
            throw new IOException("File does not exist");
        }
        Files.write(file, content.getBytes());
    }


    /**
     * Reads the content of a contact list file.
     *
     * @param username the username for whom to read the contact list
     * @param filename the name of the contact list file to read
     * @return the content of the file as a String
     * @throws IOException if the file does not exist or cannot be read
     */
    public String readFile(String username, String filename) throws IOException {
        Path userDirectory = rootLocation.resolve(username);
        Path file = userDirectory.resolve(filename);
        return new String(Files.readAllBytes(file));
    }

    /**
     * Creates a directory for storing contact lists.
     *
     * @param username the username for whom to create the directory
     * @throws IOException if the directory cannot be created
     */
    public void createUserDirectory(String username) throws IOException {
        Path userDirectory = rootLocation.resolve(username);
        if (!Files.exists(userDirectory)) {
            Files.createDirectories(userDirectory);
        }
    }
}