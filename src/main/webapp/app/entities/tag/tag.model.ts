export interface ITag {
  id: number;
  name?: string | null;
  description?: string | null;
}

export type NewTag = Omit<ITag, 'id'> & { id: null };
