import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AtividadeCPBSDetailComponent } from './atividade-cpbs-detail.component';

describe('AtividadeCPBS Management Detail Component', () => {
  let comp: AtividadeCPBSDetailComponent;
  let fixture: ComponentFixture<AtividadeCPBSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AtividadeCPBSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ atividadeCPBS: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AtividadeCPBSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AtividadeCPBSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load atividadeCPBS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.atividadeCPBS).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
