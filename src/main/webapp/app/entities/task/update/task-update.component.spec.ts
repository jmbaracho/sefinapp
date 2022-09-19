import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TaskFormService } from './task-form.service';
import { TaskService } from '../service/task.service';
import { ITask } from '../task.model';
import { ITaskStatus } from 'app/entities/task-status/task-status.model';
import { TaskStatusService } from 'app/entities/task-status/service/task-status.service';
import { IScheduleExecution } from 'app/entities/schedule-execution/schedule-execution.model';
import { ScheduleExecutionService } from 'app/entities/schedule-execution/service/schedule-execution.service';

import { TaskUpdateComponent } from './task-update.component';

describe('Task Management Update Component', () => {
  let comp: TaskUpdateComponent;
  let fixture: ComponentFixture<TaskUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taskFormService: TaskFormService;
  let taskService: TaskService;
  let taskStatusService: TaskStatusService;
  let scheduleExecutionService: ScheduleExecutionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TaskUpdateComponent],
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
      .overrideTemplate(TaskUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaskUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taskFormService = TestBed.inject(TaskFormService);
    taskService = TestBed.inject(TaskService);
    taskStatusService = TestBed.inject(TaskStatusService);
    scheduleExecutionService = TestBed.inject(ScheduleExecutionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TaskStatus query and add missing value', () => {
      const task: ITask = { id: 456 };
      const status: ITaskStatus = { id: 94263 };
      task.status = status;

      const taskStatusCollection: ITaskStatus[] = [{ id: 73486 }];
      jest.spyOn(taskStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: taskStatusCollection })));
      const additionalTaskStatuses = [status];
      const expectedCollection: ITaskStatus[] = [...additionalTaskStatuses, ...taskStatusCollection];
      jest.spyOn(taskStatusService, 'addTaskStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ task });
      comp.ngOnInit();

      expect(taskStatusService.query).toHaveBeenCalled();
      expect(taskStatusService.addTaskStatusToCollectionIfMissing).toHaveBeenCalledWith(
        taskStatusCollection,
        ...additionalTaskStatuses.map(expect.objectContaining)
      );
      expect(comp.taskStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ScheduleExecution query and add missing value', () => {
      const task: ITask = { id: 456 };
      const scheduleExecution: IScheduleExecution = { id: 95374 };
      task.scheduleExecution = scheduleExecution;

      const scheduleExecutionCollection: IScheduleExecution[] = [{ id: 80717 }];
      jest.spyOn(scheduleExecutionService, 'query').mockReturnValue(of(new HttpResponse({ body: scheduleExecutionCollection })));
      const additionalScheduleExecutions = [scheduleExecution];
      const expectedCollection: IScheduleExecution[] = [...additionalScheduleExecutions, ...scheduleExecutionCollection];
      jest.spyOn(scheduleExecutionService, 'addScheduleExecutionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ task });
      comp.ngOnInit();

      expect(scheduleExecutionService.query).toHaveBeenCalled();
      expect(scheduleExecutionService.addScheduleExecutionToCollectionIfMissing).toHaveBeenCalledWith(
        scheduleExecutionCollection,
        ...additionalScheduleExecutions.map(expect.objectContaining)
      );
      expect(comp.scheduleExecutionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const task: ITask = { id: 456 };
      const status: ITaskStatus = { id: 42186 };
      task.status = status;
      const scheduleExecution: IScheduleExecution = { id: 49294 };
      task.scheduleExecution = scheduleExecution;

      activatedRoute.data = of({ task });
      comp.ngOnInit();

      expect(comp.taskStatusesSharedCollection).toContain(status);
      expect(comp.scheduleExecutionsSharedCollection).toContain(scheduleExecution);
      expect(comp.task).toEqual(task);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITask>>();
      const task = { id: 123 };
      jest.spyOn(taskFormService, 'getTask').mockReturnValue(task);
      jest.spyOn(taskService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ task });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: task }));
      saveSubject.complete();

      // THEN
      expect(taskFormService.getTask).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taskService.update).toHaveBeenCalledWith(expect.objectContaining(task));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITask>>();
      const task = { id: 123 };
      jest.spyOn(taskFormService, 'getTask').mockReturnValue({ id: null });
      jest.spyOn(taskService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ task: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: task }));
      saveSubject.complete();

      // THEN
      expect(taskFormService.getTask).toHaveBeenCalled();
      expect(taskService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITask>>();
      const task = { id: 123 };
      jest.spyOn(taskService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ task });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taskService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTaskStatus', () => {
      it('Should forward to taskStatusService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(taskStatusService, 'compareTaskStatus');
        comp.compareTaskStatus(entity, entity2);
        expect(taskStatusService.compareTaskStatus).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareScheduleExecution', () => {
      it('Should forward to scheduleExecutionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(scheduleExecutionService, 'compareScheduleExecution');
        comp.compareScheduleExecution(entity, entity2);
        expect(scheduleExecutionService.compareScheduleExecution).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
