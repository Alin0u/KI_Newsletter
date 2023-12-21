import { Component } from '@angular/core';
import { NlpService } from 'src/app/services/nlp.service';
import { MailService } from 'src/app/services/mail/mail.service';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { ContactListService } from 'src/app/services/contact-list.service';
import { MatDialog } from '@angular/material/dialog';
import {ContactListDialogComponent} from "../contact-list-dialog/contact-list-dialog.component";
import {AppLoadingComponent} from "../app-loading/app-loading.component";
import { MatSnackBar } from '@angular/material/snack-bar';
import {TextProcessingService} from "../../services/text-processing.service";

/**
 * Component for creating and sending newsletters.
 *
 * This component allows users to input text, choose styles and lengths for a newsletter,
 * and send it via email. It integrates with NLP services for newsletter generation and
 * a mail service for sending emails.
 */
@Component({
  selector: 'app-newsletter-creation',
  templateUrl: './newsletter-creation.component.html',
  styleUrls: ['./newsletter-creation.component.css']
})
export class NewsletterCreationComponent {
  inputText: string = '';
  outputText: string = '';
  subjectText: string = '';
  emailAddress: string = '';


  selectedStyle: string = 'standard';

  chosenLenght: string = 'normal';

  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: 'auto',
    minHeight: '60px',
    translate: 'no',
    toolbarHiddenButtons:[
      [],
      [
        'customClasses',
        'link',
        'unlink',
        'insertVideo',
      ]
    ]
  };


  constructor(
    private nlpService: NlpService,
    private mailService: MailService,
    private contactListService: ContactListService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private textProcessingService: TextProcessingService
  ) {}

  /**
   * Sends data from the input field to the server or handles it as needed.
   * This method is called when the send button is clicked.
   */
  sendData() {
    const dialogRef = this.dialog.open(AppLoadingComponent, {
      width: '250px',
      disableClose: true
    });

    this.nlpService.generateNewsletter(this.selectedStyle, this.chosenLenght, this.inputText).subscribe(
      (response) => {
        // Verarbeitet den Output-Text, um das Subject zu extrahieren
        const processedData = this.textProcessingService.processOutputTextForSubject(response);
        this.subjectText = processedData.subject;
        this.outputText = processedData.updatedOutput;

        dialogRef.close();
      },
      (error) => {
        console.error('There was an error!', error);
        dialogRef.close();
      }
    );
  }

  /**
   * Sends the generated newsletter via email.
   *
   * Uses the mail service to send the newsletter content to the specified email address.
   * Alerts the user upon success or failure of the email sending process.
   */
  sendMail() {
    // TODO: implement -> deleted the Form in the HTML file, because it influenced the editor's input
    this.mailService.sendMailToBackend(this.emailAddress, this.subjectText, this.outputText).subscribe(
      (response) => {
        console.log('Mail sent successfully!', response);
        this.snackBar.open('E-Mail sent successfully!', 'Close', { duration: 3000 });
      },
      (error) => {
        console.error('There was an error sending the mail!', error);
        this.snackBar.open('There was an error sending the mail!', 'Close', { duration: 3000 });
      }
    );
  }

  /**
   * Fetches and displays a list of contact lists.
   *
   * Retrieves contact lists from the contact list service and displays them in a dialog.
   * The selected contact list's details are used to update the email address field.
   */
  getContactLists() {
    this.contactListService.getContactLists().subscribe(contactLists => {
      this.openDialog(contactLists);
    });
  }

  /**
   * Opens a dialog for selecting a contact list.
   *
   * @param contactLists An array of contact lists to choose from.
   */
  openDialog(contactLists: any[]): void {
    const dialogRef = this.dialog.open(ContactListDialogComponent, {
      width: '250px',
      data: { contactLists: contactLists }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.emailAddress = result.content;
      }
    });
  }
}
