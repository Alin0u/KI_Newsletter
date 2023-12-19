import { Component } from '@angular/core';
import { NlpService } from 'src/app/services/nlp.service';
import { MailService } from 'src/app/services/mail/mail.service';
import { AngularEditorConfig } from '@kolkov/angular-editor';


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


  constructor(private nlpService: NlpService, private mailService: MailService) {}

  /**
   * Sends data from the input field to the server or handles it as needed.
   * This method is called when the send button is clicked.
   */
  sendData() {
    // alert('Data has been sent. It will take a moment to generate the newsletter');
    this.nlpService.generateNewsletter(this.selectedStyle, this.chosenLenght, this.inputText).subscribe(
      (response) => {
        this.outputText = response;
      },
      (error) => {
        console.error('There was an error!', error);
      }
    );
  }

  sendMail() {
    // TODO: implement -> deleted the Form in the HTML file, because it influenced the editor's input
    this.mailService.sendMailToBackend(this.emailAddress, this.subjectText, this.outputText).subscribe(
      (response) => {
        console.log('Mail sent successfully!', response); //TODO Add Toast
        window.alert('E-Mail erfolgreich gesendet!');
      },
      (error) => {
        console.error('There was an error sending the mail!', error); //TODO Add Toast
        window.alert('Fehler beim Senden der E-Mail.');
      }
    );
  }
}
