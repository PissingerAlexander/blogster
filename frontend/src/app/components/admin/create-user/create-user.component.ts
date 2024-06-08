import {Component, EventEmitter, Output} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {
  FormsModule,
  ReactiveFormsModule,
} from "@angular/forms";
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {UserForm} from "../../Reuseable/Form/UserForm";
import {MatOption, MatSelect} from "@angular/material/select";
import {UserService} from "../../../services/api/user.service";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-create-user',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.scss'
})
export class CreateUserComponent {

  @Output() createUserEvent = new EventEmitter<void>();

  constructor(public dialog: MatDialog) {
  }

  openDialog() {
    const dialogRef = this.dialog.open(CreateUserDialog)
    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) this.createUserEvent.emit();
    })
  }
}

@Component({
  selector: 'create-user-dialog',
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
    ReactiveFormsModule,
    MatSelect,
    MatOption
  ],
  templateUrl: './create-user-dialog/create-user.dialog.html',
  styleUrl: './create-user-dialog/create-user.dialog.scss'
})
export class CreateUserDialog {
  hide = true;
  hideConfirm = true;
  role: string = 'ROLE_USER';

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<CreateUserDialog>,
    public createUserForm: UserForm
  ) {
  }

  createUser(fullName: string | null) {
    if (fullName == '') fullName = null;
    this.userService.createUser(
      fullName,
      this.createUserForm.userForm.controls.username.value!,
      this.role,
      this.createUserForm.userForm.controls.mailAddress.value!,
      this.createUserForm.userForm.controls.password.value!
    )
      .pipe(catchError((error: HttpErrorResponse) => {
        // TODO: display info about error to user directly (on the form?)
        console.error(error.error);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe(() => {
        this.dialogRef.close(true)
        this.createUserForm.reset();
      });
  }
}
