import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contactlist',
  templateUrl: './contactlist.component.html',
  styleUrls: ['./contactlist.component.css']
})
export class ContactlistComponent implements OnInit {
  contactLists: { name: string; contacts: string[] }[] = []; // Replace with actual data source
  dummyList1: string[] = ["email1@example.com", "email2@example.com"]
  dummyList2: string[] = ["mail1@example.com", "mail2@example.com"]
  selectedContacts = '';
  selectedList: any = null;
  selectedFile: File | null = null;

  constructor() {}

  ngOnInit(): void {
    this.contactLists = [
      { name: "Liste 1", contacts: this.dummyList1 },
      { name: "Liste 2", contacts: this.dummyList2 },
      { name: "Liste 3", contacts: this.dummyList1 },
      { name: "Liste 4", contacts: this.dummyList2 }
    ];
  }

  onSelectContactList(list: any): void {
    this.selectedList = list;
    this.selectedContacts = list.contacts.join(', ');
  }

  triggerFileInput() {
    const fileInput = document.querySelector('.file-input') as HTMLElement;
    fileInput.click();
  }
  onFileSelect(event: Event): void {
    const target = event.target as HTMLInputElement;
    this.selectedFile = (target.files as FileList)[0];
    if (this.selectedFile) {
      this.importCSV();
    }

    // Setze das File-Input-Element zurück
    target.value = '';
  }

  addContactList(): void {
    const newListName = window.prompt("Name der neuen Kontaktliste:");
    if (newListName) {
      this.contactLists.push({ name: newListName, contacts: [] });
    }
  }

  deleteContactList(): void {
    if (this.selectedList) {
      this.contactLists = this.contactLists.filter(list => list !== this.selectedList);
      this.selectedList = null;
      this.selectedContacts = '';
    } else {
      alert("Bitte wähle zuerst eine Liste zum Löschen aus.");
    }
  }


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

  processCSVData(csvData: string): void {
    const lines = csvData.split('\n');
    lines.forEach(line => {
      const contact = line.trim();
      if (contact && this.isValidEmail(contact)) {
        this.selectedList.contacts.push(contact);
      }
    });

    // Aktualisiere die Ansicht der ausgewählten Kontakte
    this.selectedContacts = this.selectedList.contacts.join(', ');
  }

  isValidEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailRegex.test(email);
  }

    saveContacts(): void {
      // TODO: save logic
    }

    deleteContacts(): void {
      // TODO: delete logic
    }
}
