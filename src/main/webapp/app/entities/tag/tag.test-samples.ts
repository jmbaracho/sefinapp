import { ITag, NewTag } from './tag.model';

export const sampleWithRequiredData: ITag = {
  id: 42372,
};

export const sampleWithPartialData: ITag = {
  id: 49254,
};

export const sampleWithFullData: ITag = {
  id: 77324,
  name: 'Principal HTTP Credit',
  description: 'intangible',
};

export const sampleWithNewData: NewTag = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
