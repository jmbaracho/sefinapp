import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInscricaoCPBS, NewInscricaoCPBS } from '../inscricao-cpbs.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInscricaoCPBS for edit and NewInscricaoCPBSFormGroupInput for create.
 */
type InscricaoCPBSFormGroupInput = IInscricaoCPBS | PartialWithRequiredKeyOf<NewInscricaoCPBS>;

type InscricaoCPBSFormDefaults = Pick<NewInscricaoCPBS, 'id' | 'optanteSimplesNacional'>;

type InscricaoCPBSFormGroupContent = {
  id: FormControl<IInscricaoCPBS['id'] | NewInscricaoCPBS['id']>;
  inscricao: FormControl<IInscricaoCPBS['inscricao']>;
  nome: FormControl<IInscricaoCPBS['nome']>;
  nomeFantasia: FormControl<IInscricaoCPBS['nomeFantasia']>;
  numDocumento: FormControl<IInscricaoCPBS['numDocumento']>;
  situacao: FormControl<IInscricaoCPBS['situacao']>;
  optanteSimplesNacional: FormControl<IInscricaoCPBS['optanteSimplesNacional']>;
  codigoNaturezaJuridica: FormControl<IInscricaoCPBS['codigoNaturezaJuridica']>;
  descricaoNaturezaJuridica: FormControl<IInscricaoCPBS['descricaoNaturezaJuridica']>;
  tipoLogradouro: FormControl<IInscricaoCPBS['tipoLogradouro']>;
  tituloLogradouro: FormControl<IInscricaoCPBS['tituloLogradouro']>;
  logradouro: FormControl<IInscricaoCPBS['logradouro']>;
  numero: FormControl<IInscricaoCPBS['numero']>;
  complemento: FormControl<IInscricaoCPBS['complemento']>;
  nomeCidade: FormControl<IInscricaoCPBS['nomeCidade']>;
  nomeBairro: FormControl<IInscricaoCPBS['nomeBairro']>;
  numeroCep: FormControl<IInscricaoCPBS['numeroCep']>;
};

export type InscricaoCPBSFormGroup = FormGroup<InscricaoCPBSFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InscricaoCPBSFormService {
  createInscricaoCPBSFormGroup(inscricaoCPBS: InscricaoCPBSFormGroupInput = { id: null }): InscricaoCPBSFormGroup {
    const inscricaoCPBSRawValue = {
      ...this.getFormDefaults(),
      ...inscricaoCPBS,
    };
    return new FormGroup<InscricaoCPBSFormGroupContent>({
      id: new FormControl(
        { value: inscricaoCPBSRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      inscricao: new FormControl(inscricaoCPBSRawValue.inscricao),
      nome: new FormControl(inscricaoCPBSRawValue.nome),
      nomeFantasia: new FormControl(inscricaoCPBSRawValue.nomeFantasia),
      numDocumento: new FormControl(inscricaoCPBSRawValue.numDocumento),
      situacao: new FormControl(inscricaoCPBSRawValue.situacao),
      optanteSimplesNacional: new FormControl(inscricaoCPBSRawValue.optanteSimplesNacional),
      codigoNaturezaJuridica: new FormControl(inscricaoCPBSRawValue.codigoNaturezaJuridica),
      descricaoNaturezaJuridica: new FormControl(inscricaoCPBSRawValue.descricaoNaturezaJuridica),
      tipoLogradouro: new FormControl(inscricaoCPBSRawValue.tipoLogradouro),
      tituloLogradouro: new FormControl(inscricaoCPBSRawValue.tituloLogradouro),
      logradouro: new FormControl(inscricaoCPBSRawValue.logradouro),
      numero: new FormControl(inscricaoCPBSRawValue.numero),
      complemento: new FormControl(inscricaoCPBSRawValue.complemento),
      nomeCidade: new FormControl(inscricaoCPBSRawValue.nomeCidade),
      nomeBairro: new FormControl(inscricaoCPBSRawValue.nomeBairro),
      numeroCep: new FormControl(inscricaoCPBSRawValue.numeroCep),
    });
  }

  getInscricaoCPBS(form: InscricaoCPBSFormGroup): IInscricaoCPBS | NewInscricaoCPBS {
    return form.getRawValue() as IInscricaoCPBS | NewInscricaoCPBS;
  }

  resetForm(form: InscricaoCPBSFormGroup, inscricaoCPBS: InscricaoCPBSFormGroupInput): void {
    const inscricaoCPBSRawValue = { ...this.getFormDefaults(), ...inscricaoCPBS };
    form.reset(
      {
        ...inscricaoCPBSRawValue,
        id: { value: inscricaoCPBSRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InscricaoCPBSFormDefaults {
    return {
      id: null,
      optanteSimplesNacional: false,
    };
  }
}
