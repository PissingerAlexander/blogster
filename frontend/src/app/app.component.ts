import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {AuthService} from "./services/auth/auth.service";
import {LoginComponent} from "./components/user/login/login.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Blogster';

  constructor() {
    console.log('HEHE');
  }
}
