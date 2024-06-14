import { Component } from '@angular/core';
import {HeaderComponent} from "../../../components/page-elements/header/header.component";
import {PostDetailsComponent} from "../../../components/post/post-details/post-details.component";

@Component({
  selector: 'app-post-details-page',
  standalone: true,
  imports: [
    HeaderComponent,
    PostDetailsComponent
  ],
  templateUrl: './post-details-page.component.html',
  styleUrl: './post-details-page.component.scss'
})
export class PostDetailsPageComponent {

}
