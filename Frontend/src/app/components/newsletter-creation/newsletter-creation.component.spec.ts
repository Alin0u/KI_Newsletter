import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NewsletterCreationComponent } from './newsletter-creation.component';


describe('NewsletterCreationComponent', () => {
  let component: NewsletterCreationComponent;
  let fixture: ComponentFixture<NewsletterCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsletterCreationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsletterCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
