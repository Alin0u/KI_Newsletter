import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from '@angular/router'
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AngularEditorModule } from '@kolkov/angular-editor';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { MenubarComponent } from './components/menubar/menubar.component';
import { FooterComponent } from './components/footer/footer.component';
import { NewsletterCreationComponent } from './components/newsletter-creation/newsletter-creation.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardService } from './services/auth-guard.service';
import { TokenInterceptor } from './services/token.interceptor';
import { ContactlistComponent } from './components/contactlist/contactlist.component';
import {MailService} from "./services/mail/mail.service";
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ContactListDialogComponent } from './components/contact-list-dialog/contact-list-dialog.component';
import {MatListModule} from "@angular/material/list";
import { MatDialogModule } from '@angular/material/dialog';
import { AppLoadingComponent } from './components/app-loading/app-loading.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SettingsComponent } from './components/settings/settings.component';

const appRoute: Routes = [
  {path: 'login', component: LoginComponent, data: { showMenuBar: false } },
  {path: '', component: NewsletterCreationComponent, canActivate: [AuthGuardService]},
  {path: 'contactlist', component: ContactlistComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'settings', component: SettingsComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NewsletterCreationComponent,
    LoginComponent,
    MenubarComponent,
    ContactlistComponent,
    ContactListDialogComponent,
    AppLoadingComponent,
    ContactlistComponent,
    DashboardComponent,
    SettingsComponent
  ],
  imports: [
    ReactiveFormsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AngularEditorModule,
    RouterModule.forRoot(appRoute),
    NoopAnimationsModule,
    MatListModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule
  ],
  exports: [
    RouterModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    MailService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
