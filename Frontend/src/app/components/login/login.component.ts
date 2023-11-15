import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
    form: FormGroup;

    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router  ) {
        this.form = this.fb.group({
            username: ['', Validators.required],
            password: ['', Validators.required],
        });
    }

    // Method to handle form submission
    submitForm(): void {
        const { username, password } = this.form.value;
        this.authService.login(username, password).subscribe(
            (response) => {
                console.log('Login success:', response);
                alert("Success");
                this.router.navigate(['']); //TODO routing to a new page
            },
            (error) => {
                console.error('Login failed:', error);
                alert("Failed")
            }
        );
    }
}
