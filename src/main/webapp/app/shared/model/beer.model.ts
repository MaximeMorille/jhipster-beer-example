import { IIngredient } from 'app/shared/model/ingredient.model';
import { BeerColor } from 'app/shared/model/enumerations/beer-color.model';

export interface IBeer {
  id?: number;
  uuid?: string | null;
  name?: string | null;
  abv?: number | null;
  ebc?: number | null;
  ibu?: number | null;
  ownerUuid?: string | null;
  color?: BeerColor | null;
  ingredients?: IIngredient[] | null;
}

export const defaultValue: Readonly<IBeer> = {};
