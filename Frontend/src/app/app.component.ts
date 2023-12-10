import { Component, OnInit } from '@angular/core';
import { AuthService } from "./services/auth.service";
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  showMenuBar = true;

  constructor(public authService:AuthService, private router: Router, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.showMenuBar = this.activatedRoute.firstChild?.snapshot.data['showMenuBar'] !== false;
    });
  }


}
