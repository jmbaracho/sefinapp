import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TaskStatusFormService } from './task-status-form.service';
import { TaskStatusService } from '../service/task-status.service';
import { ITaskStatus } from '../task-status.model';

import { TaskStatusUpdateComponent } from './task-status-update.component';

describe('TaskStatus Management Update Component', () => {
  let comp: TaskStatusUpdateComponent;
  let fixture: ComponentFixture<TaskStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let taskStatusFormService: TaskStatusFormService;
  let taskStatusService: TaskStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TaskStatusUpdateComponent],
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
      .overrideTemplate(TaskStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TaskStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    taskStatusFormService = TestBed.inject(TaskStatusFormService);
    taskStatusService = TestBed.inject(TaskStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const taskStatus: ITaskStatus = { id: 456 };

      activatedRoute.data = of({ taskStatus });
      comp.ngOnInit();

      expect(comp.taskStatus).toEqual(taskStatus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaskStatus>>();
      const taskStatus = { id: 123 };
      jest.spyOn(taskStatusFormService, 'getTaskStatus').mockReturnValue(taskStatus);
      jest.spyOn(taskStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taskStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taskStatus }));
      saveSubject.complete();

      // THEN
      expect(taskStatusFormService.getTaskStatus).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(taskStatusService.update).toHaveBeenCalledWith(expect.objectContaining(taskStatus));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaskStatus>>();
      const taskStatus = { id: 123 };
      jest.spyOn(taskStatusFormService, 'getTaskStatus').mockReturnValue({ id: null });
      jest.spyOn(taskStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taskStatus: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: taskStatus }));
      saveSubject.complete();

      // THEN
      expect(taskStatusFormService.getTaskStatus).toHaveBeenCalled();
      expect(taskStatusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITaskStatus>>();
      const taskStatus = { id: 123 };
      jest.spyOn(taskStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ taskStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(taskStatusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
