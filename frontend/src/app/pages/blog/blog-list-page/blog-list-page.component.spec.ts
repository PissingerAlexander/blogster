import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlogListPageComponent } from './blog-list-page.component';

describe('BlogOverviewPageComponent', () => {
  let component: BlogListPageComponent;
  let fixture: ComponentFixture<BlogListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BlogListPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BlogListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
