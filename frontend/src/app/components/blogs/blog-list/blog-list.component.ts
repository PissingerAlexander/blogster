import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {BlogService} from "../../../services/api/blog.service";
import {catchError, throwError} from "rxjs";
import {Blog} from "../../../model/blog/blog";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";
import {handleErrorAndShowSnackBar} from "../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DATE_FORMAT} from "../../../config/date-format";
import {BlogDetailsComponent} from "../blog-details/blog-details.component";
import {PageTitleComponent} from "../../page-elements/page-title/page-title.component";

@Component({
  selector: 'app-blog-list',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatList,
    MatListItem,
    BlogDetailsComponent,
    RouterLink,
    PageTitleComponent
  ],
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.scss', '../../../styles/list.scss']
})
export class BlogListComponent implements OnInit {
  userId: number | undefined;
  blogList: Blog[] = [];

  dateFormat = DATE_FORMAT;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private blogService: BlogService,
    private snackBar: MatSnackBar
  ) {
  }

  updateBlogList() {
    if (!this.userId) return;
    this.blogService.getAllBlogs(this.userId)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
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
    this.activatedRoute.params.subscribe((parameterList) => {
      this.userId = parameterList['userId'];
    })
    this.updateBlogList();
  }
}
