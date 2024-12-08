import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LauyoutComponent } from './lauyout.component';

describe('LauyoutComponent', () => {
  let component: LauyoutComponent;
  let fixture: ComponentFixture<LauyoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LauyoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LauyoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
