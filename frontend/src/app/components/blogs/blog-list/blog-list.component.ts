import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BlogService} from "../../../services/api/blog.service";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {Blog} from "../../../model/blog/blog";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";

@Component({
  selector: 'app-blog-list',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatList,
    MatListItem
  ],
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.scss', '../../../styles/list.scss']
})
export class BlogListComponent implements OnInit {
  userId: number | undefined;
  blogList: Blog[] = [];

  dateOptions: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric'
  }

  constructor(private router: Router, private route: ActivatedRoute, private blogService: BlogService) {
  }

  updateBlogList() {
    if (!this.userId) return;
    this.blogService.getAllBlogs(this.userId)
      .pipe(catchError((error: HttpErrorResponse) => {
        console.error(error.error);
        // TODO: display info about error to user directly (on the form?)
        return throwError(() => new Error('Something bad happened; please try again'));
      }))
      .subscribe((response: Blog[]) => {
        response.map((value: Blog) => value.createdAt = new Date(value.createdAt))
        this.blogList = response;
      })
  }

  navigateToBlog(event: any, blogId: number) {
    if (event.target.classList[0] == 'listItemContent' || event.target.classList[0] == 'listItem') {
      this.router.navigate(['/', this.userId, 'blog', blogId]).then();
    }
  }

  ngOnInit() {
    this.route.params.subscribe((parameterList) => {
      this.userId = parameterList['userId'];
    })
    this.updateBlogList();
  }
}
