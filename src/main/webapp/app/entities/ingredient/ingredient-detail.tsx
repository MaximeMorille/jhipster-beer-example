import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ingredient.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IIngredientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const IngredientDetail = (props: IIngredientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ingredientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ingredientDetailsHeading">
          <Translate contentKey="jhipsterBeerExampleApp.ingredient.detail.title">Ingredient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ingredientEntity.id}</dd>
          <dt>
            <span id="uuid">
              <Translate contentKey="jhipsterBeerExampleApp.ingredient.uuid">Uuid</Translate>
            </span>
          </dt>
          <dd>{ingredientEntity.uuid}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterBeerExampleApp.ingredient.name">Name</Translate>
            </span>
          </dt>
          <dd>{ingredientEntity.name}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="jhipsterBeerExampleApp.ingredient.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{ingredientEntity.quantity}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="jhipsterBeerExampleApp.ingredient.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{ingredientEntity.unit}</dd>
          <dt>
            <span id="countryName">
              <Translate contentKey="jhipsterBeerExampleApp.ingredient.countryName">Country Name</Translate>
            </span>
          </dt>
          <dd>{ingredientEntity.countryName}</dd>
          <dt>
            <Translate contentKey="jhipsterBeerExampleApp.ingredient.beer">Beer</Translate>
          </dt>
          <dd>{ingredientEntity.beer ? ingredientEntity.beer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ingredient" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ingredient/${ingredientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ ingredient }: IRootState) => ({
  ingredientEntity: ingredient.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(IngredientDetail);
