import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TagTextFormService } from './tag-text-form.service';
import { TagTextService } from '../service/tag-text.service';
import { ITagText } from '../tag-text.model';
import { INfse } from 'app/entities/nfse/nfse.model';
import { NfseService } from 'app/entities/nfse/service/nfse.service';
import { ITag } from 'app/entities/tag/tag.model';
import { TagService } from 'app/entities/tag/service/tag.service';

import { TagTextUpdateComponent } from './tag-text-update.component';

describe('TagText Management Update Component', () => {
  let comp: TagTextUpdateComponent;
  let fixture: ComponentFixture<TagTextUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tagTextFormService: TagTextFormService;
  let tagTextService: TagTextService;
  let nfseService: NfseService;
  let tagService: TagService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TagTextUpdateComponent],
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
      .overrideTemplate(TagTextUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TagTextUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tagTextFormService = TestBed.inject(TagTextFormService);
    tagTextService = TestBed.inject(TagTextService);
    nfseService = TestBed.inject(NfseService);
    tagService = TestBed.inject(TagService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Nfse query and add missing value', () => {
      const tagText: ITagText = { id: 456 };
      const nfse: INfse = { id: 55023 };
      tagText.nfse = nfse;

      const nfseCollection: INfse[] = [{ id: 95880 }];
      jest.spyOn(nfseService, 'query').mockReturnValue(of(new HttpResponse({ body: nfseCollection })));
      const additionalNfses = [nfse];
      const expectedCollection: INfse[] = [...additionalNfses, ...nfseCollection];
      jest.spyOn(nfseService, 'addNfseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tagText });
      comp.ngOnInit();

      expect(nfseService.query).toHaveBeenCalled();
      expect(nfseService.addNfseToCollectionIfMissing).toHaveBeenCalledWith(
        nfseCollection,
        ...additionalNfses.map(expect.objectContaining)
      );
      expect(comp.nfsesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tag query and add missing value', () => {
      const tagText: ITagText = { id: 456 };
      const tag: ITag = { id: 14352 };
      tagText.tag = tag;

      const tagCollection: ITag[] = [{ id: 73400 }];
      jest.spyOn(tagService, 'query').mockReturnValue(of(new HttpResponse({ body: tagCollection })));
      const additionalTags = [tag];
      const expectedCollection: ITag[] = [...additionalTags, ...tagCollection];
      jest.spyOn(tagService, 'addTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tagText });
      comp.ngOnInit();

      expect(tagService.query).toHaveBeenCalled();
      expect(tagService.addTagToCollectionIfMissing).toHaveBeenCalledWith(tagCollection, ...additionalTags.map(expect.objectContaining));
      expect(comp.tagsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tagText: ITagText = { id: 456 };
      const nfse: INfse = { id: 24394 };
      tagText.nfse = nfse;
      const tag: ITag = { id: 47566 };
      tagText.tag = tag;

      activatedRoute.data = of({ tagText });
      comp.ngOnInit();

      expect(comp.nfsesSharedCollection).toContain(nfse);
      expect(comp.tagsSharedCollection).toContain(tag);
      expect(comp.tagText).toEqual(tagText);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITagText>>();
      const tagText = { id: 123 };
      jest.spyOn(tagTextFormService, 'getTagText').mockReturnValue(tagText);
      jest.spyOn(tagTextService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tagText });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tagText }));
      saveSubject.complete();

      // THEN
      expect(tagTextFormService.getTagText).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tagTextService.update).toHaveBeenCalledWith(expect.objectContaining(tagText));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITagText>>();
      const tagText = { id: 123 };
      jest.spyOn(tagTextFormService, 'getTagText').mockReturnValue({ id: null });
      jest.spyOn(tagTextService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tagText: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tagText }));
      saveSubject.complete();

      // THEN
      expect(tagTextFormService.getTagText).toHaveBeenCalled();
      expect(tagTextService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITagText>>();
      const tagText = { id: 123 };
      jest.spyOn(tagTextService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tagText });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tagTextService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNfse', () => {
      it('Should forward to nfseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(nfseService, 'compareNfse');
        comp.compareNfse(entity, entity2);
        expect(nfseService.compareNfse).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTag', () => {
      it('Should forward to tagService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tagService, 'compareTag');
        comp.compareTag(entity, entity2);
        expect(tagService.compareTag).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
