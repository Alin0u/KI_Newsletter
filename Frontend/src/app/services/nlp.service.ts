import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NlpService {
  private apiUrl = 'http://localhost:8080/api/text-generation/generate';

  constructor(private http: HttpClient) { }

  generateNewsletter(prompt: string): Observable<string> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + window.btoa('user:password')
    });

    const requestBody = {
      prompt: prompt
    };

    return this.http.post(this.apiUrl, requestBody, { headers: headers, responseType: 'text' });
  }
}
