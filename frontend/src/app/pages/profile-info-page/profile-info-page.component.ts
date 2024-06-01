import {Component} from '@angular/core';
import {HeaderComponent} from "../../components/header/header.component";
import {ProfileInfoComponent} from "../../components/profile-info/profile-info.component";
import {ChangePasswordComponent} from "../../components/change-password/change-password.component";

@Component({
  selector: 'app-profile-info-page',
  standalone: true,
  imports: [
    HeaderComponent,
    ProfileInfoComponent,
    ChangePasswordComponent
  ],
  templateUrl: './profile-info-page.component.html',
  styleUrl: './profile-info-page.component.scss'
})
export class ProfileInfoPageComponent {

}
