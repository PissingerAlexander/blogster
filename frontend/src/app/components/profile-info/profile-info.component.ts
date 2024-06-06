import {Component} from '@angular/core';
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {User} from "../../model/user/user";
import {UserService} from "../../services/api/user.service";
import {catchError} from "rxjs";
import {ProfileInfo} from "../../model/user/profile-info";
import {ChangePasswordComponent} from "../change-password/change-password.component";

@Component({
  selector: 'app-profile-info',
  standalone: true,
  imports: [
    MatFormField,
    MatInput,
    MatLabel,
    FormsModule,
    MatIconButton,
    MatSuffix,
    MatIcon,
    MatButton,
    ReactiveFormsModule,
    MatError,
    ChangePasswordComponent
  ],
  templateUrl: './profile-info.component.html',
  styleUrl: './profile-info.component.scss'
})
export class ProfileInfoComponent {

  userInfo: ProfileInfo = {
    username: '',
    mailAddress: ''
  };
  profileFormGroup = new FormGroup({
    fullName: new FormControl<string>({value: '', disabled: true}),
    username: new FormControl<string>({
      value: '',
      disabled: true
    }, [Validators.required, Validators.minLength(3), Validators.maxLength(64)]),
    mailAddress: new FormControl<string>({value: '', disabled: true}, [Validators.required, Validators.email])
  })
  usernameErrorMessage = '';
  mailAddressErrorMessage = '';

  constructor(private userService: UserService) {
    this.userService.getCurrentUserInfo()
      .pipe(catchError(this.userService.handleError))
      .subscribe(
        (data: User) => {
          this.profileFormGroup.controls.fullName.setValue(data.fullName ? data.fullName : null);
          this.userInfo.fullName = data.fullName;
          this.profileFormGroup.controls.username.setValue(data.username);
          this.userInfo.username = data.username;
          this.profileFormGroup.controls.mailAddress.setValue(data.mailAddress);
          this.userInfo.mailAddress = data.mailAddress;
        }
      )
  }

  public update() {
    console.log(this.profileFormGroup.controls.fullName.value);
    this.userService.updateUserInfo(
      this.profileFormGroup.controls.fullName.value,
      this.profileFormGroup.controls.username.value,
      this.profileFormGroup.controls.mailAddress.value
    ).subscribe(() => {
      this.profileFormGroup.controls.fullName.disable();
      this.profileFormGroup.controls.username.disable();
      this.profileFormGroup.controls.mailAddress.disable();
    });
  }

  updateUsernameErrorMessage() {
    if (this.profileFormGroup.controls.username.hasError('required')) {
      this.usernameErrorMessage = 'You need to enter a valid username';
    } else if (this.profileFormGroup.controls.username.hasError('minlength')) {
      this.usernameErrorMessage = 'Your username must have at least 3 characters';
    } else if (this.profileFormGroup.controls.username.hasError('maxlength')) {
      this.usernameErrorMessage = 'Your username can\'t be longer than 64 characters';
    } else {
      this.usernameErrorMessage = '';
    }
  }

  updateMailAddressErrorMessage() {
    if (this.profileFormGroup.controls.mailAddress.hasError('required')) {
      this.mailAddressErrorMessage = 'You need to enter a valid mail address';
    } else if (this.profileFormGroup.controls.mailAddress.hasError('email')) {
      this.mailAddressErrorMessage = 'Your email address is invalid';
    } else {
      this.mailAddressErrorMessage = '';
    }
  }
}
