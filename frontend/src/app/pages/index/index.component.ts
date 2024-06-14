import { Component } from '@angular/core';
import {HeaderComponent} from "../../components/page-elements/header/header.component";

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [
    HeaderComponent
  ],
  templateUrl: './index.component.html',
  styleUrl: './index.component.scss'
})
export class IndexComponent {

}
