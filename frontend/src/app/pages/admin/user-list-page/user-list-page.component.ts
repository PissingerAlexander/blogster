import {Component} from '@angular/core';
import {HeaderComponent} from "../../../components/page-elements/header/header.component";
import {UserListComponent} from "../../../components/admin/user/user-list/user-list.component";
import {CreateUserComponent} from "../../../components/admin/user/create-user/create-user.component";
import {Subject} from "rxjs";

@Component({
  selector: 'app-user-list-page',
  standalone: true,
  imports: [
    HeaderComponent,
    UserListComponent,
    CreateUserComponent
  ],
  templateUrl: './user-list-page.component.html',
  styleUrl: './user-list-page.component.scss'
})
export class UserListPageComponent {
  newUserCreatedSubject: Subject<void> = new Subject<void>();

  notifyUserList() {
    this.newUserCreatedSubject.next();
  }
}
