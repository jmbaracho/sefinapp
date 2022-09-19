import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RawDataFormService } from './raw-data-form.service';
import { RawDataService } from '../service/raw-data.service';
import { IRawData } from '../raw-data.model';
import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';

import { RawDataUpdateComponent } from './raw-data-update.component';

describe('RawData Management Update Component', () => {
  let comp: RawDataUpdateComponent;
  let fixture: ComponentFixture<RawDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rawDataFormService: RawDataFormService;
  let rawDataService: RawDataService;
  let taskService: TaskService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RawDataUpdateComponent],
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
      .overrideTemplate(RawDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RawDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rawDataFormService = TestBed.inject(RawDataFormService);
    rawDataService = TestBed.inject(RawDataService);
    taskService = TestBed.inject(TaskService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Task query and add missing value', () => {
      const rawData: IRawData = { id: 456 };
      const task: ITask = { id: 42939 };
      rawData.task = task;

      const taskCollection: ITask[] = [{ id: 84205 }];
      jest.spyOn(taskService, 'query').mockReturnValue(of(new HttpResponse({ body: taskCollection })));
      const additionalTasks = [task];
      const expectedCollection: ITask[] = [...additionalTasks, ...taskCollection];
      jest.spyOn(taskService, 'addTaskToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rawData });
      comp.ngOnInit();

      expect(taskService.query).toHaveBeenCalled();
      expect(taskService.addTaskToCollectionIfMissing).toHaveBeenCalledWith(
        taskCollection,
        ...additionalTasks.map(expect.objectContaining)
      );
      expect(comp.tasksSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const rawData: IRawData = { id: 456 };
      const task: ITask = { id: 71331 };
      rawData.task = task;

      activatedRoute.data = of({ rawData });
      comp.ngOnInit();

      expect(comp.tasksSharedCollection).toContain(task);
      expect(comp.rawData).toEqual(rawData);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRawData>>();
      const rawData = { id: 123 };
      jest.spyOn(rawDataFormService, 'getRawData').mockReturnValue(rawData);
      jest.spyOn(rawDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawData }));
      saveSubject.complete();

      // THEN
      expect(rawDataFormService.getRawData).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(rawDataService.update).toHaveBeenCalledWith(expect.objectContaining(rawData));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRawData>>();
      const rawData = { id: 123 };
      jest.spyOn(rawDataFormService, 'getRawData').mockReturnValue({ id: null });
      jest.spyOn(rawDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawData: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawData }));
      saveSubject.complete();

      // THEN
      expect(rawDataFormService.getRawData).toHaveBeenCalled();
      expect(rawDataService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRawData>>();
      const rawData = { id: 123 };
      jest.spyOn(rawDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rawDataService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTask', () => {
      it('Should forward to taskService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(taskService, 'compareTask');
        comp.compareTask(entity, entity2);
        expect(taskService.compareTask).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
