import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CreateCommentRequest} from "../../model/comment/http/create_comment/CreateCommentRequest";
import {CreateCommentResponse} from "../../model/comment/http/create_comment/CreateCommentResponse";
import {Observable, shareReplay, Subject} from "rxjs";
import {environment} from "../../../environments/environment";
import {GetCommentResponse} from "../../model/comment/http/get_comment/GetCommentResponse";
import {UpdateCommentRequest} from "../../model/comment/http/update_comment/UpdateCommentRequest";
import {UpdateCommentResponse} from "../../model/comment/http/update_comment/UpdateCommentResponse";
import {DeleteCommentResponse} from "../../model/comment/http/delete_comment/DeleteCommentResponse";
import {Comment} from "../../model/comment/comment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  commentsSubject: Subject<Comment[]> = new Subject<Comment[]>()
  currentPostComments: Comment[] = [];

  constructor(private http: HttpClient) {
  }

  public createComment(createCommentRequest: CreateCommentRequest): Observable<CreateCommentResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<CreateCommentResponse>(environment.apiUrl + '/comment/', createCommentRequest, options)
      .pipe(shareReplay(1));
  }

  public getComment(commentId: number): Observable<GetCommentResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<GetCommentResponse>(environment.apiUrl + `/comment/${commentId}/`, options)
      .pipe(shareReplay(1));
  }

  public updateComment(updateCommentRequest: UpdateCommentRequest): Observable<UpdateCommentResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<UpdateCommentResponse>(environment.apiUrl + '/comment/', updateCommentRequest, options)
      .pipe(shareReplay(1));
  }

  public deleteComment(commentId: number): Observable<DeleteCommentResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.delete<DeleteCommentResponse>(environment.apiUrl + `/comment/${commentId}/`, options)
      .pipe(shareReplay(1));
  }

  public getAllComments(postId: number): Observable<Comment[]> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<Comment[]>(environment.apiUrl + `/comment/${postId}/all/`, options)
      .pipe(shareReplay(1));
  }

  public setCommentList(commentList: Comment[]) {
    this.currentPostComments = commentList;
  }

  public getCommentListObservable() {
    return this.commentsSubject.asObservable();
  }

  public addCommentToList(comment: Comment) {
    this.currentPostComments.push(comment);
    this.commentsSubject.next(this.currentPostComments);
  }

  public removeCommentFromList(comment: Comment) {
    let toDelete = this.currentPostComments.indexOf(comment);
    this.currentPostComments.splice(toDelete, 1);
    this.commentsSubject.next(this.currentPostComments);
  }
}
