import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITagText, NewTagText } from '../tag-text.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITagText for edit and NewTagTextFormGroupInput for create.
 */
type TagTextFormGroupInput = ITagText | PartialWithRequiredKeyOf<NewTagText>;

type TagTextFormDefaults = Pick<NewTagText, 'id'>;

type TagTextFormGroupContent = {
  id: FormControl<ITagText['id'] | NewTagText['id']>;
  startPosition: FormControl<ITagText['startPosition']>;
  endPosition: FormControl<ITagText['endPosition']>;
  nfse: FormControl<ITagText['nfse']>;
  tag: FormControl<ITagText['tag']>;
};

export type TagTextFormGroup = FormGroup<TagTextFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TagTextFormService {
  createTagTextFormGroup(tagText: TagTextFormGroupInput = { id: null }): TagTextFormGroup {
    const tagTextRawValue = {
      ...this.getFormDefaults(),
      ...tagText,
    };
    return new FormGroup<TagTextFormGroupContent>({
      id: new FormControl(
        { value: tagTextRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      startPosition: new FormControl(tagTextRawValue.startPosition),
      endPosition: new FormControl(tagTextRawValue.endPosition),
      nfse: new FormControl(tagTextRawValue.nfse),
      tag: new FormControl(tagTextRawValue.tag),
    });
  }

  getTagText(form: TagTextFormGroup): ITagText | NewTagText {
    return form.getRawValue() as ITagText | NewTagText;
  }

  resetForm(form: TagTextFormGroup, tagText: TagTextFormGroupInput): void {
    const tagTextRawValue = { ...this.getFormDefaults(), ...tagText };
    form.reset(
      {
        ...tagTextRawValue,
        id: { value: tagTextRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TagTextFormDefaults {
    return {
      id: null,
    };
  }
}
