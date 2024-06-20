import {Component, OnInit} from '@angular/core';
import {MatError, MatFormField, MatLabel, MatSuffix} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {catchError, throwError} from "rxjs";
import {ChangePasswordComponent} from "../change-password/change-password.component";
import {ProfileInfo} from "../../../model/user/profile-info";
import {AuthService} from "../../../services/auth/auth.service";
import {UserService} from "../../../services/api/user.service";
import {GetUserResponse} from "../../../model/user/http/get_user/GetUserResponse";
import {UpdateUserInfoResponse} from "../../../model/user/http/update_user/UpdateUserInfoResponse";
import {User} from "../../../model/user/user";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SpotifyAuthService} from "../../../services/auth/spotify-auth.service";
import {ActivatedRoute, Router} from "@angular/router";

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
  spotifyAuthorized: boolean = true;

  userInfo: ProfileInfo = {
    fullName: '',
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

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private spotifyAuthService: SpotifyAuthService
  ) {
    this.userService.getCurrentUserInfo()
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe(
        (response: GetUserResponse) => {
          this.setProfileFormGroupAndUserInfo(response.data!);
          this.spotifyAuthorized = response.data!.spotifyAuthorized;
        }
      )
  }

  public updateUserInfo() {
    this.userService.updateUserInfo(
      this.profileFormGroup.controls.fullName.value || '',
      this.profileFormGroup.controls.username.value!,
      this.profileFormGroup.controls.mailAddress.value!
    )
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((response: UpdateUserInfoResponse) => {
        this.setProfileFormGroupAndUserInfo(response.data!.user);
        this.profileFormGroup.controls.fullName.disable();
        this.profileFormGroup.controls.username.disable();
        this.profileFormGroup.controls.mailAddress.disable();
        this.authService.setAccessToken(response.data!.accessToken);
      });
  }

  setProfileFormGroupAndUserInfo(user: User) {
    this.profileFormGroup.controls.fullName.setValue(user.fullName!);
    this.profileFormGroup.controls.username.setValue(user.username);
    this.profileFormGroup.controls.mailAddress.setValue(user.mailAddress);
    this.userInfo.fullName = user.fullName;
    this.userInfo.username = user.username;
    this.userInfo.mailAddress = user.mailAddress;

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

  authorizeSpotifyOrLogin() {
    this.spotifyAuthService.authorizeSpotify()
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((res: { redirectUrl: string }) => {
        document.location.href = res.redirectUrl;
      });
  }

  getSpotifyRefreshToken() {
    return this.spotifyAuthService.getSpotifyRefreshToken();
  }

  getSpotifyAccessToken() {
    return this.spotifyAuthService.getSpotifyAccessToken();
  }

  requestSpotifyAccessTokenWithRefreshToken() {
    this.spotifyAuthService.requestAccessTokenWithRefreshToken()
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((res) => {
        console.log(res.data!);
        this.spotifyAuthService.setSpotifyTokens(res.data!.access_token, res.data!.refresh_token);
      });
  }
}
