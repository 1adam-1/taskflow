import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MembersAdd } from './members-add';

describe('MembersAdd', () => {
  let component: MembersAdd;
  let fixture: ComponentFixture<MembersAdd>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MembersAdd],
    }).compileComponents();

    fixture = TestBed.createComponent(MembersAdd);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
