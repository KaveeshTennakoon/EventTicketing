import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorTicketpoolComponent } from './vendor-ticketpool.component';

describe('VendorTicketpoolComponent', () => {
  let component: VendorTicketpoolComponent;
  let fixture: ComponentFixture<VendorTicketpoolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VendorTicketpoolComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorTicketpoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
