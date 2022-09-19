import { ITagText, NewTagText } from './tag-text.model';

export const sampleWithRequiredData: ITagText = {
  id: 82348,
};

export const sampleWithPartialData: ITagText = {
  id: 37294,
  startPosition: 92459,
};

export const sampleWithFullData: ITagText = {
  id: 55579,
  startPosition: 95051,
  endPosition: 45314,
};

export const sampleWithNewData: NewTagText = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
