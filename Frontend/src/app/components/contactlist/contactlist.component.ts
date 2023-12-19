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
        console.log("Ausgewählte Liste:", list);
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
        // Setze das File-Input-Element zurück
        target.value = '';
    }

    /**
     * Adds a new contact list.
     */
    addContactList(): void {
        const newListName = window.prompt("Name der neuen Kontaktliste:");
        if (newListName) {
            this.contactListService.createContactList(newListName).subscribe(
                response => {
                    console.log('Neue Kontaktliste erstellt:', response);
                    this.contactLists.push({name: newListName, contacts: ""});
                    this.loadContactLists(); // Aktualisieren Sie die Kontaktlisten
                },
                error => console.error('Fehler beim Erstellen der Kontaktliste:', error)
            );
        }
    }

    /**
     * Deletes the selected contact list.
     */
    deleteContactList(): void {
        if (this.selectedList) {
            this.contactListService.deleteContactList(this.selectedList.name).subscribe(
                response => {
                    console.log('Kontaktliste gelöscht', response);
                    this.loadContactLists(); // Aktualisieren Sie die Kontaktlisten nach dem Löschen
                },
                error => console.error(error)
            );
        } else {
            alert("Bitte wähle zuerst eine Liste zum Löschen aus.");
        }
    }


    /**
     * Handles the import of contacts from a CSV file.
     */
    importCSV(): void {
        if (this.selectedFile) {
            if (!this.selectedList) {
                alert("Bitte wähle zuerst eine Liste aus, bevor du eine CSV-Datei importierst.");
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
        this.selectedContacts = this.processContacts(csvData); // Bereits ein String
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
            this.selectedList.contacts = this.selectedContacts; // Direkte Zuweisung des String
            this.contactListService.saveContactList(this.selectedList.name, this.selectedList.contacts).subscribe(
                response => console.log('Kontaktlisten gespeichert', response),
                error => console.error(error)
            );
        } else {
            console.error('Keine Liste ausgewählt');
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
            .join(', '); // Konvertiere zurück in einen String
    }
}



