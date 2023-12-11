import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MailService {
// Base URL for the mail API
  private baseUrl = 'http://localhost:8080/api/mail'; //TODO change URL

  // Liste mit Test-E-Mail-Adressen
  private testEmailAdresses: string[] = [ //TODO delete at the end
    "test@mail.com"
  ];


  constructor(private http: HttpClient) { }

  /**
   * Creates a MailRequest object with the specified parameters.
   *
   * @param tos    The email addresses of the recipients.
   * @param subject - The subject of the email.
   * @param text - The main content of the email.
   * @returns A MailRequest object representing the email structure.
   */
  createMailRequest(tos: string[], subject: string, text: string): MailRequest {
    return {
      tos: tos,
      subject: subject,
      text: text
    };
  }

  /**
   * Sends an email using the provided text.
   *
   * @param text - The main content of the email.
   * @returns An Observable with the result of the HTTP request.
   */
  sendMailToBackend(text: string): Observable<any>{
    const url = `${this.baseUrl}/send`;
    const mailRequest = this.createMailRequest(this.testEmailAdresses, "test", text);//TODO add MailAdressse from ContactList

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + window.btoa('user:password')
    });

    return this.http.post(url, { tos: mailRequest.tos, subject: "Newsletter", text: mailRequest.text }, { headers });
  }
}

// Interface representing the structure of a mail request
interface MailRequest {
  tos: string[];
  subject: string;
  text: string;
}
