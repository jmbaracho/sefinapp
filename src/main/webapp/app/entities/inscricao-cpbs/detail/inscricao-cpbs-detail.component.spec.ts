import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InscricaoCPBSDetailComponent } from './inscricao-cpbs-detail.component';

describe('InscricaoCPBS Management Detail Component', () => {
  let comp: InscricaoCPBSDetailComponent;
  let fixture: ComponentFixture<InscricaoCPBSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InscricaoCPBSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ inscricaoCPBS: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InscricaoCPBSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InscricaoCPBSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load inscricaoCPBS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.inscricaoCPBS).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
