import { Component } from '@angular/core';

@Component({
  selector: 'app-newsletter-creation',
  templateUrl: './newsletter-creation.component.html',
  styleUrls: ['./newsletter-creation.component.css']
})
export class NewsletterCreationComponent {
  inputText: string = '';

  /**
   * Sends data from the input field to the server or handles it as needed.
   * This method is called when the send button is clicked.
   */
  sendData() {
    // TODO: sending logic
    alert('Data sent: ' + this.inputText);
  }
}
