import {Injectable} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Track} from "../../model/spotify/http/ResponseType/SongListResponseType";

@Injectable({
  providedIn: 'root'
})
export class PostForm {
  private readonly _postForm;
  private _postTitleErrorMessage = '';
  private _contentErrorMessage = '';
  private _songErrorMessage = '';

  constructor() {
    this._postForm = new FormGroup({
      postTitle: new FormControl<string>('', [Validators.required, Validators.minLength(4), Validators.maxLength(64)]),
      content: new FormControl<string>('', [Validators.required]),
      song: new FormControl<string>('', [Validators.required]),
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

  updateSongErrorMessage(validTrack: boolean) {
    if (!validTrack) this._postForm.controls.song.setErrors({'invalidTrack': validTrack});
    if (this._postForm.controls.song.hasError('required')) {
      this._songErrorMessage = 'You need to add a song to your post';
    } else if(!validTrack) {
      this._songErrorMessage = 'Please select a song from the autocomplete';
    } else {
      this._postForm.controls.song.setErrors(null);
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

  get songErrorMessage(): string {
    return this._songErrorMessage;
  }

  set songErrorMessage(value: string) {
    this._songErrorMessage = value;
  }
}
