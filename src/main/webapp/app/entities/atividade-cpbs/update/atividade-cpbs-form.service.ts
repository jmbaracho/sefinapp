import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAtividadeCPBS, NewAtividadeCPBS } from '../atividade-cpbs.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAtividadeCPBS for edit and NewAtividadeCPBSFormGroupInput for create.
 */
type AtividadeCPBSFormGroupInput = IAtividadeCPBS | PartialWithRequiredKeyOf<NewAtividadeCPBS>;

type AtividadeCPBSFormDefaults = Pick<NewAtividadeCPBS, 'id' | 'principal'>;

type AtividadeCPBSFormGroupContent = {
  id: FormControl<IAtividadeCPBS['id'] | NewAtividadeCPBS['id']>;
  idAtividadeCpbs: FormControl<IAtividadeCPBS['idAtividadeCpbs']>;
  cnae: FormControl<IAtividadeCPBS['cnae']>;
  descricaoCnae: FormControl<IAtividadeCPBS['descricaoCnae']>;
  principal: FormControl<IAtividadeCPBS['principal']>;
  idSegmentoIss: FormControl<IAtividadeCPBS['idSegmentoIss']>;
  descricaoSegmento: FormControl<IAtividadeCPBS['descricaoSegmento']>;
  idListaSerCpbs: FormControl<IAtividadeCPBS['idListaSerCpbs']>;
  codigoListaSerCpbs: FormControl<IAtividadeCPBS['codigoListaSerCpbs']>;
  descricaoListaSerCpbs: FormControl<IAtividadeCPBS['descricaoListaSerCpbs']>;
  inscricaoCpbs: FormControl<IAtividadeCPBS['inscricaoCpbs']>;
};

export type AtividadeCPBSFormGroup = FormGroup<AtividadeCPBSFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AtividadeCPBSFormService {
  createAtividadeCPBSFormGroup(atividadeCPBS: AtividadeCPBSFormGroupInput = { id: null }): AtividadeCPBSFormGroup {
    const atividadeCPBSRawValue = {
      ...this.getFormDefaults(),
      ...atividadeCPBS,
    };
    return new FormGroup<AtividadeCPBSFormGroupContent>({
      id: new FormControl(
        { value: atividadeCPBSRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idAtividadeCpbs: new FormControl(atividadeCPBSRawValue.idAtividadeCpbs),
      cnae: new FormControl(atividadeCPBSRawValue.cnae),
      descricaoCnae: new FormControl(atividadeCPBSRawValue.descricaoCnae),
      principal: new FormControl(atividadeCPBSRawValue.principal),
      idSegmentoIss: new FormControl(atividadeCPBSRawValue.idSegmentoIss),
      descricaoSegmento: new FormControl(atividadeCPBSRawValue.descricaoSegmento),
      idListaSerCpbs: new FormControl(atividadeCPBSRawValue.idListaSerCpbs),
      codigoListaSerCpbs: new FormControl(atividadeCPBSRawValue.codigoListaSerCpbs),
      descricaoListaSerCpbs: new FormControl(atividadeCPBSRawValue.descricaoListaSerCpbs),
      inscricaoCpbs: new FormControl(atividadeCPBSRawValue.inscricaoCpbs),
    });
  }

  getAtividadeCPBS(form: AtividadeCPBSFormGroup): IAtividadeCPBS | NewAtividadeCPBS {
    return form.getRawValue() as IAtividadeCPBS | NewAtividadeCPBS;
  }

  resetForm(form: AtividadeCPBSFormGroup, atividadeCPBS: AtividadeCPBSFormGroupInput): void {
    const atividadeCPBSRawValue = { ...this.getFormDefaults(), ...atividadeCPBS };
    form.reset(
      {
        ...atividadeCPBSRawValue,
        id: { value: atividadeCPBSRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AtividadeCPBSFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
