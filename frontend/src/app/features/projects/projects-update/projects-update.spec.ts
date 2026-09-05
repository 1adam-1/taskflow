import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectsUpdate } from './projects-update';

describe('ProjectsUpdate', () => {
  let component: ProjectsUpdate;
  let fixture: ComponentFixture<ProjectsUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectsUpdate],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectsUpdate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
