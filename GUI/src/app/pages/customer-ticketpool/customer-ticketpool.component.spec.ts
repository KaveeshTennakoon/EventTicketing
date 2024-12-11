import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerTicketpoolComponent } from './customer-ticketpool.component';

describe('CustomerTicketpoolComponent', () => {
  let component: CustomerTicketpoolComponent;
  let fixture: ComponentFixture<CustomerTicketpoolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerTicketpoolComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerTicketpoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
