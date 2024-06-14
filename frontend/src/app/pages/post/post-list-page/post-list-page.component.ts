import { Component } from '@angular/core';
import {HeaderComponent} from "../../../components/header/header.component";
import {PostListComponent} from "../../../components/post/post-list/post-list.component";

@Component({
  selector: 'app-post-list-page',
  standalone: true,
  imports: [
    HeaderComponent,
    PostListComponent
  ],
  templateUrl: './post-list-page.component.html',
  styleUrl: './post-list-page.component.scss'
})
export class PostListPageComponent {

}
