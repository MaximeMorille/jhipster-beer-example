import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBeer } from 'app/shared/model/beer.model';
import { getEntities as getBeers } from 'app/entities/beer/beer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ingredient.reducer';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IIngredientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IngredientUpdate = (props: IIngredientUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { ingredientEntity, beers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ingredient');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBeers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ingredientEntity,
        ...values,
        beer: beers.find(it => it.id.toString() === values.beerId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterBeerExampleApp.ingredient.home.createOrEditLabel" data-cy="IngredientCreateUpdateHeading">
            <Translate contentKey="jhipsterBeerExampleApp.ingredient.home.createOrEditLabel">Create or edit a Ingredient</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ingredientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ingredient-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ingredient-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="uuidLabel" for="ingredient-uuid">
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.uuid">Uuid</Translate>
                </Label>
                <AvField id="ingredient-uuid" data-cy="uuid" type="text" name="uuid" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="ingredient-name">
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.name">Name</Translate>
                </Label>
                <AvField id="ingredient-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="quantityLabel" for="ingredient-quantity">
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.quantity">Quantity</Translate>
                </Label>
                <AvField id="ingredient-quantity" data-cy="quantity" type="string" className="form-control" name="quantity" />
              </AvGroup>
              <AvGroup>
                <Label id="unitLabel" for="ingredient-unit">
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.unit">Unit</Translate>
                </Label>
                <AvField id="ingredient-unit" data-cy="unit" type="text" name="unit" />
              </AvGroup>
              <AvGroup>
                <Label id="countryNameLabel" for="ingredient-countryName">
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.countryName">Country Name</Translate>
                </Label>
                <AvField id="ingredient-countryName" data-cy="countryName" type="text" name="countryName" />
              </AvGroup>
              <AvGroup>
                <Label for="ingredient-beer">
                  <Translate contentKey="jhipsterBeerExampleApp.ingredient.beer">Beer</Translate>
                </Label>
                <AvInput id="ingredient-beer" data-cy="beer" type="select" className="form-control" name="beerId">
                  <option value="" key="0" />
                  {beers
                    ? beers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ingredient" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  beers: storeState.beer.entities,
  ingredientEntity: storeState.ingredient.entity,
  loading: storeState.ingredient.loading,
  updating: storeState.ingredient.updating,
  updateSuccess: storeState.ingredient.updateSuccess,
});

const mapDispatchToProps = {
  getBeers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IngredientUpdate);
