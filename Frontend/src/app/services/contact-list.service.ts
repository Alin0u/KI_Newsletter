import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

/**
 * Service to manage contact lists.
 */
@Injectable({
  providedIn: 'root'
})
export class ContactListService {
  private apiUrl = 'http://localhost:8080/api/list'; // TODO URL Backend

  constructor(private http: HttpClient) {}

  /**
   * Retrieves all contact lists for the current user.
   *
   * @return An Observable of the contact lists data.
   */
  getContactLists(): Observable<any> {
    return this.http.get(`${this.apiUrl}`);
  }

  /**
   * Saves or updates a contact list.
   *
   * @param listName The name of the contact list to be saved or updated.
   * @param contactList The contact list data.
   * @return An Observable of the response from the backend.
   */
  saveContactList(listName: string, contactList: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${listName}`, contactList);
  }

  /**
   * Deletes a contact list.
   *
   * @param listName The name of the contact list to be deleted.
   * @return An Observable of the response from the backend.
   */
  deleteContactList(listName: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${listName}`);
  }

  /**
   * Deletes a contact list.
   *
   * @param listName The name of the contact list to be deleted.
   * @return An Observable of the response from the backend.
   */
  createContactList(listName: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/${listName}`, {});
  }

}
