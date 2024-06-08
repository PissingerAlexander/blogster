import {AbstractControl, FormControl, FormGroup, ValidationErrors, Validators} from "@angular/forms";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class UserForm {
  private readonly _userForm;
  private _usernameErrorMessage = '';
  private _mailAddressErrorMessage = '';
  private _passwordErrorMessage = '';
  private _passwordConfirmErrorMessage = '';

  passwordMatchValidator(group: AbstractControl): ValidationErrors | null {
    return group.value.password === group.value.passwordConfirm ? null : {mismatch: true};
  }

  constructor() {
    this._userForm = new FormGroup({
      username: new FormControl<string>('', [Validators.required, Validators.minLength(3), Validators.maxLength(64)]),
      mailAddress: new FormControl<string>('', [Validators.required, Validators.email]),
      password: new FormControl<string>('', [Validators.required, Validators.minLength(8)]),
      passwordConfirm: new FormControl<string>('', Validators.required)
    }, {validators: this.passwordMatchValidator});
  }

  updateUsernameErrorMessage() {
    if (this._userForm.controls.username.hasError('required')) {
      this._usernameErrorMessage = 'You need to enter a valid username';
    } else if (this._userForm.controls.username.hasError('minlength')) {
      this._usernameErrorMessage = 'Your username must have at least 3 characters';
    } else if (this._userForm.controls.username.hasError('maxlength')) {
      this._usernameErrorMessage = 'Your username can\'t be longer than 64 characters';
    } else {
      this._usernameErrorMessage = '';
    }
  }

  updateMailAddressErrorMessage() {
    if (this._userForm.controls.mailAddress.hasError('required')) {
      this._mailAddressErrorMessage = 'You need to enter a valid mail address';
    } else if (this._userForm.controls.mailAddress.hasError('email')) {
      this._mailAddressErrorMessage = 'Your email address is invalid';
    } else {
      this._mailAddressErrorMessage = '';
    }
  }

  updatePasswordErrorMessage() {
    if (this._userForm.controls.passwordConfirm.hasError('required')) {
      this._passwordConfirmErrorMessage = 'You need to confirm your password';
    } else if (this._userForm.hasError('mismatch')) {
      this._userForm.controls.passwordConfirm.setErrors({mismatch: true});
      this._passwordConfirmErrorMessage = 'Passwords do not match';
    } else {
      this._userForm.controls.passwordConfirm.setErrors(null);
      this._passwordConfirmErrorMessage = '';
    }

    if (this._userForm.controls.password.hasError('required')) {
      this._passwordErrorMessage = 'You need to enter a password'
    } else if (this._userForm.controls.password.hasError('minlength')) {
      this._passwordErrorMessage = 'Your password must at least have 8 characters';
    } else {
      this._passwordErrorMessage = '';
    }
  }


  get userForm() {
    return this._userForm;
  }

  get usernameErrorMessage(): string {
    return this._usernameErrorMessage;
  }

  get mailAddressErrorMessage(): string {
    return this._mailAddressErrorMessage;
  }

  get passwordErrorMessage(): string {
    return this._passwordErrorMessage;
  }

  get passwordConfirmErrorMessage(): string {
    return this._passwordConfirmErrorMessage;
  }

  public reset() {
    this._userForm.reset();
  }
}
