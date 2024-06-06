import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {HeaderComponent} from "../../../components/header/header.component";
import {UserService} from "../../../services/api/user.service";
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";
import {User} from "../../../model/user/user";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable, MatTableDataSource
} from "@angular/material/table";
import {MatIcon} from "@angular/material/icon";
import {catchError} from "rxjs";
import {Router} from "@angular/router";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-user-list-page',
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
  templateUrl: './user-list-page.component.html',
  styleUrl: './user-list-page.component.scss'
})
export class UserListPageComponent {
  userList: User[] = [];
  displayedColumns: string[] = ['id', 'username', 'fullName', 'mailAddress', 'role', 'remove'];


  constructor(private userService: UserService) {
    userService.getAllUsers().subscribe((data: User[]) => {
      this.userList = data;
    })
  }

  getCurrentUsername(): string {
    return this.userService.getCurrentUser().username;
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user)
      .pipe(catchError(this.userService.handleError))
      .subscribe((): void => {
        this.userList.splice(this.userList.indexOf(user), 1);
      });
  }
}
