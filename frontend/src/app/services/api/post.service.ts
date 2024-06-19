import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CreatePostRequest} from "../../model/post/http/create_post/CreatePostRequest";
import {CreatePostResponse} from "../../model/post/http/create_post/CreatePostResponse";
import {Observable, shareReplay} from "rxjs";
import {environment} from "../../../environments/environment";
import {Post} from "../../model/post/post";
import {GetPostResponse} from "../../model/post/http/get_post/GetPostResponse";
import {UpdatePostRequest} from "../../model/post/http/update_post/UpdatePostRequest";
import {UpdatePostResponse} from "../../model/post/http/update_post/UpdatePostResponse";
import {DeletePostResponse} from "../../model/post/http/delete_post/DeletePostResponse";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  public createPost(createPostRequest: CreatePostRequest): Observable<CreatePostResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<CreatePostResponse>(environment.apiUrl + '/post/', createPostRequest, options)
      .pipe(shareReplay(1));
  }

  public getPost(postId: number): Observable<GetPostResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<GetPostResponse>(environment.apiUrl + `/post/${postId}/`, options)
      .pipe(shareReplay(1));
  }

  public updatePost(updatePostRequest: UpdatePostRequest): Observable<UpdatePostResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    return this.http.put<UpdatePostResponse>(environment.apiUrl + '/post/', updatePostRequest, options)
      .pipe(shareReplay(1));
  }

  public deletePost(postId: number): Observable<DeletePostResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.delete<DeletePostResponse>(environment.apiUrl + `/post/${postId}/`, options)
      .pipe(shareReplay(1));
  }

  public getAllPostsOfBlog(blogId: number): Observable<Post[]> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<Post[]>(environment.apiUrl + `/post/${blogId}/all/`, options)
      .pipe(shareReplay(1));
  }

  public getAllPosts() {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<Post[]>(environment.apiUrl + '/post/all/', options)
      .pipe(shareReplay(1));
  }
}
