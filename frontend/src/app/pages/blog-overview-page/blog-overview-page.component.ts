import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HeaderComponent} from "../../components/header/header.component";
import {CreateBlogComponent} from "../../components/blogs/create-blog/create-blog.component";
import {BlogListComponent} from "../../components/blogs/blog-list/blog-list.component";

@Component({
  selector: 'app-blog-overview-page',
  standalone: true,
  imports: [
    HeaderComponent,
    CreateBlogComponent,
    BlogListComponent
  ],
  templateUrl: './blog-overview-page.component.html',
  styleUrl: './blog-overview-page.component.scss'
})
export class BlogOverviewPageComponent implements OnInit {
  id: number | null = null;

  constructor(private activeRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activeRoute.params.subscribe((parameterList => {
      this.id = parameterList['userId'];
    }))
  }
}
