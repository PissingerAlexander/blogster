import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, shareReplay} from "rxjs";
import {environment} from "../../../environments/environment";
import {CreateBlogRequest} from "../../model/blog/http/create_blog/CreateBlogRequest";
import {CreateBlogResponse} from "../../model/blog/http/create_blog/CreateBlogResponse";
import {Blog} from "../../model/blog/blog";
import {GetBlogResponse} from "../../model/blog/http/get_blog/GetBlogResponse";
import {UpdateBlogResponse} from "../../model/blog/http/update_blog/UpdateBlogResponse";
import {UpdateBlogRequest} from "../../model/blog/http/update_blog/UpdateBlogRequest";
import {DeleteBlogResponse} from "../../model/blog/http/delete_blog/DeleteBlogResponse";

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
    return this.http.post<CreateBlogResponse>(environment.apiUrl + '/blog/', createBlogRequest, options)
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

  public getBlog(blogId: number): Observable<GetBlogResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json'
      })
    };
    return this.http.get<GetBlogResponse>(environment.apiUrl + `/blog/${blogId}/`, options)
      .pipe(shareReplay(1));
  }

  public updateBlog(blog: Blog): Observable<UpdateBlogResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      })
    };
    let updateBlogRequest: UpdateBlogRequest = {
      id: blog.id,
      blogName: blog.blogName
    };
    return this.http.put<UpdateBlogResponse>(environment.apiUrl + '/blog/', updateBlogRequest, options)
      .pipe(shareReplay(1));
  }

  public deleteBlog(blogId: number): Observable<DeleteBlogResponse> {
    let options = {
      headers: new HttpHeaders({
        'Accept': 'application/json',
      })
    };
    return this.http.delete<DeleteBlogResponse>(environment.apiUrl + `/blog/${blogId}/`, options)
      .pipe(shareReplay(1));
  }
}
