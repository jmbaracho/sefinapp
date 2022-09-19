import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HotelResultDetailComponent } from './hotel-result-detail.component';

describe('HotelResult Management Detail Component', () => {
  let comp: HotelResultDetailComponent;
  let fixture: ComponentFixture<HotelResultDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelResultDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hotelResult: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HotelResultDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HotelResultDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hotelResult on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hotelResult).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
