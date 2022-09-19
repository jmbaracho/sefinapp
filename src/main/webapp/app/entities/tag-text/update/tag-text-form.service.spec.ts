import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tag-text.test-samples';

import { TagTextFormService } from './tag-text-form.service';

describe('TagText Form Service', () => {
  let service: TagTextFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TagTextFormService);
  });

  describe('Service methods', () => {
    describe('createTagTextFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTagTextFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startPosition: expect.any(Object),
            endPosition: expect.any(Object),
            nfse: expect.any(Object),
            tag: expect.any(Object),
          })
        );
      });

      it('passing ITagText should create a new form with FormGroup', () => {
        const formGroup = service.createTagTextFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startPosition: expect.any(Object),
            endPosition: expect.any(Object),
            nfse: expect.any(Object),
            tag: expect.any(Object),
          })
        );
      });
    });

    describe('getTagText', () => {
      it('should return NewTagText for default TagText initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTagTextFormGroup(sampleWithNewData);

        const tagText = service.getTagText(formGroup) as any;

        expect(tagText).toMatchObject(sampleWithNewData);
      });

      it('should return NewTagText for empty TagText initial value', () => {
        const formGroup = service.createTagTextFormGroup();

        const tagText = service.getTagText(formGroup) as any;

        expect(tagText).toMatchObject({});
      });

      it('should return ITagText', () => {
        const formGroup = service.createTagTextFormGroup(sampleWithRequiredData);

        const tagText = service.getTagText(formGroup) as any;

        expect(tagText).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITagText should not enable id FormControl', () => {
        const formGroup = service.createTagTextFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTagText should disable id FormControl', () => {
        const formGroup = service.createTagTextFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
