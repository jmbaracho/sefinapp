import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ScheduleExecutionFormService } from './schedule-execution-form.service';
import { ScheduleExecutionService } from '../service/schedule-execution.service';
import { IScheduleExecution } from '../schedule-execution.model';
import { ISchedule } from 'app/entities/schedule/schedule.model';
import { ScheduleService } from 'app/entities/schedule/service/schedule.service';

import { ScheduleExecutionUpdateComponent } from './schedule-execution-update.component';

describe('ScheduleExecution Management Update Component', () => {
  let comp: ScheduleExecutionUpdateComponent;
  let fixture: ComponentFixture<ScheduleExecutionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let scheduleExecutionFormService: ScheduleExecutionFormService;
  let scheduleExecutionService: ScheduleExecutionService;
  let scheduleService: ScheduleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ScheduleExecutionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ScheduleExecutionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ScheduleExecutionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    scheduleExecutionFormService = TestBed.inject(ScheduleExecutionFormService);
    scheduleExecutionService = TestBed.inject(ScheduleExecutionService);
    scheduleService = TestBed.inject(ScheduleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Schedule query and add missing value', () => {
      const scheduleExecution: IScheduleExecution = { id: 456 };
      const schedule: ISchedule = { id: 21629 };
      scheduleExecution.schedule = schedule;

      const scheduleCollection: ISchedule[] = [{ id: 32197 }];
      jest.spyOn(scheduleService, 'query').mockReturnValue(of(new HttpResponse({ body: scheduleCollection })));
      const additionalSchedules = [schedule];
      const expectedCollection: ISchedule[] = [...additionalSchedules, ...scheduleCollection];
      jest.spyOn(scheduleService, 'addScheduleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ scheduleExecution });
      comp.ngOnInit();

      expect(scheduleService.query).toHaveBeenCalled();
      expect(scheduleService.addScheduleToCollectionIfMissing).toHaveBeenCalledWith(
        scheduleCollection,
        ...additionalSchedules.map(expect.objectContaining)
      );
      expect(comp.schedulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const scheduleExecution: IScheduleExecution = { id: 456 };
      const schedule: ISchedule = { id: 82554 };
      scheduleExecution.schedule = schedule;

      activatedRoute.data = of({ scheduleExecution });
      comp.ngOnInit();

      expect(comp.schedulesSharedCollection).toContain(schedule);
      expect(comp.scheduleExecution).toEqual(scheduleExecution);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IScheduleExecution>>();
      const scheduleExecution = { id: 123 };
      jest.spyOn(scheduleExecutionFormService, 'getScheduleExecution').mockReturnValue(scheduleExecution);
      jest.spyOn(scheduleExecutionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scheduleExecution });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scheduleExecution }));
      saveSubject.complete();

      // THEN
      expect(scheduleExecutionFormService.getScheduleExecution).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(scheduleExecutionService.update).toHaveBeenCalledWith(expect.objectContaining(scheduleExecution));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IScheduleExecution>>();
      const scheduleExecution = { id: 123 };
      jest.spyOn(scheduleExecutionFormService, 'getScheduleExecution').mockReturnValue({ id: null });
      jest.spyOn(scheduleExecutionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scheduleExecution: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scheduleExecution }));
      saveSubject.complete();

      // THEN
      expect(scheduleExecutionFormService.getScheduleExecution).toHaveBeenCalled();
      expect(scheduleExecutionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IScheduleExecution>>();
      const scheduleExecution = { id: 123 };
      jest.spyOn(scheduleExecutionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scheduleExecution });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(scheduleExecutionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSchedule', () => {
      it('Should forward to scheduleService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(scheduleService, 'compareSchedule');
        comp.compareSchedule(entity, entity2);
        expect(scheduleService.compareSchedule).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
