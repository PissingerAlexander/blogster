import {Component} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {
  MatDialog,
  MatDialogActions, MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {
  AbstractControl,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from "@angular/forms";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {catchError, throwError} from "rxjs";
import {UserService} from "../../../services/api/user.service";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.scss'
})
export class ChangePasswordComponent {

  constructor(public dialog: MatDialog) {  }

  openDialog() {
    this.dialog.open(ChangePasswordDialog)
  }
}

@Component({
  selector: 'change-password-dialog',
  standalone: true,
  imports: [
    MatDialogTitle,
    MatDialogContent,
    MatButton,
    MatDialogActions,
    MatDialogClose,
    FormsModule,
    MatError,
    MatFormField,
    MatIcon,
    MatIconButton,
    MatInput,
    MatLabel,
    MatSuffix,
    ReactiveFormsModule
  ],
  templateUrl: './change-password-dialog/change-password.dialog.html',
  styleUrl: './change-password-dialog/change-password.dialog.scss'
})
export class ChangePasswordDialog {
  hideOld = true;
  hideNew = true;
  hideNewConfirm = true;

  notSamePasswordValidator(group: AbstractControl): ValidationErrors | null {
    return group.value.oldPassword !== group.value.newPassword ? null : {samePassword: true}
  }

  passwordMatchValidator(group: AbstractControl): ValidationErrors | null {
    return group.value.newPassword === group.value.newPasswordConfirm ? null : {mismatch: true};
  }

  changePasswordFormGroup = new FormGroup({
    oldPassword: new FormControl<string>('', Validators.required),
    newPassword: new FormControl<string>('', [Validators.required, Validators.minLength(8)]),
    newPasswordConfirm: new FormControl<string>('', Validators.required)
  }, {validators: [this.passwordMatchValidator, this.notSamePasswordValidator]});
  oldPasswordErrorMessage = '';
  newPasswordErrorMessage = '';
  newPasswordConfirmErrorMessage = '';

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<ChangePasswordDialog>,
    private snackBar: MatSnackBar
  ) {  }

  updateOldPasswordErrorMessage() {
    if (this.changePasswordFormGroup.controls.oldPassword.hasError('required')) {
      this.oldPasswordErrorMessage = 'You need to enter your old password';
    }
  }

  updateNewPasswordErrorMessage() {
    if (this.changePasswordFormGroup.controls.newPasswordConfirm.hasError('required')) {
      this.newPasswordConfirmErrorMessage = 'You need to confirm your password';
    } else if (this.changePasswordFormGroup.hasError('mismatch')) {
      this.changePasswordFormGroup.controls.newPasswordConfirm.setErrors({mismatch: true});
      this.newPasswordConfirmErrorMessage = 'Passwords do not match';
    } else {
      this.changePasswordFormGroup.controls.newPasswordConfirm.setErrors(null);
      this.newPasswordConfirmErrorMessage = '';
    }

    if (this.changePasswordFormGroup.controls.newPassword.hasError('required')) {
      this.newPasswordErrorMessage = 'You need to enter a password'
    } else if (this.changePasswordFormGroup.hasError('samePassword')) {
      this.changePasswordFormGroup.controls.newPassword.setErrors({samePassword: true});
      this.newPasswordErrorMessage = 'Can\'t use current password';
    } else if (this.changePasswordFormGroup.controls.newPassword.hasError('minlength')) {
      this.newPasswordErrorMessage = 'Your password must at least have 8 characters';
    } else {
      this.changePasswordFormGroup.controls.newPassword.setErrors(null);
      this.newPasswordErrorMessage = '';
    }
  }

  // FIXME
  changePassword() {
    if (this.changePasswordFormGroup.controls.oldPassword.value == null || this.changePasswordFormGroup.controls.newPassword.value == null || this.changePasswordFormGroup.invalid)
      console.error('Internal error')
    else
      this.userService.updatePassword(
        this.changePasswordFormGroup.controls.oldPassword.value,
        this.changePasswordFormGroup.controls.newPassword.value
      ).pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
        .subscribe(() => {
          this.dialogRef.close();
        }
      );
  }
}
