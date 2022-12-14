import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RoomFormService } from './room-form.service';
import { RoomService } from '../service/room.service';
import { IRoom } from '../room.model';
import { IHotelResult } from 'app/entities/hotel-result/hotel-result.model';
import { HotelResultService } from 'app/entities/hotel-result/service/hotel-result.service';

import { RoomUpdateComponent } from './room-update.component';

describe('Room Management Update Component', () => {
  let comp: RoomUpdateComponent;
  let fixture: ComponentFixture<RoomUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let roomFormService: RoomFormService;
  let roomService: RoomService;
  let hotelResultService: HotelResultService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RoomUpdateComponent],
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
      .overrideTemplate(RoomUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RoomUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    roomFormService = TestBed.inject(RoomFormService);
    roomService = TestBed.inject(RoomService);
    hotelResultService = TestBed.inject(HotelResultService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call HotelResult query and add missing value', () => {
      const room: IRoom = { id: 456 };
      const hotelResult: IHotelResult = { id: 35178 };
      room.hotelResult = hotelResult;

      const hotelResultCollection: IHotelResult[] = [{ id: 99913 }];
      jest.spyOn(hotelResultService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelResultCollection })));
      const additionalHotelResults = [hotelResult];
      const expectedCollection: IHotelResult[] = [...additionalHotelResults, ...hotelResultCollection];
      jest.spyOn(hotelResultService, 'addHotelResultToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ room });
      comp.ngOnInit();

      expect(hotelResultService.query).toHaveBeenCalled();
      expect(hotelResultService.addHotelResultToCollectionIfMissing).toHaveBeenCalledWith(
        hotelResultCollection,
        ...additionalHotelResults.map(expect.objectContaining)
      );
      expect(comp.hotelResultsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const room: IRoom = { id: 456 };
      const hotelResult: IHotelResult = { id: 66502 };
      room.hotelResult = hotelResult;

      activatedRoute.data = of({ room });
      comp.ngOnInit();

      expect(comp.hotelResultsSharedCollection).toContain(hotelResult);
      expect(comp.room).toEqual(room);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoom>>();
      const room = { id: 123 };
      jest.spyOn(roomFormService, 'getRoom').mockReturnValue(room);
      jest.spyOn(roomService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ room });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: room }));
      saveSubject.complete();

      // THEN
      expect(roomFormService.getRoom).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(roomService.update).toHaveBeenCalledWith(expect.objectContaining(room));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoom>>();
      const room = { id: 123 };
      jest.spyOn(roomFormService, 'getRoom').mockReturnValue({ id: null });
      jest.spyOn(roomService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ room: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: room }));
      saveSubject.complete();

      // THEN
      expect(roomFormService.getRoom).toHaveBeenCalled();
      expect(roomService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRoom>>();
      const room = { id: 123 };
      jest.spyOn(roomService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ room });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(roomService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHotelResult', () => {
      it('Should forward to hotelResultService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelResultService, 'compareHotelResult');
        comp.compareHotelResult(entity, entity2);
        expect(hotelResultService.compareHotelResult).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
