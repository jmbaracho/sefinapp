import { INfse } from 'app/entities/nfse/nfse.model';
import { ITag } from 'app/entities/tag/tag.model';

export interface ITagText {
  id: number;
  startPosition?: number | null;
  endPosition?: number | null;
  nfse?: Pick<INfse, 'id' | 'idNfse'> | null;
  tag?: Pick<ITag, 'id'> | null;
}

export type NewTagText = Omit<ITagText, 'id'> & { id: null };
