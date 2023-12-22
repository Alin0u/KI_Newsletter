package kgn.model;

import lombok.Getter;

/**
 * Represents a contact list with a name and content.
 * This class is used to model the data structure of a contact list.
 */
@Getter
public class ContactListData {
    private String name;
    private String content;

    /**
     * Constructs a new ContactListData object with specified name and content.
     *
     * @param name    The name of the contact list.
     * @param content The content of the contact list.
     */
    public ContactListData(String name, String content) {
        this.name = name;
        this.content = content;
    }
}

