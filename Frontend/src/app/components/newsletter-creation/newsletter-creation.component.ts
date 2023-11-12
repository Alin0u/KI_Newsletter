import { Component } from '@angular/core';

@Component({
  selector: 'app-newsletter-creation',
  templateUrl: './newsletter-creation.component.html',
  styleUrls: ['./newsletter-creation.component.css']
})
export class NewsletterCreationComponent {
  inputText: string = '';

  sendData() {
    // Holen Sie den Wert aus dem Input-Feld
    const inputFieldValue = (<HTMLInputElement>document.getElementById('inputField')).value;

    // Führen Sie hier die Verarbeitung durch, z. B. Ändern des Output-Felds
    const outputField = document.getElementById('outputField') as HTMLOutputElement;
    outputField.textContent = `${inputFieldValue}`;
  }
}
