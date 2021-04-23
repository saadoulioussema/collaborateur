import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EipsComponent } from './eips.component';

describe('EipsComponent', () => {
  let component: EipsComponent;
  let fixture: ComponentFixture<EipsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EipsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EipsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
