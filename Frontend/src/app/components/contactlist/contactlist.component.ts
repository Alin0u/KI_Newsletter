import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contactlist',
  templateUrl: './contactlist.component.html',
  styleUrls: ['./contactlist.component.css']
})
export class ContactlistComponent implements OnInit {
  contactLists = []; // Replace with actual data source
  selectedContacts = '';
  selectedList: any = null;

  constructor() { }

  ngOnInit(): void {
    // Load contact lists here
  }

  onSelectContactList(list: any): void {
    this.selectedList = list;
    this.selectedContacts = list.contacts.join(', ');
  }

    saveContacts(): void {
      // TODO: save logic
    }

    deleteContacts(): void {
      // TODO: delete logic
    }
}
