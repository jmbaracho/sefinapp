import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ScheduleExecutionDetailComponent } from './schedule-execution-detail.component';

describe('ScheduleExecution Management Detail Component', () => {
  let comp: ScheduleExecutionDetailComponent;
  let fixture: ComponentFixture<ScheduleExecutionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ScheduleExecutionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ scheduleExecution: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ScheduleExecutionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ScheduleExecutionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load scheduleExecution on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.scheduleExecution).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
