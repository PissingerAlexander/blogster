import {Component} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {MatFormField} from "@angular/material/form-field";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInput} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {Router, RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {LoginService} from "../../services/auth/login.service";
import {catchError} from "rxjs";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatFormField,
    MatFormFieldModule,
    MatInput,
    MatInputModule,
    MatIcon,
    MatIconButton,
    MatButton,
    RouterLink,
    ReactiveFormsModule,
    FormsModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  hide = true;

  loginFormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(64)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  })
  usernameErrorMessage = '';
  passwordErrorMessage = '';

  constructor(private authService: AuthService, private loginService: LoginService, private route: Router) {
    if (this.authService.isAuthenticated()) this.redirectToIndexPage();
  }

  updateUsernameErrorMessage() {
    if (this.loginFormGroup.controls.username.hasError('required')) {
      this.usernameErrorMessage = 'You need to enter your username';
    } else if (this.loginFormGroup.controls.username.hasError('minlength')) {
      this.usernameErrorMessage = 'Username is at least 4 characters long';
    } else if (this.loginFormGroup.controls.username.hasError('maxlength')) {
      this.usernameErrorMessage = 'Username can\'t be longer than 64 characters';
    } else {
      this.usernameErrorMessage = '';
    }
  }

  updatePasswordErrorMessage() {
    if (this.loginFormGroup.controls.password.hasError('required')) {
      this.passwordErrorMessage = 'You need to enter your password'
    } else if (this.loginFormGroup.controls.password.hasError('minlength')) {
      this.passwordErrorMessage = 'Your password must at least have 8 characters'
    } else {
      this.passwordErrorMessage = '';
    }
  }

  login(username: string, password: string) {
    this.loginService.login(username, password)
      .pipe(catchError(this.loginService.handleError))
      .subscribe(
        (data) => {
          document.cookie = `accessToken=${data.accessToken}; Max-Age=86400; SameSite=None; Secure`;
          this.redirectToIndexPage();
        }
      );
  }

  redirectToIndexPage() {
    this.route.navigate(['/index']).then(() => console.log('You are logged in'));
  }
}
