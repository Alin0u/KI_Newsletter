import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  showLogoutAndProfile: boolean = true;

  /**
   * Constructs the HeaderComponent instance.
   *
   * @param authService Service to handle authentication related operations.
   * @param router Service to handle navigation.
   */
  constructor(private authService: AuthService, private router: Router) {}

  /**
   * OnInit lifecycle hook to set up initial state of the component.
   * Subscribes to router events to toggle the visibility of logout and profile options.
   */
  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (this.router.url === '/login') {
        this.showLogoutAndProfile = false;
      } else {
        this.showLogoutAndProfile = true;
      }
    });
  }

  /**
   * Logs the user out of the application.
   * Redirects the user to the login page after successful logout.
   */
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  /**
   * Navigates to the settings page.
   */
  settings() {
    this.router.navigate(['/settings']);
  }
}
