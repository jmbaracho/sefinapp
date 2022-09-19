import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskStatusDetailComponent } from './task-status-detail.component';

describe('TaskStatus Management Detail Component', () => {
  let comp: TaskStatusDetailComponent;
  let fixture: ComponentFixture<TaskStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TaskStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ taskStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TaskStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TaskStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load taskStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.taskStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
