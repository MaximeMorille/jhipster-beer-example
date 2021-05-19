import { IBeer } from 'app/shared/model/beer.model';

export interface IIngredient {
  id?: number;
  uuid?: string | null;
  name?: string | null;
  quantity?: number | null;
  unit?: string | null;
  countryName?: string | null;
  beer?: IBeer | null;
}

export const defaultValue: Readonly<IIngredient> = {};
