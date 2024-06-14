import {Routes} from '@angular/router';
import {IndexComponent} from "./pages/index/index.component";
import {LoginPageComponent} from "./pages/user/login-page/login-page.component";
import {RegisterPageComponent} from "./pages/user/register-page/register-page.component";
import {ProfileInfoPageComponent} from "./pages/user/profile-info-page/profile-info-page.component";
import {adminGuard} from "./guards/auth/admin.guard";
import {UserListPageComponent} from "./pages/admin/user-list-page/user-list-page.component";
import {authGuard} from "./guards/auth/auth.guard";
import {BlogListPageComponent} from "./pages/blog/blog-list-page/blog-list-page.component";
import {BlogPageComponent} from "./pages/blog/blog-page/blog-page.component";
import {PostDetailsPageComponent} from "./pages/post/post-details-page/post-details-page.component";


export const routes: Routes = [
  {path: 'admin/users', component: UserListPageComponent, canActivate: [adminGuard]},
  {path: 'register', component: RegisterPageComponent},
  {path: 'login', component: LoginPageComponent},
  {path: 'index', component: IndexComponent},
  {path: 'profile', component: ProfileInfoPageComponent, canActivate: [authGuard]},
  {path: ':userId/blogs', component: BlogListPageComponent, canActivate: [authGuard]},
  {path: ':userId/blog/:blogId', component: BlogPageComponent, canActivate: [authGuard]},
  {path: ':userId/blog/:blogId/post/:postId', component: PostDetailsPageComponent, canActivate: [authGuard]},
  {path: '**', component: IndexComponent}
];
