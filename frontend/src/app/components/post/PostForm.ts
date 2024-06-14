import {Injectable} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class PostForm {
  private readonly _postForm;
  private _postTitleErrorMessage = '';
  private _contentErrorMessage = '';

  constructor() {
    this._postForm = new FormGroup({
      postTitle: new FormControl<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(64)]),
      content: new FormControl<string>('', [Validators.required])
    })
  }



  updatePostTitleErrorMessage() {
    if (this._postForm.controls.postTitle.hasError('required')) {
      this._postTitleErrorMessage = 'You need to enter a post title';
    } else if (this._postForm.controls.postTitle.hasError('minlength')) {
      this._postTitleErrorMessage = 'The title of your post must have at least 4 characters';
    } else if (this._postForm.controls.postTitle.hasError('maxlength')) {
      this._postTitleErrorMessage = 'The title of your post can\'t be longer than 64 characters';
    } else {
      this._postTitleErrorMessage = '';
    }
  }

  updateContentErrorMessage() {
    if (this._postForm.controls.content.hasError('required')) {
      this._contentErrorMessage = 'You need to add content to your post';
    } else {
      this._contentErrorMessage = '';
    }
  }


  get postForm() {
    return this._postForm;
  }

  get postTitleErrorMessage(): string {
    return this._postTitleErrorMessage;
  }

  set postTitleErrorMessage(value: string) {
    this._postTitleErrorMessage = value;
  }

  get contentErrorMessage(): string {
    return this._contentErrorMessage;
  }

  set contentErrorMessage(value: string) {
    this._contentErrorMessage = value;
  }
}
