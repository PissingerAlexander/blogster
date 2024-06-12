import {Component} from '@angular/core';
import {
  FormsModule,
  ReactiveFormsModule,
} from "@angular/forms";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {RouterLink} from "@angular/router";
import {catchError, throwError} from "rxjs";
import {RegisterService} from "../../../services/auth/register.service";
import {UserForm} from "../UserForm";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

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

  constructor(private registerService: RegisterService, public registerForm: UserForm, private snackBar: MatSnackBar) {
  }

  register(fullName: string | null, username: string, mailAddress: string, password: string) {
    this.registerService.register(fullName, username, mailAddress, password)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      })
  )
  .
    subscribe(() => {
        this.registerForm.reset();
        this.registerService.redirectToLoginPage();
      }
    );
  }
}
