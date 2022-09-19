import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TagTextDetailComponent } from './tag-text-detail.component';

describe('TagText Management Detail Component', () => {
  let comp: TagTextDetailComponent;
  let fixture: ComponentFixture<TagTextDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TagTextDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tagText: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TagTextDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TagTextDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tagText on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tagText).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
