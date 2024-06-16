import { Component } from '@angular/core';
import {HeaderComponent} from "../../../components/page-elements/header/header.component";
import {PostDetailsComponent} from "../../../components/post/post-details/post-details.component";
import {CreateCommentComponent} from "../../../components/comments/create-comment/create-comment.component";

@Component({
  selector: 'app-post-details-page',
  standalone: true,
  imports: [
    HeaderComponent,
    PostDetailsComponent,
    CreateCommentComponent
  ],
  templateUrl: './post-details-page.component.html',
  styleUrl: './post-details-page.component.scss'
})
export class PostDetailsPageComponent {
  mode = 'VIEW'

  modeChanged(event: string) {
    this.mode = event;
  }
}
