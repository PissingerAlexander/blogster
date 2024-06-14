import { Component } from '@angular/core';
import {HeaderComponent} from "../../../components/page-elements/header/header.component";
import {BlogDetailsComponent} from "../../../components/blogs/blog-details/blog-details.component";
import {PostListComponent} from "../../../components/post/post-list/post-list.component";
import {CreatePostComponent} from "../../../components/post/create-post/create-post.component";

@Component({
  selector: 'app-blog-page',
  standalone: true,
  imports: [
    HeaderComponent,
    BlogDetailsComponent,
    PostListComponent,
    CreatePostComponent
  ],
  templateUrl: './blog-page.component.html',
  styleUrl: './blog-page.component.scss'
})
export class BlogPageComponent {

}
