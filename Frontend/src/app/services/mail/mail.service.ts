import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MailService {
// Base URL for the mail API
  private baseUrl = 'http://localhost:8080/api/mail'; //TODO change

  constructor(private http: HttpClient) { }

  /**
   * Creates a MailRequest object with the specified parameters.
   *
   * @param to - The email address of the recipient.
   * @param subject - The subject of the email.
   * @param text - The main content of the email.
   * @returns A MailRequest object representing the email structure.
   */
  createMailRequest(to: string, subject: string, text: string): MailRequest {
    return {
      to: to,
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
    const mailRequest = this.createMailRequest("simbis@mailbox.org", "test", text);//TODO Take Text from Textfield

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + window.btoa('user:password')//TODO Authorization after Login
    });

    return this.http.post(url, mailRequest, { headers });
  }
}

// Interface representing the structure of a mail request
interface MailRequest {
  to: string;
  subject: string;
  text: string;
}
