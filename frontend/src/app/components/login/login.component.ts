import { Component } from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {MatFormField} from "@angular/material/form-field";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInput} from "@angular/material/input";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";

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

  usernameFormControl = new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(64)]);
  usernameErrorMessage = '';
  passwordFormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  passwordErrorMessage = '';

  constructor(private loginService: LoginService) {
  }

  updateUsernameErrorMessage() {
    if (this.usernameFormControl.hasError('required')) {
      this.usernameErrorMessage = 'You need to enter your username';
    } else if (this.usernameFormControl.hasError('minlength')) {
      this.usernameErrorMessage = 'Username is at least 4 characters long';
    } else if (this.usernameFormControl.hasError('maxlength')) {
      this.usernameErrorMessage = 'Username can\'t be longer than 64 characters';
    } else {
      this.usernameErrorMessage = '';
    }
  }

  updatePasswordErrorMessage() {
    if (this.passwordFormControl.hasError('required')) {
      this.passwordErrorMessage = 'You need to enter your password'
    } else if (this.passwordFormControl.hasError('minlength')) {
      this.passwordErrorMessage = 'Your password must have length 8'
    } else {
      this.passwordErrorMessage = '';
    }
  }

  login(username: string, password: string) {
    this.loginService.login(username, password);
  }
}