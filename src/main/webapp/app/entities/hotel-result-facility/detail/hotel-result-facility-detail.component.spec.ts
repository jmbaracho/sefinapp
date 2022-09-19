import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HotelResultFacilityDetailComponent } from './hotel-result-facility-detail.component';

describe('HotelResultFacility Management Detail Component', () => {
  let comp: HotelResultFacilityDetailComponent;
  let fixture: ComponentFixture<HotelResultFacilityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelResultFacilityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hotelResultFacility: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HotelResultFacilityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HotelResultFacilityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hotelResultFacility on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hotelResultFacility).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
