import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NfseDetailComponent } from './nfse-detail.component';

describe('Nfse Management Detail Component', () => {
  let comp: NfseDetailComponent;
  let fixture: ComponentFixture<NfseDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NfseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nfse: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NfseDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NfseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nfse on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nfse).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
