import {Component} from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule, ValidationErrors, ValidatorFn,
  Validators
} from "@angular/forms";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {RouterLink} from "@angular/router";
import {RegisterService} from "../../services/auth/register.service";
import {catchError} from "rxjs";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    MatButton,
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatError,
    MatFormField,
    MatIcon,
    MatIconButton,
    MatInput,
    MatLabel,
    MatSuffix,
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  hide = true;
  hideConfirm = true;

  passwordMatchValidator(group: AbstractControl): ValidationErrors | null {
    return group.value.password === group.value.passwordConfirm ? null : {mismatch: true};
  }

  registerFormGroup = new FormGroup({
    username: new FormControl<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(64)]),
    mailAddress: new FormControl<string>('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    passwordConfirm: new FormControl('', Validators.required)
  }, {validators: this.passwordMatchValidator});
  usernameErrorMessage = '';
  mailAddressErrorMessage = '';
  passwordErrorMessage = '';
  passwordConfirmErrorMessage = '';

  constructor(private registerService: RegisterService) {

  }

  updateUsernameErrorMessage() {
    if (this.registerFormGroup.controls.username.hasError('required')) {
      this.usernameErrorMessage = 'You need to enter a valid username';
    } else if (this.registerFormGroup.controls.username.hasError('minlength')) {
      this.usernameErrorMessage = 'Your username must have at least 4 characters';
    } else if (this.registerFormGroup.controls.username.hasError('maxlength')) {
      this.usernameErrorMessage = 'Your username can\'t be longer than 64 characters';
    } else {
      this.usernameErrorMessage = '';
    }
  }

  updateMailAddressErrorMessage() {
    if (this.registerFormGroup.controls.mailAddress.hasError('required')) {
      this.mailAddressErrorMessage = 'You need to enter a valid mail address';
    } else if (this.registerFormGroup.controls.mailAddress.hasError('email')) {
      this.mailAddressErrorMessage = 'Your email address is invalid';
    } else {
      this.mailAddressErrorMessage = '';
    }
  }

  updatePasswordErrorMessage() {
    if (this.registerFormGroup.controls.passwordConfirm.hasError('required')) {
      this.passwordConfirmErrorMessage = 'You need to confirm your password';
    } else if (this.registerFormGroup.hasError('mismatch')) {
      this.registerFormGroup.controls.passwordConfirm.setErrors({mismatch: true});
      this.passwordConfirmErrorMessage = 'Passwords do not match';
    } else {
      this.registerFormGroup.controls.passwordConfirm.setErrors(null);
      this.passwordConfirmErrorMessage = '';
    }

    if (this.registerFormGroup.controls.password.hasError('required')) {
      this.passwordErrorMessage = 'You need to enter a password'
    } else if (this.registerFormGroup.controls.password.hasError('minlength')) {
      this.passwordErrorMessage = 'Your password must at least have 8 characters';
    } else {
      this.passwordErrorMessage = '';
    }
  }

  register(fullName: string | null, username: string, mailAddress: string, password: string) {
    this.registerService.register(fullName, username, mailAddress, password)
      .pipe(catchError(this.registerService.handleError))
      .subscribe((data: any) => {
          console.log(data);
          this.registerService.redirectToLoginPage();
        }
      );
  }
}
