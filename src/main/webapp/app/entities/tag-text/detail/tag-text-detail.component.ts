import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITagText } from '../tag-text.model';

@Component({
  selector: 'jhi-tag-text-detail',
  templateUrl: './tag-text-detail.component.html',
})
export class TagTextDetailComponent implements OnInit {
  tagText: ITagText | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tagText }) => {
      this.tagText = tagText;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
