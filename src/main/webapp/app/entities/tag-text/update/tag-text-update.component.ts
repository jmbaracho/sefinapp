import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TagTextFormService, TagTextFormGroup } from './tag-text-form.service';
import { ITagText } from '../tag-text.model';
import { TagTextService } from '../service/tag-text.service';
import { INfse } from 'app/entities/nfse/nfse.model';
import { NfseService } from 'app/entities/nfse/service/nfse.service';
import { ITag } from 'app/entities/tag/tag.model';
import { TagService } from 'app/entities/tag/service/tag.service';

@Component({
  selector: 'jhi-tag-text-update',
  templateUrl: './tag-text-update.component.html',
})
export class TagTextUpdateComponent implements OnInit {
  isSaving = false;
  tagText: ITagText | null = null;

  nfsesSharedCollection: INfse[] = [];
  tagsSharedCollection: ITag[] = [];

  editForm: TagTextFormGroup = this.tagTextFormService.createTagTextFormGroup();

  constructor(
    protected tagTextService: TagTextService,
    protected tagTextFormService: TagTextFormService,
    protected nfseService: NfseService,
    protected tagService: TagService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNfse = (o1: INfse | null, o2: INfse | null): boolean => this.nfseService.compareNfse(o1, o2);

  compareTag = (o1: ITag | null, o2: ITag | null): boolean => this.tagService.compareTag(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagText }) => {
      this.tagText = tagText;
      if (tagText) {
        this.updateForm(tagText);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tagText = this.tagTextFormService.getTagText(this.editForm);
    if (tagText.id !== null) {
      this.subscribeToSaveResponse(this.tagTextService.update(tagText));
    } else {
      this.subscribeToSaveResponse(this.tagTextService.create(tagText));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITagText>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tagText: ITagText): void {
    this.tagText = tagText;
    this.tagTextFormService.resetForm(this.editForm, tagText);

    this.nfsesSharedCollection = this.nfseService.addNfseToCollectionIfMissing<INfse>(this.nfsesSharedCollection, tagText.nfse);
    this.tagsSharedCollection = this.tagService.addTagToCollectionIfMissing<ITag>(this.tagsSharedCollection, tagText.tag);
  }

  protected loadRelationshipsOptions(): void {
    this.nfseService
      .query()
      .pipe(map((res: HttpResponse<INfse[]>) => res.body ?? []))
      .pipe(map((nfses: INfse[]) => this.nfseService.addNfseToCollectionIfMissing<INfse>(nfses, this.tagText?.nfse)))
      .subscribe((nfses: INfse[]) => (this.nfsesSharedCollection = nfses));

    this.tagService
      .query()
      .pipe(map((res: HttpResponse<ITag[]>) => res.body ?? []))
      .pipe(map((tags: ITag[]) => this.tagService.addTagToCollectionIfMissing<ITag>(tags, this.tagText?.tag)))
      .subscribe((tags: ITag[]) => (this.tagsSharedCollection = tags));
  }
}
