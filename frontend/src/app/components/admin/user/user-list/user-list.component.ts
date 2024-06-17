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
import {catchError, Subject, throwError} from "rxjs";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {HeaderComponent} from "../../../page-elements/header/header.component";
import {User} from "../../../../model/user/user";
import {UserService} from "../../../../services/api/user.service";
import {AuthService} from "../../../../services/auth/auth.service";
import {Router, RouterLink} from "@angular/router";
import {
  handleErrorAndShowSnackBar
} from "../../../ErrorSnackBar/HandleErrorAndShowSnackBar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {PageTitleComponent} from "../../../page-elements/page-title/page-title.component";

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
    MatPaginator,
    RouterLink,
    PageTitleComponent
  ],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss', '../../../../styles/list.scss']
})
export class UserListComponent implements OnInit {
  length: number = 0;
  pageSize: number = 10;
  pageIndex: number = 0;
  userList: User[] = [];

  @Input() newUserCreatedSubject: Subject<void> | undefined;

  constructor(private authService: AuthService, private userService: UserService, private router: Router, private snackBar: MatSnackBar) {
    this.updateUserList();
  }

  updateUserList() {
    this.userService.getUserPage(this.pageIndex, this.pageSize)
      .pipe()
      .subscribe(res => {
        this.length = res.data!.itemCount;
        this.userList = res.data!.pageContent;
      });
  }

  getUserListPage(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updateUserList();
  }

  getCurrentUserId(): number | undefined {
    return this.authService.getId();
  }

  deleteUser(user: User): void {
    this.userService.deleteUser(user)
      .pipe(catchError((error: HttpErrorResponse) => {
        handleErrorAndShowSnackBar(error.error.error, this.snackBar);
        return throwError(() => new Error('Something bad happened; please try again later'));
      }))
      .subscribe((): void => {
        this.userList.splice(this.userList.indexOf(user), 1);
        this.length--;
        if (this.userList.length === 0 && this.pageIndex > 0) {
          this.pageIndex--;
          this.updateUserList();
        }
      });
  }

  navigateToBlogList(event: any, userId: number) {
    if (event.target.classList[0] == 'listItemContent' || event.target.classList[0] == 'listItem') {
      this.router.navigate(['/', userId, 'blogs']).then();
    }
  }

  ngOnInit(): void {
    this.newUserCreatedSubject?.subscribe(() => {
      this.updateUserList();
    });
  }
}
