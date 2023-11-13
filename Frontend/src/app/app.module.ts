import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';


import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { NewsletterCreationComponent } from './components/newsletter-creation/newsletter-creation.component';
import {FormsModule} from "@angular/forms";
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';

const myRoutes: Routes = [
  {path: 'login', component: LoginComponent}, //TODO change component
  {path: 'home', component: NewsletterCreationComponent},
  {path: 'signup', component: SignupComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NewsletterCreationComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    RouterModule.forRoot(myRoutes),
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
