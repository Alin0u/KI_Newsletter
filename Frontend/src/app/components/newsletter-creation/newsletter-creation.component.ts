import { Component } from '@angular/core';
import { NlpService } from 'src/app/services/nlp.service';

@Component({
  selector: 'app-newsletter-creation',
  templateUrl: './newsletter-creation.component.html',
  styleUrls: ['./newsletter-creation.component.css']
})
export class NewsletterCreationComponent {
  inputText: string = '';
  outputText: string = '';

  constructor(private nlpService: NlpService) {}

  /**
   * Sends data from the input field to the server or handles it as needed.
   * This method is called when the send button is clicked.
   */
  sendData() {
    // alert('Data has been sent. It will take a moment to generate the newsletter');
    this.nlpService.generateNewsletter(this.inputText).subscribe(
      (response) => {
        this.outputText = response;
      },
      (error) => {
        console.error('There was an error!', error);
      }
    );
  }

  sendMail() {
    // TODO: implement
  }
}
