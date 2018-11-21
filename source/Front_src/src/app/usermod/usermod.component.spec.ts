import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsermodComponent } from './usermod.component';

describe('UsermodComponent', () => {
  let component: UsermodComponent;
  let fixture: ComponentFixture<UsermodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsermodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsermodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
