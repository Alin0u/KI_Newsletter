import {Component, OnInit} from '@angular/core';
import {ContactListService} from "../../services/contact-list.service";

/**
 * Component for managing contact lists.
 */
@Component({
    selector: 'app-contactlist',
    templateUrl: './contactlist.component.html',
    styleUrls: ['./contactlist.component.css']
})
export class ContactlistComponent implements OnInit {
    contactLists: { name: string; contacts: string }[] = [];
    selectedContacts = '';
    selectedList: any = null;
    selectedFile: File | null = null;
    displayMode: 'editor' | 'contacts' = 'contacts';

    /**
     * Constructs the ContactlistComponent.
     *
     * @param contactListService The service to manage contact lists.
     */
    constructor(private contactListService: ContactListService) {
    }

    /**
     * Initializes the component by loading the contact lists.
     */
    ngOnInit(): void {
        this.loadContactLists();
    }

    /**
     * Loads the contact lists from the backend.
     */
    loadContactLists(): void {
        this.contactListService.getContactLists().subscribe(
            data => {
                this.contactLists = data;
            },
            error => console.error(error)
        );
    }


    /**
     * Handles the selection of a contact list.
     *
     * @param list The selected contact list.
     */
    onSelectContactList(list: any): void {
        this.selectedList = list;
        this.selectedContacts = list.content;
    }

    /**
     * Triggers the file input for importing contacts.
     */
    triggerFileInput() {
        const fileInput = document.querySelector('.file-input') as HTMLElement;
        fileInput.click();
    }

    /**
     * Handles file selection for importing contacts.
     *
     * @param event The file selection event.
     */
    onFileSelect(event: Event): void {
        const target = event.target as HTMLInputElement;
        this.selectedFile = (target.files as FileList)[0];
        if (this.selectedFile) {
            this.importCSV();
        }
        target.value = '';
    }

    /**
     * Adds a new contact list.
     */
    addContactList(): void {
        const newListName = window.prompt("Enter the name for the contact list:");
        if (newListName) {
            const listExists = this.contactLists.some(list => list.name === newListName);
            if (listExists) {
                        alert("A list with this name already exists. Please choose a different name.");
            } else {
                this.contactListService.createContactList(newListName).subscribe(
                    response => {
                        this.contactLists.push({name: newListName, contacts: ""});
                        this.loadContactLists();
                    },
                    error => console.error('Error when creating the contact list:', error)
                );
            }
        }
    }

    /**
     * Deletes the selected contact list.
     */
    deleteContactList(): void {
        if (this.selectedList) {
            this.contactListService.deleteContactList(this.selectedList.name).subscribe(
                response => {
                    this.selectedContacts = '';
                    this.selectedList = null;
                    this.loadContactLists();
                },
                error => console.error(error)
            );
        } else {
            alert("Please select a list to delete first.");
        }
    }


    /**
     * Handles the import of contacts from a CSV file.
     */
    importCSV(): void {
        if (this.selectedFile) {
            if (!this.selectedList) {
                alert("Please select a list first before importing a CSV file.");
                return;
            }

            const fileReader = new FileReader();
            fileReader.readAsText(this.selectedFile, 'UTF-8');
            fileReader.onload = (e) => {
                const csvData = fileReader.result as string;
                this.processCSVData(csvData);
            };
        }
    }

    /**
     * Processes the data from a CSV file and updates the contact list.
     *
     * @param csvData The CSV data as a string.
     */
    processCSVData(csvData: string): void {
        this.selectedContacts = this.processContacts(csvData);
        this.selectedList.contacts = this.selectedContacts;
    }

    /**
     * Validates if a string is a valid email format.
     *
     * @param email The email string to validate.
     * @return True if the email is valid, false otherwise.
     */
    isValidEmail(email: string): boolean {
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        return emailRegex.test(email);
    }


    /**
     * Saves changes to the selected contact list.
     * Updates the list in the backend if a list is selected.
     */
    saveContactList(): void {
        if (this.selectedList) {
            this.selectedList.contacts = this.selectedContacts;
            this.contactListService.saveContactList(this.selectedList.name, this.selectedList.contacts).subscribe(
                response => {
                    console.log('Contact lists saved', response);
                    this.loadContactLists();
                },
                error => console.error(error)
            );
        } else {
            console.error('No list selected');
        }
    }

    /**
     * Processes contact information from a given string.
     *
     * @param input The input string containing contact information.
     * @return A string with processed contacts.
     */
    processContacts(input: string): string {
        return input
            .split(/,|\n/)
            .map(email => email.trim())
            .filter(email => this.isValidEmail(email))
            .join(', ');
    }

     addNewContact(): void {
        const newContactEmail = window.prompt('Enter the new contact\'s email:');
        if (newContactEmail && this.isValidEmail(newContactEmail)) {
          if (this.selectedContacts.split(', ').includes(newContactEmail)) {
            alert('This email is already in the list.');
            return;
          }

          this.selectedContacts = this.selectedContacts
            ? `${this.selectedContacts}, ${newContactEmail}`
            : newContactEmail;

          this.selectedList.contacts = this.selectedContacts;
          this.saveContactList();
        } else if (newContactEmail) {
          alert('Please enter a valid email address.');
        }
     }

    /**
     * Deletes a contact from the selected contact list.
     *
     * @param contactEmail The email of the contact to be deleted.
     */
    deleteContact(contactEmail: string): void {
        this.selectedContacts = this.selectedContacts
          .split(', ')
          .filter(email => email !== contactEmail)
          .join(', ');
        this.selectedList.contacts = this.selectedContacts;
        this.saveContactList();
    }
}



