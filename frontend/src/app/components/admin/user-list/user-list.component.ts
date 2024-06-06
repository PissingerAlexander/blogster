import {Component, Input, OnInit} from '@angular/core';
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
} from "@angular/material/table";
import {MatIcon} from "@angular/material/icon";
import {catchError, Subject} from "rxjs";
import {MatPaginator} from "@angular/material/paginator";
import {HeaderComponent} from "../../header/header.component";
import {User} from "../../../model/user/user";
import {UserService} from "../../../services/api/user.service";
import {AuthService} from "../../../services/auth/auth.service";

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    HeaderComponent,
    MatButton,
    MatIcon,
    MatList,
    MatListItem,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    MatMiniFabButton,
    MatIconButton,
    MatPaginator
  ],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {
  userList: User[] = [];

  @Input() newUserCreatedSubject: Subject<void> | undefined;

  constructor(private authService: AuthService, private userService: UserService) {
    this.updateUserList();
  }

  updateUserList() {
    this.userService.getAllUsers().subscribe((data: User[]) => {
      this.userList = data;
    });
  }

  getCurrentUserId(): number | undefined {
    return this.authService.getId();
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user)
      .pipe(catchError(this.userService.handleError))
      .subscribe((): void => {
        this.userList.splice(this.userList.indexOf(user), 1);
      });
  }

  ngOnInit(): void {
    this.newUserCreatedSubject?.subscribe(() => {
      this.updateUserList();
    });
  }
}
