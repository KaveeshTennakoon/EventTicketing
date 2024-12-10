import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorConfigureComponent } from './vendor-configure.component';

describe('VendorConfigureComponent', () => {
  let component: VendorConfigureComponent;
  let fixture: ComponentFixture<VendorConfigureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VendorConfigureComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorConfigureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
