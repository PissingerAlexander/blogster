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
import {UserForm} from "../Reuseable/Form/UserForm";

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

  constructor(private registerService: RegisterService, public registerForm: UserForm) {  }

  register(fullName: string | null, username: string, mailAddress: string, password: string) {
    this.registerService.register(fullName, username, mailAddress, password)
      .pipe(catchError(this.registerService.handleError))
      .subscribe((data: any) => {
          this.registerService.redirectToLoginPage();
        }
      );
  }
}
