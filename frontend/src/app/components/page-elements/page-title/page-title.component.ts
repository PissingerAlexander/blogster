import {Component, Input} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-page-title',
  standalone: true,
    imports: [
        MatIcon,
        MatIconButton,
        RouterLink
    ],
  templateUrl: './page-title.component.html',
  styleUrls: ['./page-title.component.scss', '../../../styles/page-title.scss']
})
export class PageTitleComponent {
  @Input() title: string = '';
  @Input() routeLink: string = '';
}
