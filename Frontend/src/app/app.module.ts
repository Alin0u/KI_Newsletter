import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { NewsletterCreationComponent } from './components/newsletter-creation/newsletter-creation.component';
import { FormsModule } from "@angular/forms";
import { AngularEditorModule } from '@kolkov/angular-editor';
import {RouterModule, Routes} from '@angular/router'
import {LoginComponent} from "./components/login/login.component";
import { ReactiveFormsModule } from '@angular/forms';

const appRoute: Routes = [
  {path: '', component: LoginComponent},
  {path: 'home', component: NewsletterCreationComponent}

]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NewsletterCreationComponent,
    LoginComponent
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AngularEditorModule,
    RouterModule.forRoot(appRoute)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
