import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { NewsletterCreationComponent } from './components/newsletter-creation/newsletter-creation.component';
import { FormsModule } from "@angular/forms";
import { AngularEditorModule } from '@kolkov/angular-editor';
import {MailService} from "./services/mail/mail.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NewsletterCreationComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AngularEditorModule,
  ],
  providers: [MailService],
  bootstrap: [AppComponent]
})
export class AppModule { }
