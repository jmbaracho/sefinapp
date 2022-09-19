import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RawDataDetailComponent } from './raw-data-detail.component';

describe('RawData Management Detail Component', () => {
  let comp: RawDataDetailComponent;
  let fixture: ComponentFixture<RawDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RawDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rawData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RawDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RawDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rawData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rawData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
