import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface DialogData {
  contactLists: any[];
}

@Component({
  selector: 'app-contact-list-dialog',
  templateUrl: './contact-list-dialog.component.html',
  styleUrls: ['./contact-list-dialog.component.css']
})
export class ContactListDialogComponent {

  /**
   * Constructs the contact list dialog component.
   *
   * @param dialogRef Reference to the dialog.
   * @param data Data containing the contact lists.
   */
  constructor(
    public dialogRef: MatDialogRef<ContactListDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {}

  /**
   * Closes the dialog and returns the selected list.
   *
   * @param list The selected contact list.
   */
  onSelect(list: any): void {
    this.dialogRef.close(list);
  }
}
