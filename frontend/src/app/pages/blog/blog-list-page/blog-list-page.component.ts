import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HeaderComponent} from "../../../components/header/header.component";
import {CreateBlogComponent} from "../../../components/blogs/create-blog/create-blog.component";
import {BlogListComponent} from "../../../components/blogs/blog-list/blog-list.component";

@Component({
  selector: 'app-blog-list-page',
  standalone: true,
  imports: [
    HeaderComponent,
    CreateBlogComponent,
    BlogListComponent
  ],
  templateUrl: './blog-list-page.component.html',
  styleUrl: './blog-list-page.component.scss'
})
export class BlogListPageComponent implements OnInit {
  id: number | null = null;

  constructor(private activeRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activeRoute.params.subscribe((parameterList => {
      this.id = parameterList['userId'];
    }))
  }
}
