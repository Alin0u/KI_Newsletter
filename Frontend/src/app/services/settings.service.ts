import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserSettings } from '../components/settings/user-settings.model';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private apiBaseUrl = 'http://localhost:8080/api/settings';

  constructor(private http: HttpClient) {}

  submitSettings(settings: UserSettings): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/submit`, settings);
  }
}
