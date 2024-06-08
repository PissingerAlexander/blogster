import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {BlogService} from "../../../services/api/blog.service";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {Blog} from "../../../model/blog/blog";

@Component({
  selector: 'app-blog-list',
  standalone: true,
  imports: [],
  templateUrl: './blog-list.component.html',
  styleUrl: './blog-list.component.scss'
})
export class BlogListComponent implements OnInit {
  blogList: Blog[] = [];

  constructor(private route: ActivatedRoute, private blogService: BlogService) {
  }

  ngOnInit() {
    this.route.params.subscribe((parameterList) => {
      this.blogService.getAllBlogs(parameterList['userId'])
        .pipe(catchError((error: HttpErrorResponse) => {
          console.error(error.error);

          return throwError(() => new Error('Something bad happened; please try again'));
        }))
        .subscribe((response: Blog[]) => {
          this.blogList = response;
      })
    })
  }
}
