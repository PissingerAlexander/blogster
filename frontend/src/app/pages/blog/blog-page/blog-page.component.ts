import { Component } from '@angular/core';
import {HeaderComponent} from "../../../components/header/header.component";
import {BlogDetailsComponent} from "../../../components/blogs/blog-details/blog-details.component";

@Component({
  selector: 'app-blog-page',
  standalone: true,
  imports: [
    HeaderComponent,
    BlogDetailsComponent
  ],
  templateUrl: './blog-page.component.html',
  styleUrl: './blog-page.component.scss'
})
export class BlogPageComponent {

}
