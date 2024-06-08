import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, shareReplay} from "rxjs";
import {environment} from "../../../environments/environment";
import {CreateBlogRequest} from "../../model/blog/http/create_blog/CreateBlogRequest";
import {CreateBlogResponse} from "../../model/blog/http/create_blog/CreateBlogResponse";
import {Blog} from "../../model/blog/blog";

@Injectable({
  providedIn: 'root'
})
export class BlogService {

  constructor(private http: HttpClient) {
  }

  public createBlog(createBlogRequest: CreateBlogRequest): Observable<CreateBlogResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<CreateBlogResponse>(environment.apiUrl + "/blog/", createBlogRequest, options)
      .pipe(shareReplay(1));
  }

  public getAllBlogs(userId: number): Observable<Blog[]> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
      })
    };
    return this.http.get<Blog[]>(environment.apiUrl + `/blog/${userId}/all/`, options)
      .pipe(shareReplay(1));
  }
}
