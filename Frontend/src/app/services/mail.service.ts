import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MailService {
  private baseUrl = 'http://localhost:8080/api/mail'; //TODO change URL

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

  sendMailToBackend(emailAddresses: string, subject: string, text: string): Observable<any>{
    const url = `${this.baseUrl}/send`;
    const emailAddressesList = this.getEmailAddressesFromString(emailAddresses); // Convert the string to an array

    const mailRequest = this.createMailRequest(emailAddressesList, subject, text);//TODO add MailAdressse from ContactList

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + window.btoa('user:password')
    });

    return this.http.post(url, { tos: mailRequest.tos, subject: mailRequest.subject, text: mailRequest.text }, { headers });
  }

  getEmailAddressesFromString(emailAddresses: string): string[] {
    const emailAddressesArray = emailAddresses.split(',').map(email => email.trim());
    return emailAddressesArray.filter(email => email.length > 0);
  }
}

interface MailRequest {
  tos: string[];
  subject: string;
  text: string;
}
