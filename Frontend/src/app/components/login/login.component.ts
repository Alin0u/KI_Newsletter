import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  form: FormGroup;

  /**
   * Constructs the LoginComponent instance.
   *
   * @param fb FormBuilder used to create form controls and groups.
   * @param authService Service to handle authentication-related operations.
   * @param router Service to handle navigation.
   */
  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  /**
   * Submits the login form. If successful, navigates to the home page.
   * If there is an error, it is logged to the console.
   */
  submitForm(): void {
    const { username, password } = this.form.value;
    this.authService.login(username, password).subscribe(
      (response) => {
        console.log('Login success:', response);
        this.router.navigate(['/']);
      },
      (error) => {
        console.error('Login failed:', error);
      }
    );
  }
}
