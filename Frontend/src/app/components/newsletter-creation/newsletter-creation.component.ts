import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-newsletter-creation',
  templateUrl: './newsletter-creation.component.html',
  styleUrls: ['./newsletter-creation.component.css']
})
export class NewsletterCreationComponent {
  constructor(private router:Router) { }

  inputText: string = '';

  /**
   * Sends data from the input field to the server or handles it as needed.
   * This method is called when the send button is clicked.
   */
  sendData() {
    // TODO: sending logic
    alert('Data sent: ' + this.inputText);
  }

  /**
   * Navigates between the pages.
   */
  navigateToPage(route: string): void {
    this.router.navigate([route]).then(r => false);
  }
}
