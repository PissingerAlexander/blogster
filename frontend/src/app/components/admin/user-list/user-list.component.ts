import {Component} from '@angular/core';
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {MatList, MatListItem} from "@angular/material/list";
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
import {MatPaginator} from "@angular/material/paginator";
import {HeaderComponent} from "../../header/header.component";
import {User} from "../../../model/user/user";
import {UserService} from "../../../services/api/user.service";

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
export class UserListComponent {
  userList: User[] = [];

  constructor(private userService: UserService) {
    userService.getAllUsers().subscribe((data: User[]) => {
      this.userList = data;
    })
  }

  getCurrentUserId(): number {
    return this.userService.getCurrentUser().id;
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user)
      .pipe(catchError(this.userService.handleError))
      .subscribe((): void => {
        this.userList.splice(this.userList.indexOf(user), 1);
      });
  }
}
