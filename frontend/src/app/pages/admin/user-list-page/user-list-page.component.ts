import {Component} from '@angular/core';
import {HeaderComponent} from "../../../components/header/header.component";

@Component({
  selector: 'app-user-list-page',
  standalone: true,
  imports: [
    HeaderComponent
  ],
  templateUrl: './user-list-page.component.html',
  styleUrl: './user-list-page.component.scss'
})
export class UserListPageComponent {

}
